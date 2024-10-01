package br.com.resolveai.melodia.domain.service;

import br.com.resolveai.melodia.domain.model.ArtistaRanking;
import br.com.resolveai.melodia.domain.model.UsuarioArtistaRanking;
import br.com.resolveai.melodia.domain.repository.ArtistaRankingRepository;
import br.com.resolveai.melodia.domain.repository.UsuarioArtistaRankingRepository;
import br.com.resolveai.melodia.domain.model.Artista;
import br.com.resolveai.melodia.domain.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RankingArtistasService {

    private final ArtistaRankingRepository artistaRankingRepository;
    private final UsuarioArtistaRankingRepository usuarioArtistaRankingRepository;
    private final ArtistaService artistaService;
    private final UsuarioService usuarioService;

    public void atualizarRankingGeralDoArtista(Long artistaId) {
        ArtistaRanking ranking = artistaRankingRepository.findByArtistaId(artistaId);

        if (ranking == null) {
            Artista artista = artistaService.buscarArtistaPorId(artistaId);
            ranking = new ArtistaRanking();
            ranking.setArtista(artista);
            ranking.setPontuacao(1.0);
        } else {
            ranking.setPontuacao(ranking.getPontuacao() + 1);
        }

        artistaRankingRepository.save(ranking);
    }

    public void atualizarRankingDoArtistaDoUsuario(Long usuarioId, Long artistaId) {
        UsuarioArtistaRanking ranking = usuarioArtistaRankingRepository.findByUsuarioIdAndArtistaId(usuarioId, artistaId);

        if (ranking == null) {
            Usuario usuario = usuarioService.buscarUsuarioPorId(usuarioId);
            Artista artista = artistaService.buscarArtistaPorId(artistaId);
            ranking = new UsuarioArtistaRanking();
            ranking.setUsuario(usuario);
            ranking.setArtista(artista);
            ranking.setPontuacao(1.0);
        } else {
            ranking.setPontuacao(ranking.getPontuacao() + 1);
        }

        usuarioArtistaRankingRepository.save(ranking);
    }

    public Page<ArtistaRanking> obterRankingGeralDosArtistas(Pageable pageable) {
        return artistaRankingRepository.findAll(pageable);
    }

    public Page<UsuarioArtistaRanking> obterRankingDeArtistasPorUsuario(Long usuarioId, Pageable pageable) {
        return usuarioArtistaRankingRepository.findByUsuarioId(usuarioId, pageable);
    }

}
