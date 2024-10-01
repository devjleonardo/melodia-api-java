package br.com.resolveai.melodia.api.controller;

import br.com.resolveai.melodia.api.dto.request.MusicaRequestDTO;
import br.com.resolveai.melodia.api.dto.response.MusicaDTO;
import br.com.resolveai.melodia.api.mapper.MusicaMapper;
import br.com.resolveai.melodia.api.openapi.MusicaOpenApi;
import br.com.resolveai.melodia.core.util.ApiEndpoint;
import br.com.resolveai.melodia.domain.model.Musica;
import br.com.resolveai.melodia.domain.service.MusicaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(ApiEndpoint.MUSICAS)
@RequiredArgsConstructor
public class MusicaController implements MusicaOpenApi {

    private final MusicaService musicaService;
    private final MusicaMapper musicaMapper;

    @Override
    @GetMapping
    public Page<MusicaDTO> listar(@PageableDefault Pageable pageable) {
        return musicaService.listarTodasMusicasPaginadas(pageable)
            .map(musicaMapper::converterMusicaParaDTO);
    }

    @Override
    @GetMapping("/{id}")
    public MusicaDTO buscarPorId(@PathVariable Long id) {
        Musica musica = musicaService.buscarMusicaPorId(id);
        return musicaMapper.converterMusicaParaDTO(musica);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MusicaDTO cadastrar(@RequestBody @Valid MusicaRequestDTO dto) {
        Musica musica = musicaMapper.converteDTOParaMusica(dto);
        Musica musicaSalva = musicaService.salvarMusica(musica);
        return musicaMapper.converterMusicaParaDTO(musicaSalva);
    }

    @Override
    @PutMapping("/{id}")
    public MusicaDTO atualizar(@PathVariable Long id, @RequestBody @Valid MusicaRequestDTO dto) {
        Musica musicaExistente = musicaService.buscarMusicaPorId(id);
        musicaMapper.atualizarMusicaComDadosDoRequestDTO(dto, musicaExistente);
        Musica musicaAtualizada = musicaService.salvarMusica(musicaExistente);
        return musicaMapper.converterMusicaParaDTO(musicaAtualizada);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        musicaService.removerMusicaPorId(id);
    }

    @Override
    @GetMapping("/populares")
    public List<MusicaDTO> listarMusicasPopulares() {
        return musicaService.listarMusicasPopulares().stream()
            .map(musicaMapper::converterMusicaParaDTO)
            .toList();
    }

    @Override
    @GetMapping("/recentes")
    public List<MusicaDTO> listarMusicasRecentes() {
        return musicaService.listarMusicasRecentes().stream()
            .map(musicaMapper::converterMusicaParaDTO)
            .toList();
    }

    @Override
    @GetMapping("/pesquisar")
    public List<MusicaDTO> pesquisarMusicas(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String artista,
            @RequestParam(required = false) String album,
            @RequestParam(required = false) String genero,
            @RequestParam(required = false) LocalDate dataLancamento,
            @RequestParam(defaultValue = "0", required = false) int pagina,
            @RequestParam(defaultValue = "10", required = false) int tamanhoPagina) {
        return musicaService.pesquisarMusicas(titulo, artista, album, genero, dataLancamento, pagina, tamanhoPagina)
            .stream()
            .map(musicaMapper::converterMusicaParaDTO)
            .toList();
    }

}
