package br.com.resolveai.melodia.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioResumoDTO {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Andressa Alves")
    private String nome;

    @Schema(example = "https://andressa-foto-perfil.jpg")
    private String fotoPerfilUrl;

}
