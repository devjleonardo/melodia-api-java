package br.com.resolveai.melodia.api.controller;

import br.com.resolveai.melodia.api.dto.response.ArtistaRankingDTO;
import br.com.resolveai.melodia.api.mapper.ArtistaRankingMapper;
import br.com.resolveai.melodia.core.util.ApiEndpoint;
import br.com.resolveai.melodia.domain.model.ArtistaRanking;
import br.com.resolveai.melodia.domain.model.UsuarioArtistaRanking;
import br.com.resolveai.melodia.domain.service.RankingArtistasService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(ApiEndpoint.RANKING_ARTISTAS)
@RequiredArgsConstructor
public class RankingArtistasController {

    private final RankingArtistasService rankingArtistasService;
    private final ArtistaRankingMapper artistaRankingMapper;

    @GetMapping("/geral")
    public Page<ArtistaRankingDTO> getRankingGeralArtistas(@PageableDefault Pageable pageable) {
        Page<ArtistaRanking> artistasRankeados = rankingArtistasService.obterRankingGeralDosArtistas(pageable);

        List<ArtistaRankingDTO> artistasRankeadosDTO = artistasRankeados.stream()
            .map(artistaRankingMapper::converterArtistaRankingParaDTO)
            .toList();

        return new PageImpl<>(artistasRankeadosDTO, pageable, artistasRankeados.getTotalElements());
    }

    @GetMapping("/usuario/{usuarioId}")
    public Page<ArtistaRankingDTO> getRankingArtistasPorUsuario(
        @PathVariable Long usuarioId,
        @PageableDefault Pageable pageable) {
        Page<UsuarioArtistaRanking> artistasRankeados = rankingArtistasService.obterRankingDeArtistasPorUsuario(usuarioId, pageable);

        List<ArtistaRankingDTO> artistaRankingDTO = artistasRankeados.stream()
            .map(artistaRankingMapper::converterUsuarioArtistaRankingParaDTO)
            .toList();

        return new PageImpl<>(artistaRankingDTO, pageable, artistasRankeados.getTotalElements());
    }

}
