package br.com.resolveai.melodia.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PreferenciasMusicais extends EntidadeBase {

    @Column(nullable = false, length = 100)
    private String genero;

    private LocalDate dataInicio;

    @Column(nullable = false)
    private Integer rankingPopularidade = 0;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    // Método para atualizar o ranking com base na interação
    public void incrementarRankingPopularidade(int pontos) {
        this.rankingPopularidade += pontos;
    }

    @Override
    public boolean equals(Object outrasPreferenciasMusicais) {
        return super.equals(outrasPreferenciasMusicais);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
