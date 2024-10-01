package br.com.resolveai.melodia.domain.service;


import br.com.resolveai.melodia.domain.exception.EntidadeEmUsoException;
import br.com.resolveai.melodia.domain.exception.EntidadeNaoEncontradaException;
import br.com.resolveai.melodia.domain.model.Musica;
import br.com.resolveai.melodia.domain.model.Playlist;
import br.com.resolveai.melodia.domain.repository.PlaylistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlaylistService {

    private static final String MSG_PLAYLIST_NAO_ENCONTRADA = "Playlist não encontrada com o ID %d";
    private static final String MSG_PLAYLIST_EM_USO = "Playlist com o ID %d não pode ser removida, pois está em uso.";

    private final PlaylistRepository playlistRepository;
    private final MusicaService musicaService;

    public Page<Playlist> listarTodasPlaylistsPaginadas(Pageable pageable) {
        return playlistRepository.findAll(pageable);
    }

    public Playlist buscarPlaylistPorId(Long id) {
        return playlistRepository.findById(id).
                orElseThrow(() -> new EntidadeNaoEncontradaException(String.format(MSG_PLAYLIST_NAO_ENCONTRADA, id)));
    }

    @Transactional
    public Playlist salvarPlaylist(Playlist playList) {
        return playlistRepository.save(playList);
    }

    @Transactional
    public void removerPlaylistPorId(Long id) {
        try {
            Playlist playlist = buscarPlaylistPorId(id);
            playlistRepository.delete(playlist);
            playlistRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_PLAYLIST_EM_USO, id)
            );
        }
    }

    @Transactional
    public void adicionarMusica(Long id, Long musicaId) {

        Playlist playlist = buscarPlaylistPorId(id);

        Musica musica = musicaService.buscarMusicaPorId(musicaId);

        playlist.adicionarMusica(musica);
        salvarPlaylist(playlist);
    }

}
