package br.com.resolveai.melodia.domain.exception;

public class UsuarioEmUsoException extends EntidadeEmUsoException {

    public UsuarioEmUsoException(Long usuarioId) {
        super(String.format("Usuário com o ID %d não pode ser removido, pois está em uso.", usuarioId));
    }

}
