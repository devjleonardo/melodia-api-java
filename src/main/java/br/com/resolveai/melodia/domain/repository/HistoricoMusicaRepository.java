package br.com.resolveai.melodia.domain.repository;

import br.com.resolveai.melodia.domain.model.HistoricoMusica;
import br.com.resolveai.melodia.domain.model.Musica;
import br.com.resolveai.melodia.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoricoMusicaRepository extends JpaRepository<HistoricoMusica, Long> {

    HistoricoMusica findByUsuarioAndMusica(Usuario usuario, Musica musica);

}
