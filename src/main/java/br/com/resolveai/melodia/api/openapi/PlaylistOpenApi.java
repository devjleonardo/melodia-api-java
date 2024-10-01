package br.com.resolveai.melodia.api.openapi;

import br.com.resolveai.melodia.api.dto.request.AtualizarPlaylistDTO;
import br.com.resolveai.melodia.api.dto.request.CadastrarPlaylistDTO;
import br.com.resolveai.melodia.api.dto.response.PlaylistDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Tag(name = "Playlists")
public interface PlaylistOpenApi {

    @Operation(
        summary = "Lista todas as playlists",
        description = "Retorna todas as playlists cadastradas",
        responses = {
            @ApiResponse(responseCode = "200", description = "Listagem bem-sucedida")
        }
    )
    Page<PlaylistDTO> listar(Pageable pageable);

    @Operation(
        summary = "Busca uma playlist por ID",
        description = "Retorna uma playlist específica com base no ID fornecido",
        responses = {
            @ApiResponse(responseCode = "200", description = "Busca bem-sucedida"),
            @ApiResponse(responseCode = "400", description = "ID da playlist inválido",
                content = @Content(schema = @Schema(ref = "Erro"))),
            @ApiResponse(responseCode = "404", description = "Playlist não encontrada",
                content = @Content(schema = @Schema(ref = "Erro")))
        }
    )
    PlaylistDTO buscarPorId(
        @Parameter(
            description = "ID da playlist a ser recuperada",
            example = "1",
            required = true
        )
        Long id
    );

    @Operation(
        summary = "Cadastra uma nova playlist",
        description = "Cadastra uma nova playlist no sistema",
        responses = {
            @ApiResponse(responseCode = "201", description = "Cadastro bem-sucedido"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                content = @Content(schema = @Schema(ref = "Erro")))
        }
    )
    PlaylistDTO cadastrar(
        @RequestBody(
            description = "Objeto contendo os dados necessários para o cadastro de uma nova playlist"
        )
        CadastrarPlaylistDTO dto
    );

    @Operation(
        summary = "Atualiza uma playlist existente",
        description = "Atualiza os detalhes de uma playlist específica com base no ID fornecido",
        responses = {
            @ApiResponse(responseCode = "200", description = "Atualização bem-sucedida"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                content = @Content(schema = @Schema(ref = "Erro"))),
            @ApiResponse(responseCode = "404", description = "Playlist não encontrada",
                content = @Content(schema = @Schema(ref = "Erro")))
        }
    )
    PlaylistDTO atualizar(
        @Parameter(
            description = "ID da playlist a ser atualizada",
            example = "1",
            required = true
        )
        Long id,

        @RequestBody(
            description = "Objeto contendo os dados necessários para atualizar as informações da playlist existente"
        )
        AtualizarPlaylistDTO dto
    );

    @Operation(
        summary = "Remove uma playlist por ID",
        description = "Remove uma playlist específica com base no ID fornecido",
        responses = {
            @ApiResponse(responseCode = "204", description = "Remoção bem-sucedida"),
            @ApiResponse(responseCode = "404", description = "Playlist não encontrada",
                content = @Content(schema = @Schema(ref = "Erro")))
        }
    )
    void remover(
        @Parameter(
            description = "ID da playlist a ser removida",
            example = "1",
            required = true
        )
        Long id
    );

}
