package br.com.resolveai.melodia.api.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MusicaOuvidaRequestDTO {

    @NotNull
    private Long usuarioId;

    @NotNull
    private Long musicaId;

}
