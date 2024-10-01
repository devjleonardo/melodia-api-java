package br.com.resolveai.melodia.api.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class HistoricoAcaoDTO {

    private Long id;
    private String tipoAcao;
    private MusicaDTO musica;
    private PlaylistDTO playlist;
    private LocalDateTime dataAcao;

}