package br.com.resolveai.melodia.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Playlist extends EntidadeBase {

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(length = 256)
    private String descricao;

    private LocalDate dataAtualizacao;

    private Boolean publica;

    private String imagemUrl;

    private Integer numeroSeguidores = 0;

    private Integer numeroMusicas;

    private Integer duracaoTotal;

    private Boolean colaborativa;

    @ManyToOne
    @JoinColumn(name = "genero_musical_id")
    private GeneroMusical generoMusical;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToMany
    @JoinTable(name = "playlists_musicas",
        joinColumns = @JoinColumn(name = "playlist_id"),
        inverseJoinColumns = @JoinColumn(name = "musica_id"))
    private Set<Musica> musicas = new HashSet<>();

    @OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL)
    private List<ParticipantesPlaylist> participantes = new ArrayList<>();

    public void adicionarMusica(Musica musica) {
        musicas.add(musica);
    }

    @Override
    public boolean equals(Object outraPlaylist) {
        return super.equals(outraPlaylist);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
