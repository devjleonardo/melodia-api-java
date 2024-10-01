package br.com.resolveai.melodia.domain.exception;

public class ArtistaNaoEncontradoException extends EntidadeNaoEncontradaException {

    public ArtistaNaoEncontradoException(Long artistaId) {
        super(String.format("Artista não encontrado com o ID %d", artistaId));
    }

}
