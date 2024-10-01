package br.com.resolveai.melodia.domain.service;


import br.com.resolveai.melodia.domain.model.HistoricoMusica;
import br.com.resolveai.melodia.domain.model.HistoricoPlaylist;
import br.com.resolveai.melodia.domain.model.Playlist;
import br.com.resolveai.melodia.domain.model.Usuario;
import br.com.resolveai.melodia.domain.repository.HistoricoPlaylistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HistoricoPlaylistService {

    private final HistoricoPlaylistRepository historicoPlaylistRepository;

    public HistoricoPlaylist buscarHistoricoPorUsuarioEPlaylist(Usuario usuario, Playlist playlist) {
        return historicoPlaylistRepository.findByUsuarioAndPlaylist(usuario, playlist);
    }

}
