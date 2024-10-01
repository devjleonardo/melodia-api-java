package br.com.resolveai.melodia.domain.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("PLAYLIST")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HistoricoPlaylist extends HistoricoAcao {

    @ManyToOne
    @JoinColumn(name = "playlist_id", nullable = false)
    private Playlist playlist;

    @Override
    public boolean equals(Object outroHistoricoPlaylist) {
        return super.equals(outroHistoricoPlaylist);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
