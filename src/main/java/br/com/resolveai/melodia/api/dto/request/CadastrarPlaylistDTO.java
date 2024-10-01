package br.com.resolveai.melodia.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CadastrarPlaylistDTO {

    @NotBlank
    @Schema(example = "Rock Clássico")
    private String nome;

}
