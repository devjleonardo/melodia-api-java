package br.com.resolveai.melodia.domain.model;

import br.com.resolveai.melodia.domain.model.enums.BaseRecomendacao;
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
public class RecomendacaoMusica extends EntidadeBase {

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "musica_id")
    private Musica musica;

    @Enumerated(EnumType.STRING)
    private BaseRecomendacao baseRecomendacao;

    private LocalDateTime dataRecomendacao;

    @Override
    public boolean equals(Object outraRecomendacaoMusica) {
        return super.equals(outraRecomendacaoMusica);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}

