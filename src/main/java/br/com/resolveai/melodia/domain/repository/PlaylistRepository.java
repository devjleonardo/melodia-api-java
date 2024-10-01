package br.com.resolveai.melodia.domain.repository;

import br.com.resolveai.melodia.domain.model.GeneroMusical;
import br.com.resolveai.melodia.domain.model.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

    boolean existsByGeneroMusicalAndUsuarioIsNull(GeneroMusical generoMusical);

}
