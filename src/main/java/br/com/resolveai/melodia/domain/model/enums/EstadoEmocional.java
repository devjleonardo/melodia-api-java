package br.com.resolveai.melodia.domain.model.enums;

import lombok.Getter;

@Getter
public enum EstadoEmocional {

    FELIZ("Feliz"),
    TRISTE("Triste"),
    MOTIVADO("Motivado"),
    RELAXADO("Relaxado"),
    ESTRESSADO("Estressado"),
    ANSIOSO("Ansioso"),
    CALMO("Calmo"),
    ENTEDIADO("Entediado"),
    INSPIRADO("Inspirado"),
    ENERGICO("Enérgico"),
    FOCADO("Focado"),
    MELANCOLICO("Melancólico"),
    APAIXONADO("Apaixonado"),
    AGITADO("Agitado"),
    EXCITADO("Excitado"),
    CONFUSO("Confuso"),
    DETERMINADO("Determinado"),
    FRUSTRADO("Frustrado"),
    ORGULHOSO("Orgulhoso"),
    DESANIMADO("Desanimado"),
    CONFIANTE("Confiante"),
    SURPRESO("Surpreso"),
    DECEPCIONADO("Decepcionado"),
    CULPADO("Culpado"),
    GRATO("Grato"),
    ESPERANCOSO("Esperançoso"),
    TENSO("Tenso"),
    SATISFEITO("Satisfeito"),
    INDIFERENTE("Indiferente"),
    OUTROS("Outros");

    private final String descricao;

    EstadoEmocional(String descricao) {
        this.descricao = descricao;
    }

}
