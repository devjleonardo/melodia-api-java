package br.com.resolveai.melodia.importacao.model;

import br.com.resolveai.melodia.importacao.model.enums.IdentificadorImportacao;
import br.com.resolveai.melodia.importacao.model.enums.TipoImportacao;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ImportacaoStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private IdentificadorImportacao identificador;

    private TipoImportacao tipo;

    private boolean concluida;

}
