package br.com.resolveai.melodia.domain.model.enums;

import lombok.Getter;

@Getter
public enum Plano {

    GRATUITO("Gratuito"),
    PREMIUM("Premium");

    private final String descricao;

    Plano(String descricao) {
        this.descricao = descricao;
    }


}
