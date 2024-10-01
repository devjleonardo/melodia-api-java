package br.com.resolveai.melodia.api.openapi;

import br.com.resolveai.melodia.api.dto.request.AdicionarMusicaPlaylistDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Músicas em Playlists")
public interface PlaylistMusicaOpenApi {

    @Operation(
        summary = "Adiciona uma música à playlist",
        description = "Adiciona uma música existente à playlist especificada",
        responses = {
            @ApiResponse(responseCode = "200", description = "Música adicionada com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID da playlist ou da música inválido",
                content = @Content(schema = @Schema(ref = "Erro"))),
            @ApiResponse(responseCode = "404", description = "Playlist ou música não encontrada",
                content = @Content(schema = @Schema(ref = "Erro")))
        }
    )
    void adicionarMusica(
        @Parameter(description = "ID da playlist à qual a música será adicionada", example = "1", required = true)
        Long id,

        @RequestBody(description = "Objeto contendo o ID da música a ser adicionada à playlist")
        AdicionarMusicaPlaylistDTO dto
    );

}