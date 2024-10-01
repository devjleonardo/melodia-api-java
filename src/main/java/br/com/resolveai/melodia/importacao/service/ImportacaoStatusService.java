package br.com.resolveai.melodia.importacao.service;

import br.com.resolveai.melodia.importacao.model.ImportacaoStatus;
import br.com.resolveai.melodia.importacao.model.enums.IdentificadorImportacao;
import br.com.resolveai.melodia.importacao.model.enums.TipoImportacao;
import br.com.resolveai.melodia.importacao.repository.ImportacaoStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImportacaoStatusService {

    private final ImportacaoStatusRepository importacaoStatusRepository;

    public boolean existeImportacaoConcluida(IdentificadorImportacao identificador, TipoImportacao tipo) {
        return importacaoStatusRepository.findByIdentificadorAndTipo(identificador, tipo)
            .filter(ImportacaoStatus::isConcluida)
            .isPresent();
    }

    public ImportacaoStatus criarNovaImportacao(IdentificadorImportacao identificador, TipoImportacao tipo) {
        ImportacaoStatus novaImportacao = new ImportacaoStatus();
        novaImportacao.setIdentificador(identificador);
        novaImportacao.setTipo(tipo);
        novaImportacao.setConcluida(false);
        return importacaoStatusRepository.save(novaImportacao);
    }

    public void atualizarImportacaoConcluida(ImportacaoStatus importacao) {
        importacao.setConcluida(true);
        importacaoStatusRepository.save(importacao);
    }

    public void atualizarImportacaoFalha(ImportacaoStatus importacao) {
        importacao.setConcluida(false);
        importacaoStatusRepository.save(importacao);
    }

}
