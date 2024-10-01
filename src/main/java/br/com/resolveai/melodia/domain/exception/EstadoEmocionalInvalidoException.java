package br.com.resolveai.melodia.domain.exception;

import br.com.resolveai.melodia.domain.model.enums.EstadoEmocional;

import java.util.List;

public class EstadoEmocionalInvalidoException extends RegraNegocioException {

    public EstadoEmocionalInvalidoException(String estadoEmocional) {
        super(String.format("Estado emocional '%s' inválido. Os valores permitidos são: %s", estadoEmocional,
            List.of(EstadoEmocional.values())));
    }

}
