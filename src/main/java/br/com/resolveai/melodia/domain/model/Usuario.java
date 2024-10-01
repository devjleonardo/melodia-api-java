package br.com.resolveai.melodia.domain.model;

import br.com.resolveai.melodia.domain.model.enums.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Usuario extends EntidadeBase {

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private LocalDate dataNascimento;

    private LocalDateTime dataUltimoLogin;

    private String fotoPerfilUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GeneroUsuario generoUsuario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Plano plano = Plano.GRATUITO;

    @ElementCollection
    @CollectionTable(name = "usuario_preferencias_notificacoes", joinColumns = @JoinColumn(name = "usuario_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_notificacao")
    private List<TipoNotificacao> preferenciasNotificacao = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "usuario_configuracoes_privacidade", joinColumns = @JoinColumn(name = "usuario_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "configuracao_privacidade")
    private List<ConfiguracaoPrivacidade> configuracoesPrivacidade = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<HistoricoEstadoEmocionalUsuario> historicoEstadosEmocionais = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<HistoricoAcao> historicoAcoes = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<PreferenciasMusicais> preferenciasMusicais = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<RecomendacaoMusica> recomendacoes = new ArrayList<>();

    @OneToMany(mappedBy = "usuario")
    private List<Playlist> playlists = new ArrayList<>();

    @OneToMany(mappedBy = "seguido")
    private List<RelacionamentoUsuario> seguidores = new ArrayList<>();

    @OneToMany(mappedBy = "seguidor")
    private List<RelacionamentoUsuario> seguindo = new ArrayList<>();

    public void adicionarPlaylist(Playlist playlist) {
        if (!this.playlists.contains(playlist)) {
            this.playlists.add(playlist);
            playlist.setUsuario(this);
        }
    }

    public void seguirUsuario(Usuario usuario) {
        boolean jaSeguindo = this.seguindo.stream()
            .anyMatch(rel -> rel.getSeguido().equals(usuario));

        if (!jaSeguindo) {
            RelacionamentoUsuario relacionamento = new RelacionamentoUsuario();
            relacionamento.setSeguidor(this);
            relacionamento.setSeguido(usuario);
            relacionamento.setDataInicio(LocalDateTime.now());

            this.seguindo.add(relacionamento);
            usuario.getSeguidores().add(relacionamento);
        }
    }

    public void deixarDeSeguirUsuario(Usuario usuario) {
        RelacionamentoUsuario relacionamento = this.seguindo.stream()
            .filter(rel -> rel.getSeguido().equals(usuario))
            .findFirst()
            .orElse(null);

        if (relacionamento != null) {
            this.seguindo.remove(relacionamento);
            usuario.getSeguidores().remove(relacionamento);
        }
    }
    public boolean estaSeguindo(Usuario usuario) {
        return this.seguindo.contains(usuario);
    }

    public void atualizarPreferenciasMusicais(List<PreferenciasMusicais> novasPreferencias) {
        if (novasPreferencias != null) {
            this.preferenciasMusicais.clear();
            this.preferenciasMusicais.addAll(novasPreferencias);
        }
    }

    public void alterarEstadoEmocional(EstadoEmocional novoEstado) {
        if (novoEstado != null) {
            HistoricoEstadoEmocionalUsuario historicoEstadoEmocionalUsuario = new HistoricoEstadoEmocionalUsuario();
            historicoEstadoEmocionalUsuario.setEstadoEmocional(novoEstado);
            historicoEstadoEmocionalUsuario.setUsuario(this);
            historicoEstadosEmocionais.add(historicoEstadoEmocionalUsuario);
        }
    }

    public boolean isConfiguracaoPrivacidadeAtiva(ConfiguracaoPrivacidade configuracao) {
        return this.configuracoesPrivacidade.contains(configuracao);
    }

    public void alterarFotoPerfil(String novaFotoUrl) {
        if (novaFotoUrl != null && !novaFotoUrl.isBlank()) {
            this.fotoPerfilUrl = novaFotoUrl;
        }
    }

    public boolean isPlanoPremium() {
        return this.plano.equals(Plano.PREMIUM);
    }

    public void alterarPreferenciasNotificacao(List<TipoNotificacao> novasPreferencias) {
        if (novasPreferencias != null) {
            this.preferenciasNotificacao.clear();
            this.preferenciasNotificacao.addAll(novasPreferencias);
        }
    }

    @Override
    public boolean equals(Object outroUsuario) {
        if (this == outroUsuario) {
            return true;
        }

        if (outroUsuario instanceof Usuario usuario) {
            return super.equals(usuario)
                && Objects.equals(email, usuario.email);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), email);
    }

}
