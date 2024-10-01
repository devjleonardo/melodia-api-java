package br.com.resolveai.melodia.api.controller;


import br.com.resolveai.melodia.api.dto.request.UsuarioCadastroRequestDTO;
import br.com.resolveai.melodia.api.dto.response.UsuarioDTO;
import br.com.resolveai.melodia.api.mapper.UsuarioMapper;
import br.com.resolveai.melodia.api.openapi.UsuarioOpenApi;
import br.com.resolveai.melodia.core.util.ApiEndpoint;
import br.com.resolveai.melodia.domain.model.Usuario;
import br.com.resolveai.melodia.domain.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiEndpoint.USUARIOS)
@RequiredArgsConstructor
public class UsuarioController implements UsuarioOpenApi {

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    @Override
    @GetMapping
    public Page<UsuarioDTO> listar(@PageableDefault Pageable pageable) {
        return usuarioService.listarTodosUsuariosPaginados(pageable)
            .map(usuarioMapper::converterUsuariotParaDTO);
    }

    @Override
    @GetMapping("/{id}")
    public UsuarioDTO buscarPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscarUsuarioPorId(id);
        return usuarioMapper.converterUsuariotParaDTO(usuario);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioDTO cadastrar(@RequestBody @Valid UsuarioCadastroRequestDTO dto) {
        Usuario usuario = usuarioMapper.converterDTOParaUsuario(dto);
        Usuario usuarioSalvo = usuarioService.salvarUsuario(usuario);
        return usuarioMapper.converterUsuariotParaDTO(usuarioSalvo);
    }

//    @Override
//    @GetMapping("/{id}/seguidores")
//    public List<UsuarioDTO> listarSeguidores(@PathVariable Long id) {
//        return usuarioService.listarSeguidores(id).stream()
//            .map(usuarioMapper::converterUsuariotParaDTO)
//            .toList();
//    }
//
//    @Override
//    @GetMapping("/{id}/seguindo")
//    public List<UsuarioDTO> listarSeguindo(@PathVariable Long id) {
//        return usuarioService.listarSeguindo(id).stream()
//            .map(usuarioMapper::converterUsuariotParaDTO)
//            .toList();
//    }

    @Override
    @PatchMapping("/{id}/plano")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioDTO alterarPlano(@PathVariable Long id, @RequestParam String plano) {
        Usuario usuarioAtualizado = usuarioService.alterarPlano(id, plano);
        return usuarioMapper.converterUsuariotParaDTO(usuarioAtualizado);
    }

    @Override
    @PatchMapping("/{id}/estado-emocional")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioDTO alterarEstadoEmocional(@PathVariable Long id, @RequestParam String estadoEmocional) {
        Usuario usuarioAtualizado = usuarioService.alterarEstadoEmocional(id, estadoEmocional);
        return usuarioMapper.converterUsuariotParaDTO(usuarioAtualizado);
    }

//    @Override
//    @PostMapping("/{id}/seguindo/{seguirId}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void seguirUsuario(@PathVariable Long id, @PathVariable Long seguirId) {
//        usuarioService.seguirUsuario(id, seguirId);
//    }
//
//    @Override
//    @DeleteMapping("/{id}/seguindo/{deixarDeSeguirId}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void deixarDeSeguirUsuario(@PathVariable Long id, @PathVariable Long deixarDeSeguirId) {
//        usuarioService.deixarDeSeguirUsuario(id, deixarDeSeguirId);
//    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        usuarioService.removerUsuarioPorId(id);
    }

}

