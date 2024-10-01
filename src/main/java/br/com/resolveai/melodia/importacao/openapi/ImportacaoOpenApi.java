package br.com.resolveai.melodia.importacao.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;

@Tag(name = "Importações")
public interface ImportacaoOpenApi {

//    @Operation(
//        summary = "Importar músicas no sistema através de uma API externa",
//        responses = {
//            @ApiResponse(responseCode = "200", description = "Músicas importadas com sucesso"),
//            @ApiResponse(responseCode = "500", description = "Ocorreu um erro ao importar as músicas")
//        }
//    )
//    @PostMapping("/musicas")
//    String importarMusicas();

//    @Operation(
//        summary = "Importar playlists fictícias no sistema",
//        responses = {
//            @ApiResponse(responseCode = "200", description = "Playlists importadas com sucesso"),
//            @ApiResponse(responseCode = "500", description = "Ocorreu um erro ao importar as playlists")
//        }
//    )
//    @PostMapping("/playlists")
//    String importarPlaylists();

//    @Operation(
//        summary = "Importar usuários fictícios no sistema",
//        responses = {
//            @ApiResponse(responseCode = "200", description = "Usuários importados com sucesso"),
//            @ApiResponse(responseCode = "500", description = "Ocorreu um erro ao importar os usuários")
//        }
//    )
//    @PostMapping("/usuarios")
//    String importarUsuarios();

}
