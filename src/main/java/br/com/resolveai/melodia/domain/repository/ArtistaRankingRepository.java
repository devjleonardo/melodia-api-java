package br.com.resolveai.melodia.domain.repository;

import br.com.resolveai.melodia.domain.model.ArtistaRanking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistaRankingRepository extends JpaRepository<ArtistaRanking, Long> {

    ArtistaRanking findByArtistaId(Long artistaId);

}
