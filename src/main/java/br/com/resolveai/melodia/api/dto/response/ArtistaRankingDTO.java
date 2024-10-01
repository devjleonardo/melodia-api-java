package br.com.resolveai.melodia.api.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArtistaRankingDTO {

    private Long artistaId;
    private String nomeArtista;
    private String artistaFotoUrl;

}
