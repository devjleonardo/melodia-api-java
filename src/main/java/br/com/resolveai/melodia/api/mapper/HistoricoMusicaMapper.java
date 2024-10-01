package br.com.resolveai.melodia.api.mapper;

import br.com.resolveai.melodia.api.dto.response.HistoricoMusicaDTO;
import br.com.resolveai.melodia.domain.projecao.HistoricoMusicaProjecao;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HistoricoMusicaMapper {

    HistoricoMusicaDTO converterProjecaoParaDTO(HistoricoMusicaProjecao projecao);

}
