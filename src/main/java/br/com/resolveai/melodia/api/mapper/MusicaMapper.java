package br.com.resolveai.melodia.api.mapper;

import br.com.resolveai.melodia.api.dto.request.MusicaRequestDTO;
import br.com.resolveai.melodia.api.dto.response.MusicaDTO;
import br.com.resolveai.melodia.domain.model.Musica;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MusicaMapper {

    @Mapping(source = "generoMusical.nome", target = "genero")
    @Mapping(
        target = "tags",
        expression = "java(musica.getTags().stream().map(tag -> tag.getNome()).collect(java.util.stream.Collectors.toSet()))"
    )
    @Mapping(source = "artista.nome", target = "artista") // Apenas o nome do artista
    MusicaDTO converterMusicaParaDTO(Musica musica);

    // Aqui não é necessário mapear o nome do artista, pois o ID será utilizado internamente
    Musica converteDTOParaMusica(MusicaRequestDTO dto);

    void atualizarMusicaComDadosDoRequestDTO(MusicaRequestDTO dto, @MappingTarget Musica musica);

}

