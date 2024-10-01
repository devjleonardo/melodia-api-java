package br.com.resolveai.melodia.importacao.controller;

import br.com.resolveai.melodia.core.util.ApiEndpoint;
import br.com.resolveai.melodia.importacao.openapi.ImportacaoOpenApi;
import br.com.resolveai.melodia.importacao.service.JamendoMusicasService;
import br.com.resolveai.melodia.importacao.service.PlaylistFakerService;
import br.com.resolveai.melodia.importacao.service.UsuarioFakerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiEndpoint.IMPORTACOES)
@RequiredArgsConstructor
public class ImportacaoController implements ImportacaoOpenApi {

    private final JamendoMusicasService jamendoMusicasService;
    private final PlaylistFakerService playlistFakerService;
    private final UsuarioFakerService usuarioFakerService;

//    @Override
//    @PostMapping("/musicas")
//    public String importarMusicas() {
//        return jamendoMusicasService.importarMusicas();
//    }

//    @PostMapping("/playlists")
//    public String importarPlaylists() {
//        return playlistFakerService.importarPlaylists();
//    }

//    @PostMapping("/usuarios")
//    public String importarUsuarios() {
//        return usuarioFakerService.importarUsuarios();
//    }

}
