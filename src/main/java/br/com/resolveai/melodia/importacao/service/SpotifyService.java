package br.com.resolveai.melodia.importacao.service;

import br.com.resolveai.melodia.domain.model.Artista;
import br.com.resolveai.melodia.domain.repository.ArtistaRepository;
import br.com.resolveai.melodia.importacao.client.SpotifyClient;
import br.com.resolveai.melodia.importacao.dto.SpotifyArtistaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SpotifyService {

    private final SpotifyClient spotifyClient;
    private final ArtistaRepository artistaRepository;

    public void pesquisarArtistas(List<String> nomesArtistas) {
        for (String nomeArtista : nomesArtistas) {
            SpotifyArtistaDTO artistaDTO = spotifyClient.pesquisarArtista(nomeArtista);
            if (artistaDTO != null) {
                salvarArtista(artistaDTO.name(), artistaDTO.avatarUrl());
            }
        }
    }

    public Artista pesquisarOuSalvarArtistaPorNome(String nomeArtista) {
        // Primeiro tenta encontrar o artista no banco de dados
        Optional<Artista> artistaExistente = artistaRepository.findByNome(nomeArtista);

        // Se o artista já existir no banco de dados, retorná-lo
        if (artistaExistente.isPresent()) {
            return artistaExistente.get();
        }

        // Caso contrário, fazer uma busca no Spotify
        SpotifyArtistaDTO artistaDTO = spotifyClient.pesquisarArtista(nomeArtista);
        if (artistaDTO != null) {
            // Salva o artista no banco de dados
            return salvarArtista(artistaDTO.name(), artistaDTO.avatarUrl());
        }

        // Se não encontrar o artista, retorna null (ou lança exceção, dependendo da lógica desejada)
        return null;
    }

    private Artista salvarArtista(String name, String avatarUrl) {
        Optional<Artista> existingArtist = artistaRepository.findByNome(name);
        return existingArtist.orElseGet(() -> {
            Artista newArtist = new Artista();
            newArtist.setNome(name);
            newArtist.setFotoUrl(avatarUrl);
            return artistaRepository.save(newArtist);
        });
    }

}
