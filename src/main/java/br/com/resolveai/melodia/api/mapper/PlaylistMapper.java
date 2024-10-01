package br.com.resolveai.melodia.api.mapper;

import br.com.resolveai.melodia.api.dto.request.AtualizarPlaylistDTO;
import br.com.resolveai.melodia.api.dto.request.CadastrarPlaylistDTO;
import br.com.resolveai.melodia.api.dto.response.PlaylistDTO;
import br.com.resolveai.melodia.domain.model.Playlist;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PlaylistMapper {

    PlaylistDTO converterPlaylistParaDTO(Playlist playlist);

    Playlist converteDTOParaPlaylist(CadastrarPlaylistDTO dto);

    void atualizarPlaylistComDadosDoRequestDTO(AtualizarPlaylistDTO dto, @MappingTarget Playlist playlist);


}
