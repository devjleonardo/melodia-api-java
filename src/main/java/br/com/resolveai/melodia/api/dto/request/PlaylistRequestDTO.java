package br.com.resolveai.melodia.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaylistRequestDTO {

    @NotBlank
    @Schema(example = "Minhas Favoritas")
    private String nome;

    @Schema(example = "Playlist com as minhas músicas favoritas de todos os tempos.")
    private String descricao;

    @Schema(example = "true")
    private Boolean publica;

    @Schema(example = "https://i1.sndcdn.com/artworks-kgdN0QdA9nrzkynk-34QCYQ-t500x500.jpg")
    private String imagemUrl;

    @Schema(example = "50")
    private Integer numeroMusicas;

    @Schema(example = "180")
    private Integer duracaTotal;

    @Schema(example = "false")
    private Boolean colaborativa;

    @Schema(example = "Rock")
    private String genero;

}
