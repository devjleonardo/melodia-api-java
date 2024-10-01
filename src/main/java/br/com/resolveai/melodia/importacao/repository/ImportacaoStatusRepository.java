package br.com.resolveai.melodia.importacao.repository;

import br.com.resolveai.melodia.importacao.model.ImportacaoStatus;
import br.com.resolveai.melodia.importacao.model.enums.IdentificadorImportacao;
import br.com.resolveai.melodia.importacao.model.enums.TipoImportacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImportacaoStatusRepository extends JpaRepository<ImportacaoStatus, Long> {

    Optional<ImportacaoStatus> findByIdentificadorAndTipo(IdentificadorImportacao identificador, TipoImportacao tipo);

}
