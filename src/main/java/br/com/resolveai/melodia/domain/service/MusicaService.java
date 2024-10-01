package br.com.resolveai.melodia.domain.service;

import br.com.resolveai.melodia.domain.exception.EntidadeEmUsoException;
import br.com.resolveai.melodia.domain.exception.EntidadeNaoEncontradaException;
import br.com.resolveai.melodia.domain.model.Musica;
import br.com.resolveai.melodia.domain.repository.MusicaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MusicaService {

    private static final String MSG_MUSICA_NAO_ENCONTRADA = "Música não encontrada com o ID %d";
    private static final String MSG_MUSICA_EM_USO = "Música com o ID %d não pode ser removida, pois está em uso.";

    private final MusicaRepository musicaRepository;

    public List<Musica> listarTodasMusicas() {
        return musicaRepository.findAll();
    }

    public Page<Musica> listarTodasMusicasPaginadas(Pageable pageable) {
        return musicaRepository.findAll(pageable);
    }

    public Musica buscarMusicaPorId(Long id) {
        return musicaRepository.findById(id)
            .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format(MSG_MUSICA_NAO_ENCONTRADA, id)));
    }

    @Transactional
    public Musica salvarMusica(Musica musica) {
        return musicaRepository.save(musica);
    }

    @Transactional
    public void removerMusicaPorId(Long id) {
        try {
            Musica musica = buscarMusicaPorId(id);
            musicaRepository.delete(musica);
            musicaRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                String.format(MSG_MUSICA_EM_USO, id)
            );
        }
    }

    public List<Musica> listarMusicasPopulares() {
        return musicaRepository.findAllByOrderByPopularidadeDesc();
    }

    public List<Musica> listarMusicasRecentes() {
        return musicaRepository.findAllByOrderByDataLancamentoDesc();
    }

    public List<Musica> pesquisarMusicas(String titulo, String artista, String album, String genero, LocalDate dataLancamento, int pagina, int tamanhoPagina) {
        List<Musica> musicasExatas = buscarMusicasExatas(titulo, artista, album, genero, dataLancamento, pagina, tamanhoPagina);

        if (!musicasExatas.isEmpty()) {
            return musicasExatas;
        }

        return fuzzySearchMusicas(titulo, artista, album, genero, pagina, tamanhoPagina);
    }

    private List<Musica> buscarMusicasExatas(String titulo, String artista, String album, String genero,
            LocalDate dataLancamento, int pagina, int tamanhoPagina) {
        return musicaRepository.searchMusic(titulo, artista, album, genero, dataLancamento,
            PageRequest.of(pagina, tamanhoPagina)).getContent();
    }

    private List<Musica> fuzzySearchMusicas(String titulo, String artista, String album, String genero, int pagina,
            int tamanhoPagina) {
        Page<Musica> todasMusicas = musicaRepository.findAll(PageRequest.of(pagina, tamanhoPagina));
        return musicaRepository.searchBySimilarity(titulo, artista, album, genero, todasMusicas.getContent());
    }

}
