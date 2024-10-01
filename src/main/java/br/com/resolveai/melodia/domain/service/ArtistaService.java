package br.com.resolveai.melodia.domain.service;


import br.com.resolveai.melodia.domain.exception.ArtistaNaoEncontradoException;
import br.com.resolveai.melodia.domain.exception.EntidadeEmUsoException;
import br.com.resolveai.melodia.domain.model.Artista;
import br.com.resolveai.melodia.domain.projecao.ArtistaRankingProjecao;
import br.com.resolveai.melodia.domain.repository.ArtistaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArtistaService {
    private static final String MSG_PLAYLIST_EM_USO = "Artista com o ID %d não pode ser removida, pois está em uso.";

    private final ArtistaRepository artistaRepository;

    public List<Artista> listarTodosArtistas() {
        return artistaRepository.findAll();
    }

    public Artista buscarArtistaPorId(Long id) {
        return artistaRepository.findById(id).
                orElseThrow(() -> new ArtistaNaoEncontradoException(id));
    }

    @Transactional(readOnly = true)
    public ArtistaRankingProjecao obterResumoArtistaPorId(Long id) {
        return artistaRepository.findResumoDeArtistaPorId(id)
            .orElseThrow(() -> new ArtistaNaoEncontradoException(id));
    }

    @Transactional
    public Artista salvarArtista(Artista artista) {
        return artistaRepository.save(artista);
    }

    @Transactional
    public void removerArtistaPorId(Long id) {
        try {
            Artista artista = buscarArtistaPorId(id);
            artistaRepository.delete(artista);
            artistaRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_PLAYLIST_EM_USO, id)
            );
        }
    }

}
