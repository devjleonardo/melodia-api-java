package br.com.resolveai.melodia.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ArtistaRanking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double pontuacao;

    @ManyToOne
    @JoinColumn(name = "artista_id", nullable = false, unique = true)
    private Artista artista;

    @Override
    public boolean equals(Object outraTag) {
        return super.equals(outraTag);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
