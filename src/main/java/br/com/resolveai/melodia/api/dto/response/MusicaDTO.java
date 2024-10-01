package br.com.resolveai.melodia.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Schema(name = "Música")
public class MusicaDTO {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Shape of You")
    private String titulo;

    @Schema(example = "Ed Sheeran")
    private String artista;

    @Schema(example = "Divide")
    private String album;

    @Schema(example = "233")
    private Integer duracao;

    @Schema(example = "https://i.ytimg.com/vi/VwomfkFDvH4/maxresdefault.jpg")
    private String capaUrl;

    @Schema(example = "https://prod-1.storage.jamendo.com/?trackid=418721&format=mp31&from=1SSfHCXNjF2bJgDJfbBZuA%3D%3D%7CsRbEQe3nQHP25HQzJYsWvA%3D%3D")
    private String audioUrl;

    @Schema(example = "2017-01-06")
    private LocalDate dataLancamento;

    @Schema(example = "85")
    private Integer popularidade;

    @Schema(example = "Pop")
    private String genero;

    @Schema(example = "[\"Pop\", \"Acústico\"]")
    private Set<String> tags;

}

