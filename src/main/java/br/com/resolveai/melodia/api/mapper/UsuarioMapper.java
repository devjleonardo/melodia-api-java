package br.com.resolveai.melodia.api.mapper;

import br.com.resolveai.melodia.api.dto.request.UsuarioCadastroRequestDTO;
import br.com.resolveai.melodia.api.dto.response.UsuarioDTO;
import br.com.resolveai.melodia.domain.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioDTO converterUsuariotParaDTO(Usuario usuario);

    Usuario converterDTOParaUsuario(UsuarioCadastroRequestDTO dto);

}
