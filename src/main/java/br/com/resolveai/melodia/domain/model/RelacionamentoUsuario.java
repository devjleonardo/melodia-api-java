package br.com.resolveai.melodia.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class RelacionamentoUsuario extends EntidadeBase {

    @ManyToOne
    @JoinColumn(name = "seguidor_id")
    private Usuario seguidor;

    @ManyToOne
    @JoinColumn(name = "seguido_id")
    private Usuario seguido;

    private LocalDateTime dataInicio;

    @Override
    public boolean equals(Object outroRelacionamentoUsuario) {
        return super.equals(outroRelacionamentoUsuario);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}

