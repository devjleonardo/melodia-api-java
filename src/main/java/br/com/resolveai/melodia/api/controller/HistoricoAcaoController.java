package br.com.resolveai.melodia.api.controller;

import br.com.resolveai.melodia.api.dto.request.HistoricoAcaoRequestDTO;
import br.com.resolveai.melodia.api.dto.response.HistoricoAcaoDTO;
import br.com.resolveai.melodia.api.mapper.HistoricoAcaoMapper;
import br.com.resolveai.melodia.core.util.ApiEndpoint;
import br.com.resolveai.melodia.domain.service.HistoricoAcaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiEndpoint.HISTORICO_ACOES)
@RequiredArgsConstructor
public class HistoricoAcaoController {

    private final HistoricoAcaoService historicoAcaoService;
    private final HistoricoAcaoMapper historicoAcaoMapper;

    @PostMapping("/ouvir")
    @ResponseStatus(HttpStatus.CREATED)
    public void registrarAcao(@RequestBody HistoricoAcaoRequestDTO dto) {
        historicoAcaoService.registrarAcao(dto.getUsuarioId(), dto.getItemId(), dto.getTipoAcao());
    }

    @GetMapping("/usuario/{usuarioId}")
    public Page<HistoricoAcaoDTO> listarHistorico(
            @PathVariable Long usuarioId,
            @PageableDefault Pageable pageable) {
        return historicoAcaoService.buscarHistoricoOrdenadoPorData(usuarioId, pageable)
            .map(historicoAcaoMapper::converterHistoricoAcaoParaDTO);
    }

}
