package br.com.resolveai.melodia.domain.service;

import br.com.resolveai.melodia.domain.model.*;
import br.com.resolveai.melodia.domain.model.enums.EstadoEmocional;
import br.com.resolveai.melodia.domain.repository.HistoricoAcaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class HistoricoAcaoService {

    private final HistoricoAcaoRepository historicoAcaoRepository;
    private final UsuarioService usuarioService;
    private final MusicaService musicaService;
    private final PlaylistService playlistService;
    private final HistoricoMusicaService historicoMusicaService;
    private final HistoricoPlaylistService historicoPlaylistService;
    private final RankingArtistasService rankingArtistasService;

    @Transactional
    public void registrarAcao(Long usuarioId, Long itemId, String tipoAcao) {
        if (tipoAcao.equalsIgnoreCase("MUSICA")) {
            registrarMusicaOuvida(usuarioId, itemId);
        } else if (tipoAcao.equalsIgnoreCase("PLAYLIST")) {
            registrarPlaylistOuvida(usuarioId, itemId);
        } else {
            throw new IllegalArgumentException("Tipo de ação inválido: " + tipoAcao);
        }
    }

    @Transactional(readOnly = true)
    public Page<HistoricoAcao> buscarHistoricoOrdenadoPorData(Long usuarioId, Pageable pageable) {
        return historicoAcaoRepository.findByUsuarioIdOrderByDataAcaoDesc(usuarioId, pageable);
    }

    private void registrarMusicaOuvida(Long usuarioId, Long musicaId) {
        Usuario usuario = usuarioService.buscarUsuarioPorId(usuarioId);
        Musica musica = musicaService.buscarMusicaPorId(musicaId);

        HistoricoMusica historicoExistente = historicoMusicaService.buscarHistoricoPorUsuarioEMusica(usuario, musica);

        if (historicoExistente != null) {
            historicoExistente.setDataAcao(LocalDateTime.now());
            historicoAcaoRepository.save(historicoExistente);
        } else {
            HistoricoMusica novoHistorico = new HistoricoMusica();
            novoHistorico.setUsuario(usuario);
            novoHistorico.setMusica(musica);
            novoHistorico.setDataAcao(LocalDateTime.now());

            EstadoEmocional estadoEmocionalAtual = usuarioService.obterEstadoEmocionalAtual(usuario.getId());
            novoHistorico.setEstadoEmocional(estadoEmocionalAtual);

            historicoAcaoRepository.save(novoHistorico);
        }

        Long artistaId = musica.getArtista().getId();
        rankingArtistasService.atualizarRankingGeralDoArtista(artistaId);
        rankingArtistasService.atualizarRankingDoArtistaDoUsuario(usuarioId, artistaId);
    }

    private void registrarPlaylistOuvida(Long usuarioId, Long playlistId) {
        Usuario usuario = usuarioService.buscarUsuarioPorId(usuarioId);
        Playlist playlist = playlistService.buscarPlaylistPorId(playlistId);

        HistoricoPlaylist historicoExistente = historicoPlaylistService.buscarHistoricoPorUsuarioEPlaylist(usuario, playlist);

        if (historicoExistente != null) {
            historicoExistente.setDataAcao(LocalDateTime.now());
            historicoAcaoRepository.save(historicoExistente);
        } else {
            HistoricoPlaylist novoHistorico = new HistoricoPlaylist();
            novoHistorico.setUsuario(usuario);
            novoHistorico.setPlaylist(playlist);
            novoHistorico.setDataAcao(LocalDateTime.now());

            EstadoEmocional estadoEmocionalAtual = usuarioService.obterEstadoEmocionalAtual(usuario.getId());
            novoHistorico.setEstadoEmocional(estadoEmocionalAtual);

            historicoAcaoRepository.save(novoHistorico);
        }
    }
}
