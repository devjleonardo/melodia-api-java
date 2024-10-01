package br.com.resolveai.melodia.domain.exception;

import java.io.Serial;

public class RegraNegocioException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = 1L;

	public RegraNegocioException(String mensagem) {
		super(mensagem);
	}

}
