package br.com.resolveai.melodia.api.controller;

import br.com.resolveai.melodia.api.dto.request.AdicionarMusicaPlaylistDTO;
import br.com.resolveai.melodia.api.openapi.PlaylistMusicaOpenApi;
import br.com.resolveai.melodia.core.util.ApiEndpoint;
import br.com.resolveai.melodia.domain.service.PlaylistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiEndpoint.PLAYLIST_MUSICAS)
@RequiredArgsConstructor
public class PlaylistMusicaController implements PlaylistMusicaOpenApi {

    private final PlaylistService playlistService;

    @Override
    @PutMapping
    public void adicionarMusica(@PathVariable Long id, @RequestBody @Valid AdicionarMusicaPlaylistDTO dto) {
        playlistService.adicionarMusica(id, dto.getMusicaId());
    }

}
