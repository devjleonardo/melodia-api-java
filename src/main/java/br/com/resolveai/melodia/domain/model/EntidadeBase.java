package br.com.resolveai.melodia.domain.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@MappedSuperclass
@Getter
@Setter
public abstract class EntidadeBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime dataCriacao;

    @Override
    public boolean equals(Object outraEntidade) {
        if (this == outraEntidade) {
            return true;
        }

        if (outraEntidade instanceof EntidadeBase entidadeBase) {
            return Objects.equals(id, entidadeBase.id);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
