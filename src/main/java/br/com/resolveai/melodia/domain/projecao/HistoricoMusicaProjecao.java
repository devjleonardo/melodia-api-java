package br.com.resolveai.melodia.domain.projecao;

import java.time.LocalDateTime;

public interface HistoricoMusicaProjecao {

    Long getId();
    Long getMusicaId();
    String getTituloMusica();
    String getNomeArtista();
    String getCapaUrl();
    String getAudioUrl();
    LocalDateTime getDataOuvida();

}
