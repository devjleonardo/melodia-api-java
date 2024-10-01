package br.com.resolveai.melodia.api.controller;


import br.com.resolveai.melodia.api.dto.request.AtualizarPlaylistDTO;
import br.com.resolveai.melodia.api.dto.request.CadastrarPlaylistDTO;
import br.com.resolveai.melodia.api.dto.response.PlaylistDTO;
import br.com.resolveai.melodia.api.mapper.PlaylistMapper;
import br.com.resolveai.melodia.api.openapi.PlaylistOpenApi;
import br.com.resolveai.melodia.core.util.ApiEndpoint;
import br.com.resolveai.melodia.domain.model.Playlist;
import br.com.resolveai.melodia.domain.service.PlaylistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiEndpoint.PLAYLISTS)
@RequiredArgsConstructor
public class PlaylistController implements PlaylistOpenApi {

    private final PlaylistService playlistService;
    private final PlaylistMapper playlistMapper;

    @Override
    @GetMapping
    public Page<PlaylistDTO> listar(@PageableDefault Pageable pageable) {
        return playlistService.listarTodasPlaylistsPaginadas(pageable)
            .map(playlistMapper::converterPlaylistParaDTO);
    }

    @Override
    @GetMapping("/{id}")
    public PlaylistDTO buscarPorId(@PathVariable Long id) {
        Playlist playlist = playlistService.buscarPlaylistPorId(id);
        return playlistMapper.converterPlaylistParaDTO(playlist);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlaylistDTO cadastrar(@RequestBody @Valid CadastrarPlaylistDTO dto) {
        Playlist playlist = playlistMapper.converteDTOParaPlaylist(dto);
        Playlist playlistSalva = playlistService.salvarPlaylist(playlist);
        return playlistMapper.converterPlaylistParaDTO(playlistSalva);
    }

    @Override
    @PutMapping("/{id}")
    public PlaylistDTO atualizar(@PathVariable Long id, @RequestBody @Valid AtualizarPlaylistDTO dto) {
        Playlist playlistExistente = playlistService.buscarPlaylistPorId(id);
        playlistMapper.atualizarPlaylistComDadosDoRequestDTO(dto, playlistExistente);
        Playlist playlistAtualizada = playlistService.salvarPlaylist(playlistExistente);
        return playlistMapper.converterPlaylistParaDTO(playlistAtualizada);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        playlistService.removerPlaylistPorId(id);
    }

}
