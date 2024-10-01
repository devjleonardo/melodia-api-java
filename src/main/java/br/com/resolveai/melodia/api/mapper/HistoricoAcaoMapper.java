package br.com.resolveai.melodia.api.mapper;

import br.com.resolveai.melodia.api.dto.response.HistoricoAcaoDTO;
import br.com.resolveai.melodia.api.dto.response.MusicaDTO;
import br.com.resolveai.melodia.api.dto.response.PlaylistDTO;
import br.com.resolveai.melodia.domain.model.HistoricoAcao;
import br.com.resolveai.melodia.domain.model.HistoricoMusica;
import br.com.resolveai.melodia.domain.model.HistoricoPlaylist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HistoricoAcaoMapper {

    private final MusicaMapper musicaMapper;
    private final PlaylistMapper playlistMapper;

    public HistoricoAcaoDTO converterHistoricoAcaoParaDTO(HistoricoAcao acao) {
        HistoricoAcaoDTO dto = new HistoricoAcaoDTO();
        dto.setId(acao.getId());
        dto.setDataAcao(acao.getDataAcao());

        if (acao instanceof HistoricoMusica historicoMusica) {
            MusicaDTO musicaDTO = musicaMapper.converterMusicaParaDTO(historicoMusica.getMusica());
            dto.setTipoAcao("MUSICA");
            dto.setMusica(musicaDTO);
        } else if (acao instanceof HistoricoPlaylist historicoPlaylist) {
            PlaylistDTO playlistDTO = playlistMapper.converterPlaylistParaDTO(historicoPlaylist.getPlaylist());
            dto.setTipoAcao("PLAYLIST");
            dto.setPlaylist(playlistDTO);
        }

        return dto;
    }

}
