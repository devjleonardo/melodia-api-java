package br.com.resolveai.melodia.api.openapi;

import br.com.resolveai.melodia.api.dto.request.MusicaRequestDTO;
import br.com.resolveai.melodia.api.dto.response.MusicaDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Músicas")
public interface MusicaOpenApi {

    @Operation(
        summary = "Lista todas as músicas",
        description = "Retorna todas as músicas cadastradas",
        responses = {
            @ApiResponse(responseCode = "200", description = "Listagem bem-sucedida")
        }
    )
    Page<MusicaDTO> listar(Pageable pageable);

    @Operation(
        summary = "Busca uma música por ID",
        description = "Retorna uma música específica com base no ID fornecido",
        responses = {
            @ApiResponse(responseCode = "200", description = "Busca bem-sucedida"),
            @ApiResponse(responseCode = "400", description = "ID da música inválido",
                content = @Content(schema = @Schema(ref = "Erro"))),
            @ApiResponse(responseCode = "404", description = "Música não encontrada",
                content = @Content(schema = @Schema(ref = "Erro")))
        }
    )
    MusicaDTO buscarPorId(
        @Parameter(
            description = "ID da música a ser recuperada",
            example = "1",
            required = true
        )
        Long id
    );

    @Operation(
        summary = "Cadastra uma nova música",
        description = "Cadastra uma nova música no sistema",
        responses = {
            @ApiResponse(responseCode = "201", description = "Cadastro bem-sucedido"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                content = @Content(schema = @Schema(ref = "Erro")))
        }
    )
    MusicaDTO cadastrar(
        @RequestBody(
            description = "Objeto contendo os dados necessários para o cadastro de uma nova música"
        )
        MusicaRequestDTO dto
    );

    @Operation(
        summary = "Atualiza uma música existente",
        description = "Atualiza os detalhes de uma música específica com base no ID fornecido",
        responses = {
            @ApiResponse(responseCode = "200", description = "Atualização bem-sucedida"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                content = @Content(schema = @Schema(ref = "Erro"))),
            @ApiResponse(responseCode = "404", description = "Música não encontrada",
                content = @Content(schema = @Schema(ref = "Erro")))
        }
    )
    MusicaDTO atualizar(
        @Parameter(
            description = "ID da música a ser atualizada",
            example = "1",
            required = true
        )
        Long id,

        @RequestBody(
            description = "Objeto contendo os dados necessários para atualizar as informações da música existente"
        )
        MusicaRequestDTO dto
    );

    @Operation(
        summary = "Remove uma música por ID",
        description = "Remove uma música específica com base no ID fornecido",
        responses = {
            @ApiResponse(responseCode = "204", description = "Remoção bem-sucedida"),
            @ApiResponse(responseCode = "404", description = "Música não encontrada",
                content = @Content(schema = @Schema(ref = "Erro")))
        }
    )
    void remover(
        @Parameter(
            description = "ID da música a ser removida",
            example = "1",
            required = true
        )
        Long id
    );

    @Operation(
        summary = "Lista músicas populares",
        description = "Retorna uma lista de músicas populares",
        responses = {
            @ApiResponse(responseCode = "200", description = "Listagem bem-sucedida")
        }
    )
    List<MusicaDTO> listarMusicasPopulares();

    @Operation(
        summary = "Lista músicas recentes",
        description = "Retorna uma lista de músicas recentes",
        responses = {
            @ApiResponse(responseCode = "200", description = "Listagem bem-sucedida")
        }
    )
    List<MusicaDTO> listarMusicasRecentes();

    @Operation(
        summary = "Pesquisa músicas",
        description = "Pesquisa músicas com base nos critérios fornecidos",
        responses = {
            @ApiResponse(responseCode = "200", description = "Pesquisa bem-sucedida")
        }
    )
    List<MusicaDTO> pesquisarMusicas(
        @Parameter(description = "Título da música", example = "Bohemian Rhapsody") String titulo,
        @Parameter(description = "Artista da música", example = "Queen") String artista,
        @Parameter(description = "Álbum da música", example = "A Night at the Opera") String album,
        @Parameter(description = "Gênero da música", example = "Rock") String genero,
        @Parameter(description = "Data de lançamento da música", example = "1975-11-21") LocalDate dataLancamento,
        @Parameter(description = "Número da página para a paginação", example = "0") int pagina,
        @Parameter(description = "Tamanho da página para a paginação", example = "10") int tamanhoPagina
    );

}
