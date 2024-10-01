package br.com.resolveai.melodia.api.mapper;

import br.com.resolveai.melodia.api.dto.response.ArtistaRankingDTO;
import br.com.resolveai.melodia.domain.projecao.ArtistaRankingProjecao;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArtistaRankingMapper {

    ArtistaRankingDTO converterProjecaoParaRankingDTO(ArtistaRankingProjecao projecao);

}
