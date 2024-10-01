package br.com.resolveai.melodia.domain.exception;

public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException {

    public UsuarioNaoEncontradoException(Long usuarioId) {
        super(String.format("Usuário não encontrado com o ID %d", usuarioId));
    }

}
