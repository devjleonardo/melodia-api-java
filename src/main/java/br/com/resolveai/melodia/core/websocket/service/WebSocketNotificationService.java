//package br.com.resolveai.melodia.core.websocket.service;
//
//import br.com.resolveai.melodia.api.dto.response.ArtistaRankingDTO;
//import br.com.resolveai.melodia.api.mapper.ArtistaRankingMapper;
//import br.com.resolveai.melodia.domain.projecao.ArtistaRankingProjecao;
//import br.com.resolveai.melodia.domain.service.RankingArtistasService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class WebSocketNotificationService {
//
//    private final SimpMessagingTemplate messagingTemplate;
//    private final RankingArtistasService rankingArtistasService;
//    private final ArtistaRankingMapper artistaRankingMapper;
//
//    public void notificarAtualizacaoRankingGeral() {
//        List<ArtistaRankingProjecao> artistasRankeados = rankingArtistasService.obterRankingGeralSemPaginacao();
//        List<ArtistaRankingDTO> artistaRankingDTOs = artistasRankeados.stream()
//            .map(artistaRankingMapper::converterProjecaoParaRankingDTO)
//            .toList();
//        messagingTemplate.convertAndSend("/topic/ranking/geral", artistaRankingDTOs);
//    }
//
//    public void notificarAtualizacaoRankingUsuario(Long usuarioId) {
//        List<ArtistaRankingProjecao> artistasRankeados =
//            rankingArtistasService.obterRankingDeUsuarioSemPaginacao(usuarioId);
//
//        List<ArtistaRankingDTO> artistaRankingDTOs = artistasRankeados.stream()
//            .map(artistaRankingMapper::converterProjecaoParaRankingDTO)
//            .toList();
//
//        messagingTemplate.convertAndSend("/topic/ranking/usuario/" + usuarioId, artistaRankingDTOs);
//    }
//}
