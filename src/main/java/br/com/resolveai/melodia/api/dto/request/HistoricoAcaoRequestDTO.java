package br.com.resolveai.melodia.api.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HistoricoAcaoRequestDTO {

    private Long usuarioId;
    private Long itemId;
    private String tipoAcao;

}
