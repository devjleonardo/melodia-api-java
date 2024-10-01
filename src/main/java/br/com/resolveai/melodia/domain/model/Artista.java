package br.com.resolveai.melodia.domain.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Artista extends EntidadeBase {

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private Long execucoes = 0L;

    private String fotoUrl;

    @OneToMany(mappedBy = "artista", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Musica> musicas;

    @Override
    public boolean equals(Object outroArtista) {
        return super.equals(outroArtista);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}

