package br.com.resolveai.melodia.importacao.service;

import br.com.resolveai.melodia.domain.model.GeneroMusical;
import br.com.resolveai.melodia.domain.model.Musica;
import br.com.resolveai.melodia.domain.model.Playlist;
import br.com.resolveai.melodia.domain.repository.PlaylistRepository;
import br.com.resolveai.melodia.domain.service.MusicaService;
import br.com.resolveai.melodia.importacao.model.ImportacaoStatus;
import br.com.resolveai.melodia.importacao.model.enums.IdentificadorImportacao;
import br.com.resolveai.melodia.importacao.model.enums.TipoImportacao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaylistFakerService {

    private static final String MSG_IMPORTACAO_JA_CONCLUIDA = "A importação de playlists já foi realizada e concluída!";
    protected static final String MSG_IMPORTACAO_SUCESSO = "Importação de playlists realizada com sucesso.";
    private static final String MSG_IMPORTACAO_ERRO = "Erro ao realizar a importação de playlists. Tente novamente mais tarde.";

    private final PlaylistRepository playlistRepository;
    private final MusicaService musicaService;
    private final JamendoMusicasService jamendoMusicasService;
    private final ImportacaoStatusService importacaoStatusService;

    @Transactional
    public String importarPlaylists() {
        if (importacaoJaConcluida()) {
            return MSG_IMPORTACAO_JA_CONCLUIDA;
        }

//        if (!importacaoJaConcluidaMusicas()) {
//            String resultadoImportacaoMusicas = jamendoMusicasService.importarMusicas();
//            if (!resultadoImportacaoMusicas.equals(JamendoMusicasService.MSG_IMPORTACAO_SUCESSO)) {
//                return resultadoImportacaoMusicas;
//            }
//        }

        ImportacaoStatus importacao = iniciarNovaImportacao();

        try {
            Map<GeneroMusical, List<Musica>> musicasPorGenero = musicaService.listarTodasMusicas()
                .stream()
                .collect(Collectors.groupingBy(Musica::getGeneroMusical));

            musicasPorGenero.forEach(this::criarPlaylistSeNaoExistir);

            finalizarImportacaoComSucesso(importacao);
            return MSG_IMPORTACAO_SUCESSO;
        } catch (Exception e) {
            registrarFalhaNaImportacao(importacao);
            return MSG_IMPORTACAO_ERRO;
        }
    }

    private boolean importacaoJaConcluida() {
        return importacaoStatusService.existeImportacaoConcluida(
            IdentificadorImportacao.INTERNA, TipoImportacao.PLAYLIST
        );
    }

    private boolean importacaoJaConcluidaMusicas() {
        return importacaoStatusService.existeImportacaoConcluida(
            IdentificadorImportacao.JAMENDO, TipoImportacao.MUSICA
        );
    }

    private ImportacaoStatus iniciarNovaImportacao() {
        return importacaoStatusService.criarNovaImportacao(
            IdentificadorImportacao.INTERNA, TipoImportacao.PLAYLIST
        );
    }

    private void finalizarImportacaoComSucesso(ImportacaoStatus importacao) {
        importacaoStatusService.atualizarImportacaoConcluida(importacao);
    }

    private void registrarFalhaNaImportacao(ImportacaoStatus importacao) {
        importacaoStatusService.atualizarImportacaoFalha(importacao);
    }

    private void criarPlaylistSeNaoExistir(GeneroMusical generoMusical, List<Musica> musicas) {
        if (musicas.isEmpty()) {
            return;
        }

        boolean playlistExistente = playlistRepository.existsByGeneroMusicalAndUsuarioIsNull(generoMusical);

        if (playlistExistente) {
            return;
        }

        Playlist playlist = new Playlist();
        playlist.setNome(generoMusical.getNome());
        playlist.setDescricao("Só as melhores músicas do gênero " + generoMusical.getNome());
        playlist.setDataAtualizacao(LocalDate.now());
        playlist.setPublica(true);
        playlist.setColaborativa(false);
        playlist.setGeneroMusical(generoMusical);
        playlist.setImagemUrl("https://" + generoMusical.getNome() + ".png");

        musicas.forEach(playlist::adicionarMusica);

        playlist.setNumeroMusicas(musicas.size());
        int duracaoTotal = musicas.stream().mapToInt(Musica::getDuracao).sum();
        playlist.setDuracaoTotal(duracaoTotal);

        playlistRepository.save(playlist);
    }

}
