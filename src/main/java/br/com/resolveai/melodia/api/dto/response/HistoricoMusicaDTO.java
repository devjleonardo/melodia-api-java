package br.com.resolveai.melodia.api.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class HistoricoMusicaDTO {

    private Long id;
    private Long musicaId;
    private String tituloMusica;
    private String nomeArtista;
    private String capaUrl;
    private String audioUrl;
    private LocalDateTime dataOuvida;

}
