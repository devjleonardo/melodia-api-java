package br.com.resolveai.melodia.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MusicaRequestDTO {

    @Schema(example = "Bohemian Rhapsody")
    private String titulo;

    @Schema(example = "A Night at the Opera")
    private String album;

    @Schema(example = "354")
    private Integer duracao;

    @Schema(example = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQNdWwRUUNosIcgjMXJv6mu3tHwYzWus3X-Nw&s")
    private String capaUrl;

    @Schema(example = "95")
    private Integer popularidade;

}
