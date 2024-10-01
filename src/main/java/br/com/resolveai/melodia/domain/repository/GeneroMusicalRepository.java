package br.com.resolveai.melodia.domain.repository;

import br.com.resolveai.melodia.domain.model.GeneroMusical;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneroMusicalRepository extends JpaRepository<GeneroMusical, Long> {

}
