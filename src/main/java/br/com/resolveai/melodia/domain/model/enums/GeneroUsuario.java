package br.com.resolveai.melodia.domain.model.enums;

import lombok.Getter;

@Getter
public enum GeneroUsuario {

    MASCULINO("Masculino"),
    MULHER("Mulher"),
    NAO_BINARIO("Não binário"),
    OUTRO("Outro"),
    PREFIRO_NAO_DIZER("Prefiro não dizer");

    private final String descricao;

    GeneroUsuario(String descricao) {
        this.descricao = descricao;
    }

}
