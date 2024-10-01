package br.com.resolveai.melodia.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdicionarMusicaPlaylistDTO {

    @Schema(example = "98")
    @NotNull
    private Long musicaId;

}
