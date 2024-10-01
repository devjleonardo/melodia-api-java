package br.com.resolveai.melodia.importacao.service;

import br.com.resolveai.melodia.domain.model.*;
import br.com.resolveai.melodia.domain.model.enums.*;
import br.com.resolveai.melodia.domain.repository.MusicaRepository;
import br.com.resolveai.melodia.domain.repository.UsuarioRepository;
import br.com.resolveai.melodia.domain.service.GeneroMusicalService;
import br.com.resolveai.melodia.importacao.model.ImportacaoStatus;
import br.com.resolveai.melodia.importacao.model.enums.IdentificadorImportacao;
import br.com.resolveai.melodia.importacao.model.enums.TipoImportacao;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UsuarioFakerService {

    private static final String MSG_IMPORTACAO_JA_CONCLUIDA = "A importação de usuários já foi realizada e concluída!";
    private static final String MSG_IMPORTACAO_SUCESSO = "Importação de usuários realizada com sucesso.";
    private static final String MSG_IMPORTACAO_ERRO = "Erro ao realizar a importação de usuário. Tente novamente mais tarde.";

    private static final LocalDate DATA_NASCIMENTO_MINIMA = LocalDate.of(1960, 1, 1);
    private static final LocalDate DATA_NASCIMENTO_MAXIMA = LocalDate.of(2007, 12, 31);

    private final UsuarioRepository usuarioRepository;
    private final MusicaRepository musicaRepository;
    private final ImportacaoStatusService importacaoStatusService;
    private final PlaylistFakerService playlistFakerService;
    private final GeneroMusicalService generoMusicalService;

    private final Faker faker = new Faker();
    private final Random random = new Random();

    public String importarUsuarios() {
        if (importacaoJaConcluida()) {
            return MSG_IMPORTACAO_JA_CONCLUIDA;
        }

        if (!importacaoJaConcluidaPlaylists()) {
            String resultadoImportacaoPlaylists = playlistFakerService.importarPlaylists();
            if (!resultadoImportacaoPlaylists.equals(PlaylistFakerService.MSG_IMPORTACAO_SUCESSO)) {
                return resultadoImportacaoPlaylists;
            }
        }

        ImportacaoStatus importacao = iniciarNovaImportacao();

        try {
            gerarUsuariosFicticios();
            finalizarImportacaoComSucesso(importacao);
            return MSG_IMPORTACAO_SUCESSO;
        } catch (Exception e) {
            registrarFalhaNaImportacao(importacao, e);
            return MSG_IMPORTACAO_ERRO;
        }
    }

    private boolean importacaoJaConcluida() {
        return importacaoStatusService.existeImportacaoConcluida(
            IdentificadorImportacao.INTERNA, TipoImportacao.USUARIO
        );
    }

    private boolean importacaoJaConcluidaPlaylists() {
        return importacaoStatusService.existeImportacaoConcluida(
            IdentificadorImportacao.INTERNA, TipoImportacao.PLAYLIST
        );
    }

    private ImportacaoStatus iniciarNovaImportacao() {
        return importacaoStatusService.criarNovaImportacao(
            IdentificadorImportacao.INTERNA, TipoImportacao.USUARIO
        );
    }

    private void gerarUsuariosFicticios() {
        List<Usuario> usuarios = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Usuario usuario = gerarUsuarioFicticio();
            usuarios.add(usuario);
        }
        usuarioRepository.saveAll(usuarios);
    }

    private Usuario gerarUsuarioFicticio() {
        Usuario usuario = new Usuario();
        usuario.setEmail(gerarEmailUnico());
        usuario.setSenha(faker.internet().password());
        usuario.setNome(faker.name().fullName());

        // Gerar data de nascimento aleatória entre 1960 e 2007
        usuario.setDataNascimento(gerarDataNascimentoAleatoria());

        // Geração de gênero e plano
        usuario.setGeneroUsuario(GeneroUsuario.values()[random.nextInt(GeneroUsuario.values().length)]);
        usuario.setPlano(Plano.values()[random.nextInt(Plano.values().length)]);

        // Definir o último login aleatoriamente nos últimos 30 dias
        usuario.setDataUltimoLogin(LocalDateTime.now().minusDays(random.nextInt(30)));

        // Foto de perfil fictícia
        usuario.setFotoPerfilUrl(faker.internet().avatar());

        // Geração de estados emocionais
        usuario.setHistoricoEstadosEmocionais(gerarEstadosEmocionais(usuario));

        // Geração de preferências musicais
        usuario.setPreferenciasMusicais(gerarPreferenciasMusicais(usuario));

        // Geração de histórico de músicas
//        usuario.setHistoricoMusica22s(gerarHistoricoMusicas(usuario));

        // Geração de seguidores e seguindo
        usuario.setSeguidores(new ArrayList<>());
        usuario.setSeguindo(new ArrayList<>());

        // Geração de notificações e configurações de privacidade
        usuario.setPreferenciasNotificacao(gerarPreferenciasNotificacao());
        usuario.setConfiguracoesPrivacidade(gerarConfiguracoesPrivacidade());

        // Geração de recomendações de música
        usuario.setRecomendacoes(gerarRecomendacoesMusicas(usuario));

        return usuario;
    }

    private String gerarEmailUnico() {
        String email;
        do {
            email = faker.internet().emailAddress();
        } while (usuarioRepository.findByEmail(email).isPresent());
        return email;
    }

    private LocalDate gerarDataNascimentoAleatoria() {
        long diasEntre = ChronoUnit.DAYS.between(DATA_NASCIMENTO_MINIMA, DATA_NASCIMENTO_MAXIMA);
        return DATA_NASCIMENTO_MINIMA.plusDays(random.nextInt((int) diasEntre + 1));
    }

    private List<HistoricoEstadoEmocionalUsuario> gerarEstadosEmocionais(Usuario usuario) {
        List<HistoricoEstadoEmocionalUsuario> estadosEmocionais = new ArrayList<>();
        int quantidade = random.nextInt(8) + 1;
        for (int i = 0; i < quantidade; i++) {
            HistoricoEstadoEmocionalUsuario historicoEstadoEmocionalUsuario = new HistoricoEstadoEmocionalUsuario();
            historicoEstadoEmocionalUsuario.setUsuario(usuario);
            historicoEstadoEmocionalUsuario.setEstadoEmocional(EstadoEmocional.values()[random.nextInt(EstadoEmocional.values().length)]);
            historicoEstadoEmocionalUsuario.setData(LocalDateTime.now().minusDays(random.nextInt(365)));
            estadosEmocionais.add(historicoEstadoEmocionalUsuario);
        }
        return estadosEmocionais;
    }

    private List<PreferenciasMusicais> gerarPreferenciasMusicais(Usuario usuario) {
        List<PreferenciasMusicais> preferenciasMusicais = new ArrayList<>();
        List<GeneroMusical> generosMusicais = generoMusicalService.listarTodosGenerosMusicais();

        int quantidade = random.nextInt(5) + 1;
        for (int i = 0; i < quantidade; i++) {
            PreferenciasMusicais preferencia = new PreferenciasMusicais();

            GeneroMusical generoAleatorio = generosMusicais.get(random.nextInt(generosMusicais.size()));
            preferencia.setGenero(generoAleatorio.getNome());

            preferencia.setDataInicio(LocalDate.now().minusDays(random.nextInt(365 * 5)));
            preferencia.setRankingPopularidade(random.nextInt(100) + 1);
            preferencia.setUsuario(usuario);
            preferenciasMusicais.add(preferencia);
        }
        return preferenciasMusicais;
    }

//    private List<HistoricoMusica> gerarHistoricoMusicas(Usuario usuario) {
//        List<HistoricoMusica> historicoMusica22s = new ArrayList<>();
//        List<Musica> musicas = new ArrayList<>(musicaRepository.findAll());
//        int quantidade = Math.min(random.nextInt(16) + 1, musicas.size());
//
//        for (int i = 0; i < quantidade; i++) {
//            Musica musicaSelecionada = musicas.remove(random.nextInt(musicas.size()));
//
//            HistoricoMusica historicoMusica22 = new HistoricoMusica();
//            historicoMusica22.setUsuario(usuario);
//            historicoMusica22.setMusica(musicaSelecionada);
//            historicoMusica22.setDataOuvida(LocalDateTime.now().minusDays(random.nextInt(365)));
//            historicoMusica22.setEstadoEmocional(EstadoEmocional.values()[random.nextInt(EstadoEmocional.values().length)]);
//            historicoMusica22s.add(historicoMusica22);
//        }
//
//        return historicoMusica22s;
//    }

    private List<RecomendacaoMusica> gerarRecomendacoesMusicas(Usuario usuario) {
        List<RecomendacaoMusica> recomendacoes = new ArrayList<>();
        List<Musica> musicas = musicaRepository.findAll();
        int quantidade = random.nextInt(5) + 1;
        for (int i = 0; i < quantidade; i++) {
            RecomendacaoMusica recomendacao = new RecomendacaoMusica();
            recomendacao.setUsuario(usuario);
            recomendacao.setMusica(musicas.get(random.nextInt(musicas.size())));
            recomendacao.setBaseRecomendacao(BaseRecomendacao.values()[random.nextInt(BaseRecomendacao.values().length)]);
            recomendacao.setDataRecomendacao(LocalDateTime.now().minusDays(random.nextInt(365)));
            recomendacoes.add(recomendacao);
        }
        return recomendacoes;
    }

    private List<TipoNotificacao> gerarPreferenciasNotificacao() {
        List<TipoNotificacao> preferencias = new ArrayList<>();
        for (TipoNotificacao tipo : TipoNotificacao.values()) {
            if (random.nextBoolean()) {
                preferencias.add(tipo);
            }
        }
        return preferencias;
    }

    private List<ConfiguracaoPrivacidade> gerarConfiguracoesPrivacidade() {
        List<ConfiguracaoPrivacidade> configuracoes = new ArrayList<>();
        for (ConfiguracaoPrivacidade configuracao : ConfiguracaoPrivacidade.values()) {
            if (random.nextBoolean()) {
                configuracoes.add(configuracao);
            }
        }
        return configuracoes;
    }

    private void finalizarImportacaoComSucesso(ImportacaoStatus importacao) {
        importacaoStatusService.atualizarImportacaoConcluida(importacao);
    }

    private void registrarFalhaNaImportacao(ImportacaoStatus importacao, Exception e) {
        importacaoStatusService.atualizarImportacaoFalha(importacao);
    }

}
