package br.com.resolveai.melodia.importacao.service;

import br.com.resolveai.melodia.core.util.LogUtil;
import br.com.resolveai.melodia.domain.model.Artista;
import br.com.resolveai.melodia.domain.model.GeneroMusical;
import br.com.resolveai.melodia.domain.model.Musica;
import br.com.resolveai.melodia.domain.repository.ArtistaRepository;
import br.com.resolveai.melodia.domain.repository.MusicaRepository;
import br.com.resolveai.melodia.domain.service.GeneroMusicalService;
import br.com.resolveai.melodia.importacao.model.ImportacaoStatus;
import br.com.resolveai.melodia.importacao.model.enums.IdentificadorImportacao;
import br.com.resolveai.melodia.importacao.model.enums.TipoImportacao;
import br.com.resolveai.melodia.importacao.util.HttpClientUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class JamendoMusicasService {

    private static final Logger logger = LoggerFactory.getLogger(JamendoMusicasService.class);

    private static final String API_KEY = "ec0eeb44";
    private static final String BASE_URL = "https://api.jamendo.com/v3.0";
    private static final String ARTISTS_PATH = "/artists";
    private static final String TRACKS_PATH = "/artists/tracks";
    private static final String CLIENT_ID_PARAM = "client_id";
    private static final String FORMAT_PARAM = "format";
    private static final String JSON_FORMAT = "json";
    private static final String PARAM_RESULTADOS = "results";
    private static final String PARAM_NOME = "name";
    private static final String PARAM_TRACKS = "tracks";
    private static final String PARAM_TITULO = "name";
    private static final String PARAM_ALBUM = "album_name";
    private static final String PARAM_DURACAO = "duration";
    private static final String PARAM_IMAGE_ARTISTA = "image";
    private static final String PARAM_CAPA_MUSICA = "image";
    private static final String PARAM_AUDIO_URL = "audio";
    private static final String PARAM_DATA_LANCAMENTO = "releasedate";
    private static final String PARAM_POPULARIDADE = "popularity";

    private static final String MSG_ERRO_PROCESSAR_RESPOSTA = "Erro ao processar a resposta do serviço";
    private static final String MSG_ERRO_DATA_INVALIDA = "Data de lançamento fornecida está em um formato inválido: {}";

    private static final String MSG_IMPORTACAO_JA_CONCLUIDA = "A importação de músicas já foi realizada e concluída!";
    protected static final String MSG_IMPORTACAO_SUCESSO = "Importação de músicas realizada com sucesso.";
    private static final String MSG_IMPORTACAO_ERRO = "Erro ao realizar a importação de músicas. Tente novamente mais tarde.";

    private static final LocalDate DATA_MINIMA = LocalDate.of(1990, 1, 1);
    private static final LocalDate DATA_ATUAL = LocalDate.now();

    private final MusicaRepository musicaRepository;
    private final GeneroMusicalService generoMusicalService;
    private final ImportacaoStatusService importacaoStatusService;
    private final ArtistaRepository artistaRepository;

    private final WebClient webClient;

    private final Random random = new Random();

    public JamendoMusicasService(MusicaRepository musicaRepository, GeneroMusicalService generoMusicalService,
        ImportacaoStatusService importacaoStatusService, ArtistaRepository artistaRepository) {
        this.musicaRepository = musicaRepository;
        this.generoMusicalService = generoMusicalService;
        this.importacaoStatusService = importacaoStatusService;
        this.artistaRepository = artistaRepository;
        this.webClient = WebClient.builder()
            .baseUrl(BASE_URL)
            .clientConnector(new ReactorClientHttpConnector(HttpClientUtil.createSecureHttpClient()))
            .build();
    }

    public String importarMusicas() {
        if (importacaoJaConcluida()) {
            return MSG_IMPORTACAO_JA_CONCLUIDA;
        }

        ImportacaoStatus importacao = iniciarNovaImportacao();

        try {
            processarImportacaoDeMusicas();
            finalizarImportacaoComSucesso(importacao);
            return MSG_IMPORTACAO_SUCESSO;
        } catch (Exception e) {
            registrarFalhaNaImportacao(importacao, e);
            return MSG_IMPORTACAO_ERRO;
        }
    }

    private boolean importacaoJaConcluida() {
        return importacaoStatusService.existeImportacaoConcluida(
            IdentificadorImportacao.JAMENDO, TipoImportacao.MUSICA
        );
    }

    private ImportacaoStatus iniciarNovaImportacao() {
        return importacaoStatusService.criarNovaImportacao(
            IdentificadorImportacao.JAMENDO, TipoImportacao.MUSICA
        );
    }

    private void processarImportacaoDeMusicas() {
        List<ArtistaInfo> artistas = listarArtistas();
        for (ArtistaInfo artistaInfo : artistas) {
            importarMusicasPorArtista(artistaInfo.nome(), artistaInfo.fotoUrl());
        }
    }

    private void finalizarImportacaoComSucesso(ImportacaoStatus importacao) {
        importacaoStatusService.atualizarImportacaoConcluida(importacao);
    }

    private void registrarFalhaNaImportacao(ImportacaoStatus importacao, Exception e) {
        LogUtil.registrarExcecao(logger, "Erro durante a importação", e);
        importacaoStatusService.atualizarImportacaoFalha(importacao);
    }

    private List<ArtistaInfo> listarArtistas() {
        String response = webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path(ARTISTS_PATH)
                .queryParam(CLIENT_ID_PARAM, API_KEY)
                .queryParam(FORMAT_PARAM, JSON_FORMAT)
                .build())
            .retrieve()
            .bodyToMono(String.class)
            .block();

        return extrairNomesArtistas(response);
    }

    private List<ArtistaInfo> extrairNomesArtistas(String response) {
        List<ArtistaInfo> artistasInfo = new ArrayList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode artistsNode = rootNode.path(PARAM_RESULTADOS);

            for (JsonNode artistNode : artistsNode) {
                String nome = artistNode.path(PARAM_NOME).asText();
                String fotoUrl = artistNode.path(PARAM_IMAGE_ARTISTA).asText();
                artistasInfo.add(new ArtistaInfo(nome, fotoUrl));
            }
        } catch (Exception e) {
            LogUtil.registrarExcecao(logger, MSG_ERRO_PROCESSAR_RESPOSTA, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, MSG_ERRO_PROCESSAR_RESPOSTA, e);
        }

        return artistasInfo;
    }

    private void importarMusicasPorArtista(String artistaNome, String fotoUrl) {
        String response = webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path(TRACKS_PATH)
                .queryParam(CLIENT_ID_PARAM, API_KEY)
                .queryParam(FORMAT_PARAM, JSON_FORMAT)
                .queryParam(PARAM_NOME, artistaNome)
                .build())
            .retrieve()
            .bodyToMono(String.class)
            .block();

        salvarMusicas(response, artistaNome, fotoUrl);
    }

    private void salvarMusicas(String response, String artistaNome, String fotoUrl) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode tracksNode = rootNode.path(PARAM_RESULTADOS);

            Artista artista = buscarOuCriarArtista(artistaNome, fotoUrl);

            List<Musica> musicas = new ArrayList<>();

            for (JsonNode artistNode : tracksNode) {
                JsonNode tracks = artistNode.path(PARAM_TRACKS);
                for (JsonNode trackNode : tracks) {
                    Musica musica = new Musica();
                    musica.setTitulo(trackNode.path(PARAM_TITULO).asText());
                    musica.setArtista(artista);
                    musica.setAlbum(trackNode.path(PARAM_ALBUM).asText());
                    musica.setGeneroMusical(selecionarGenero());
                    musica.setDuracao(trackNode.path(PARAM_DURACAO).asInt());
                    musica.setCapaUrl(trackNode.path(PARAM_CAPA_MUSICA).asText());
                    musica.setAudioUrl(trackNode.path(PARAM_AUDIO_URL).asText());
                    musica.setPopularidade(definirPopularidade(trackNode.path(PARAM_POPULARIDADE).asInt()));
                    musica.setDataLancamento(definirDataLancamento(trackNode.path(PARAM_DATA_LANCAMENTO).asText()));

                    musicas.add(musica);
                }
            }

            musicaRepository.saveAll(musicas);
        } catch (Exception e) {
            LogUtil.registrarExcecao(logger, MSG_ERRO_PROCESSAR_RESPOSTA, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, MSG_ERRO_PROCESSAR_RESPOSTA, e);
        }
    }

    private Artista buscarOuCriarArtista(String nome, String fotoUrl) {
        Optional<Artista> artistaExistente = artistaRepository.findByNome(nome);

        return artistaExistente.orElseGet(() -> {
            Artista novoArtista = new Artista();
            novoArtista.setNome(nome);
            novoArtista.setFotoUrl(fotoUrl);
            return artistaRepository.save(novoArtista);
        });
    }

    private GeneroMusical selecionarGenero() {
        List<GeneroMusical> generosMusicais = generoMusicalService.listarTodosGenerosMusicais();

        if (!generosMusicais.isEmpty()) {
            return generosMusicais.get(random.nextInt(generosMusicais.size()));
        } else {
            return generoMusicalService.criarGeneroPadrao();
        }
    }

    private int definirPopularidade(int popularidade) {
        if (popularidade == 0) {
            popularidade = random.nextInt(101);
        }
        return popularidade;
    }

    private LocalDate definirDataLancamento(String releaseDate) {
        if (!releaseDate.isEmpty()) {
            try {
                return LocalDate.parse(releaseDate);
            } catch (Exception e) {
                logger.warn(MSG_ERRO_DATA_INVALIDA, releaseDate, e);
            }
        }
        long diasEntre = ChronoUnit.DAYS.between(DATA_MINIMA, DATA_ATUAL);
        return DATA_MINIMA.plusDays(random.nextInt((int) diasEntre + 1));
    }

    private record ArtistaInfo(String nome, String fotoUrl) {}

}
