package br.com.resolveai.melodia.api.mapper;

import br.com.resolveai.melodia.api.dto.response.ArtistaRankingDTO;
import br.com.resolveai.melodia.domain.model.ArtistaRanking;
import br.com.resolveai.melodia.domain.model.UsuarioArtistaRanking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ArtistaRankingMapper {

    @Mapping(source = "artista.id", target = "artistaId")
    @Mapping(source = "artista.nome", target = "nomeArtista")
    @Mapping(source = "artista.fotoUrl", target = "artistaFotoUrl")
    ArtistaRankingDTO converterArtistaRankingParaDTO(ArtistaRanking artistaRanking);

    @Mapping(source = "artista.id", target = "artistaId")
    @Mapping(source = "artista.nome", target = "nomeArtista")
    @Mapping(source = "artista.fotoUrl", target = "artistaFotoUrl")
    ArtistaRankingDTO converterUsuarioArtistaRankingParaDTO(UsuarioArtistaRanking usuarioArtistaRanking);

}
