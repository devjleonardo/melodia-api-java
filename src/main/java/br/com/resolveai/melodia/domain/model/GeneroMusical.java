package br.com.resolveai.melodia.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GeneroMusical extends EntidadeBase {

    @Column(nullable = false, length = 50, unique = true)
    private String nome;

    @OneToMany(mappedBy = "generoMusical")
    private Set<Musica> musicas = new HashSet<>();

    @Override
    public boolean equals(Object outraPlaylist) {
        return super.equals(outraPlaylist);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
