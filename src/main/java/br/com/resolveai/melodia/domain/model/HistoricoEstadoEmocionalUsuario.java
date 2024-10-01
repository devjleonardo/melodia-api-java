package br.com.resolveai.melodia.domain.model;

import br.com.resolveai.melodia.domain.model.enums.EstadoEmocional;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HistoricoEstadoEmocionalUsuario extends EntidadeBase {

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    private EstadoEmocional estadoEmocional;

    private LocalDateTime data;

    @Override
    public boolean equals(Object outroEstadoEmocionalUsuario) {
        return super.equals(outroEstadoEmocionalUsuario);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}

