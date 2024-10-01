package br.com.resolveai.melodia.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Musica extends EntidadeBase {

    @Column(columnDefinition = "TEXT")
    private String titulo;

    @Column(length = 100)
    private String album;

    private Integer duracao;

    @Column(columnDefinition = "TEXT")
    private String capaUrl;

    @Column(columnDefinition = "TEXT")
    private String audioUrl;

    private LocalDate dataLancamento;

    private Integer popularidade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artista_id", nullable = false)
    private Artista artista;

    @ManyToOne
    @JoinColumn(name = "genero_musical_id")
    private GeneroMusical generoMusical;

    @ManyToMany
    @JoinTable(name = "musica_tags",
        joinColumns = @JoinColumn(name = "musica_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<>();

    @Override
    public boolean equals(Object outraMusica) {
        return super.equals(outraMusica);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
