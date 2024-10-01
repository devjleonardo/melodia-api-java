package br.com.resolveai.melodia.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ParticipantesPlaylist extends EntidadeBase {

    @ManyToOne
    @JoinColumn(name = "playlist_id")
    private Playlist playlist;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private String nivelParticipacao;

    @Override
    public boolean equals(Object outrosParticipantesPlaylist) {
        return super.equals(outrosParticipantesPlaylist);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}

