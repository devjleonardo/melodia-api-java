package br.com.resolveai.melodia.domain.exception;

import br.com.resolveai.melodia.domain.model.enums.Plano;

import java.util.List;

public class PlanoInvalidoException extends RegraNegocioException {

    public PlanoInvalidoException(String plano) {
        super(String.format("Plano '%s' inválido. Os valores permitidos são: %s", plano, List.of(Plano.values())));
    }

}
