package br.com.resolveai.melodia.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
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
public class Tag extends EntidadeBase {

    @Column(nullable = false, length = 50)
    private String nome;

    @ManyToMany(mappedBy = "tags")
    private Set<Musica> musicas = new HashSet<>();

    @Override
    public boolean equals(Object outraTag) {
        return super.equals(outraTag);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}

