package br.com.resolveai.melodia.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ArtistaRankingDTO {

    private Long artistaId;
    private String nomeArtista;
    private String artistaFotoUrl;

}
