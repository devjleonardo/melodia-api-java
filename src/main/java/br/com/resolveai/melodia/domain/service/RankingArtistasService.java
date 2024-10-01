package br.com.resolveai.melodia.domain.service;

import br.com.resolveai.melodia.domain.projecao.ArtistaRankingProjecao;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RankingArtistasService {

    private static final String RANKING_GERAL_KEY = "ranking:artistas:geral";
    private static final String RANKING_USUARIO_KEY_PREFIX = "ranking:artistas:usuario:";

    private final RedisTemplate<String, String> redisTemplate;
    private final ArtistaService artistaService;

    public void atualizarRankingGeralDoArtista(Long artistaId) {
        incrementarPontuacao(RANKING_GERAL_KEY, artistaId);
    }

    public void atualizarRankingDoArtistaDoUsuario(Long usuarioId, Long artistaId) {
        String rankingUsuarioKey = RANKING_USUARIO_KEY_PREFIX + usuarioId;
        incrementarPontuacao(rankingUsuarioKey, artistaId);
    }

    public Page<ArtistaRankingProjecao> obterRankingGeralDosArtistas(Pageable pageable) {
        return obterArtistasPorRanking(RANKING_GERAL_KEY, pageable);
    }

    public Page<ArtistaRankingProjecao> obterRankingDeArtistasPorUsuario(Long usuarioId, Pageable pageable) {
        String rankingUsuarioKey = RANKING_USUARIO_KEY_PREFIX + usuarioId;
        return obterArtistasPorRanking(rankingUsuarioKey, pageable);
    }

    public List<ArtistaRankingProjecao> obterRankingGeralSemPaginacao() {
        return carregarResumoDosArtistas(obterTodosIdsDoRanking(RANKING_GERAL_KEY));
    }

    public List<ArtistaRankingProjecao> obterRankingDeUsuarioSemPaginacao(Long usuarioId) {
        String rankingUsuarioKey = RANKING_USUARIO_KEY_PREFIX + usuarioId;
        return carregarResumoDosArtistas(obterTodosIdsDoRanking(rankingUsuarioKey));
    }

    private void incrementarPontuacao(String redisKey, Long artistaId) {
        redisTemplate.opsForZSet().incrementScore(redisKey, artistaId.toString(), 1);
    }

    private Page<ArtistaRankingProjecao> obterArtistasPorRanking(String redisKey, Pageable pageable) {
        Set<ZSetOperations.TypedTuple<String>> ranking = obterDadosDoRankingRedis(redisKey, pageable);

        if (ranking == null || ranking.isEmpty()) {
            return Page.empty(pageable);
        }

        List<Long> artistaIds = extrairIdsDosArtistas(ranking);
        List<ArtistaRankingProjecao> artistasRankeados = carregarResumoDosArtistas(artistaIds);

        Long totalSize = redisTemplate.opsForZSet().size(redisKey);
        long totalElements = (totalSize != null) ? totalSize : 0L;

        return new PageImpl<>(artistasRankeados, pageable, totalElements);
    }

    private List<Long> obterTodosIdsDoRanking(String redisKey) {
        Set<ZSetOperations.TypedTuple<String>> ranking =
            redisTemplate.opsForZSet().reverseRangeWithScores(redisKey, 0, -1);

        if (ranking == null || ranking.isEmpty()) {
            return new ArrayList<>();
        }

        return extrairIdsDosArtistas(ranking);
    }

    private Set<ZSetOperations.TypedTuple<String>> obterDadosDoRankingRedis(String redisKey, Pageable pageable) {
        long start = pageable.getOffset();
        long end = start + pageable.getPageSize() - 1;
        return redisTemplate.opsForZSet().reverseRangeWithScores(redisKey, start, end);
    }

    private List<Long> extrairIdsDosArtistas(Set<ZSetOperations.TypedTuple<String>> ranking) {
        List<Long> artistaIds = new ArrayList<>();
        for (ZSetOperations.TypedTuple<String> artistaData : ranking) {
            Long artistaId = Long.parseLong(Objects.requireNonNull(artistaData.getValue()));
            artistaIds.add(artistaId);
        }
        return artistaIds;
    }

    private List<ArtistaRankingProjecao> carregarResumoDosArtistas(List<Long> artistaIds) {
        List<ArtistaRankingProjecao> artistasRankeados = new ArrayList<>();
        for (Long artistaId : artistaIds) {
            ArtistaRankingProjecao artista = artistaService.obterResumoArtistaPorId(artistaId);
            artistasRankeados.add(artista);
        }
        return artistasRankeados;
    }

}
