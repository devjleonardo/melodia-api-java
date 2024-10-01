package br.com.resolveai.melodia.domain.repository;

import br.com.resolveai.melodia.domain.model.UsuarioArtistaRanking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioArtistaRankingRepository extends JpaRepository<UsuarioArtistaRanking, Long> {

    UsuarioArtistaRanking findByUsuarioIdAndArtistaId(Long usuarioId, Long artistaId);
    Page<UsuarioArtistaRanking> findByUsuarioId(Long usuarioId, Pageable pageable);

}
