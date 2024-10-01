package br.com.resolveai.melodia.domain.repository;

import br.com.resolveai.melodia.domain.model.HistoricoPlaylist;
import br.com.resolveai.melodia.domain.model.Playlist;
import br.com.resolveai.melodia.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoricoPlaylistRepository extends JpaRepository<HistoricoPlaylist, Long> {

    HistoricoPlaylist findByUsuarioAndPlaylist(Usuario usuario, Playlist playlist);

}
