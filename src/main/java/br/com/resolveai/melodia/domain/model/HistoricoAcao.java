package br.com.resolveai.melodia.domain.model;

import br.com.resolveai.melodia.domain.model.enums.EstadoEmocional;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_acao", discriminatorType = DiscriminatorType.STRING)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public abstract class HistoricoAcao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EstadoEmocional estadoEmocional;

    private LocalDateTime dataAcao;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Override
    public boolean equals(Object outroHistoricoAcao) {
        if (this == outroHistoricoAcao) {
            return true;
        }

        if (outroHistoricoAcao instanceof HistoricoAcao historicoAcao) {
            return Objects.equals(id, historicoAcao.id);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
