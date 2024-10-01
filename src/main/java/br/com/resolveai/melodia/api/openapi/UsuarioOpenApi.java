package br.com.resolveai.melodia.api.openapi;

import br.com.resolveai.melodia.api.dto.request.UsuarioCadastroRequestDTO;
import br.com.resolveai.melodia.api.dto.response.UsuarioDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Tag(name = "Usuários")
public interface UsuarioOpenApi {

    @Operation(
        summary = "Lista todos os usuários",
        description = "Retorna todos os usuários cadastrados no sistema",
        responses = {
            @ApiResponse(responseCode = "200", description = "Listagem bem-sucedida")
        }
    )
    Page<UsuarioDTO> listar(Pageable pageable);

    @Operation(
        summary = "Busca um usuário por ID",
        description = "Retorna um usuário específico com base no ID fornecido",
        responses = {
            @ApiResponse(responseCode = "200", description = "Busca bem-sucedida"),
            @ApiResponse(responseCode = "400", description = "ID do usuário inválido"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
        }
    )
    UsuarioDTO buscarPorId(
        @Parameter(description = "ID do usuário a ser recuperado", example = "1", required = true)
        Long id
    );

    @Operation(
        summary = "Cadastra um novo usuário",
        description = "Cadastra um novo usuário no sistema",
        responses = {
            @ApiResponse(responseCode = "201", description = "Cadastro bem-sucedido"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
        }
    )
    UsuarioDTO cadastrar(
        @RequestBody(description = "Dados do novo usuário a ser cadastrado", required = true)
        UsuarioCadastroRequestDTO dto
    );

    @Operation(
        summary = "Altera o plano de um usuário",
        description = "Altera o plano de um usuário existente",
        responses = {
            @ApiResponse(responseCode = "200", description = "Plano alterado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
        }
    )
    UsuarioDTO alterarPlano(
        @Parameter(description = "ID do usuário a ser atualizado", example = "1", required = true)
        Long id,

        @Parameter(description = "Novo plano do usuário", example = "Premium", required = true)
        String plano
    );

    @Operation(
        summary = "Altera o estado emocional de um usuário",
        description = "Altera o estado emocional de um usuário existente",
        responses = {
            @ApiResponse(responseCode = "200", description = "Estado emocional alterado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
        }
    )
    UsuarioDTO alterarEstadoEmocional(
        @Parameter(description = "ID do usuário a ser atualizado", example = "1", required = true)
        Long id,

        @Parameter(description = "Novo estado emocional do usuário", example = "Feliz", required = true)
        String estadoEmocional
    );
//
//    @Operation(
//        summary = "Segue outro usuário",
//        description = "Permite que o usuário siga outro usuário",
//        responses = {
//            @ApiResponse(responseCode = "204", description = "Ação realizada com sucesso"),
//            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
//        }
//    )
//    void seguirUsuario(
//        @Parameter(description = "ID do usuário que vai seguir", example = "1", required = true)
//        Long id,
//
//        @Parameter(description = "ID do usuário a ser seguido", example = "2", required = true)
//        Long seguirId
//    );
//
//    @Operation(
//        summary = "Deixar de seguir outro usuário",
//        description = "Permite que o usuário deixe de seguir outro usuário",
//        responses = {
//            @ApiResponse(responseCode = "204", description = "Ação realizada com sucesso"),
//            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
//        }
//    )
//    void deixarDeSeguirUsuario(
//        @Parameter(description = "ID do usuário que vai deixar de seguir", example = "1", required = true)
//        Long id,
//
//        @Parameter(description = "ID do usuário a ser deixado de seguir", example = "2", required = true)
//        Long deixarDeSeguirId
//    );
//
//    @Operation(
//        summary = "Lista os seguidores de um usuário",
//        description = "Retorna uma lista de usuários que seguem o usuário específico",
//        responses = {
//            @ApiResponse(responseCode = "200", description = "Listagem de seguidores bem-sucedida"),
//            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
//        }
//    )
//    List<UsuarioDTO> listarSeguidores(
//        @Parameter(description = "ID do usuário para listar os seguidores", example = "1", required = true)
//        Long id
//    );
//
//    @Operation(
//        summary = "Lista os usuários que o usuário está seguindo",
//        description = "Retorna uma lista de usuários que o usuário específico está seguindo",
//        responses = {
//            @ApiResponse(responseCode = "200", description = "Listagem de usuários seguidos bem-sucedida"),
//            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
//        }
//    )
//    List<UsuarioDTO> listarSeguindo(
//        @Parameter(description = "ID do usuário para listar os seguindos", example = "1", required = true)
//        Long id
//    );

    @Operation(
        summary = "Remove um usuário por ID",
        description = "Remove um usuário específico com base no ID fornecido",
        responses = {
            @ApiResponse(responseCode = "204", description = "Remoção bem-sucedida"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
        }
    )
    void remover(
        @Parameter(description = "ID do usuário a ser removido", example = "1", required = true)
        Long id
    );

}
