package br.com.resolveai.melodia.domain.exception;

public class EmailJaCadastradoException extends RegraNegocioException {

    public EmailJaCadastradoException(String email) {
        super(String.format("Já existe um usuário cadastrado com o e-mail: %s", email));
    }

}
