package br.com.resolveai.melodia.domain.repository;

import br.com.resolveai.melodia.domain.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoricoAcaoRepository extends JpaRepository<HistoricoAcao, Long> {

    Page<HistoricoAcao> findByUsuarioIdOrderByDataAcaoDesc(Long usuarioId, Pageable pageable);

}
