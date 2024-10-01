package br.com.resolveai.melodia.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PlaylistDTO {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Rock Clássico")
    private String nome;

    @Schema(example = "Uma seleção dos melhores hits do rock clássico.")
    private String descricao;

    @Schema(example = "2024-01-01")
    private LocalDate dataCriacao;

    @Schema(example = "2024-08-30")
    private LocalDate dataAtualizacao;

    @Schema(example = "true")
    private Boolean publica;

    @Schema(example = "https://cirandapoetrix.com.br/storage/cu1opzA2HwRrbUwPpUoGcBqpCUTkOlVR9xqZHZi2.jpeg")
    private String imagemUrl;

    @Schema(example = "15000")
    private Integer numeroSeguidores;

    @Schema(example = "50")
    private Integer numeroMusicas;

    @Schema(example = "180")
    private Integer duracaTotal;

    @Schema(example = "false")
    private Boolean colaborativa;

    @Schema(example = "Rock")
    private String genero;

}
