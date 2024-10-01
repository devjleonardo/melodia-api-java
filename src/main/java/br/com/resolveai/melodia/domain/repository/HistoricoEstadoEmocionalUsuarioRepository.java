package br.com.resolveai.melodia.domain.repository;

import br.com.resolveai.melodia.domain.model.HistoricoEstadoEmocionalUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HistoricoEstadoEmocionalUsuarioRepository extends JpaRepository<HistoricoEstadoEmocionalUsuario, Long> {

    Optional<HistoricoEstadoEmocionalUsuario> findTopByUsuarioIdOrderByDataDesc(Long usuarioId);

}
