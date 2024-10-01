package br.com.resolveai.melodia.domain.repository;

import br.com.resolveai.melodia.domain.model.Musica;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MusicaRepository extends JpaRepository<Musica, Long> {

    Optional<Musica> findByTitulo(String titulo);

    List<Musica> findAllByOrderByPopularidadeDesc();

    List<Musica> findAllByOrderByDataLancamentoDesc();

    @Query("""
    SELECT m
    FROM Musica m
    WHERE
        (COALESCE(:titulo, '') = '' OR LOWER(m.titulo) = LOWER(:titulo)) AND
        (COALESCE(:artista, '') = '' OR LOWER(m.artista.nome) = LOWER(:artista)) AND
        (COALESCE(:album, '') = '' OR LOWER(m.album) = LOWER(:album)) AND
        (COALESCE(:genero, '') = '' OR LOWER(m.generoMusical.nome) = LOWER(:genero)) AND
        (COALESCE(:dataLancamento, NULL) IS NULL OR m.dataLancamento = :dataLancamento)
    """)
    Page<Musica> searchMusic(String titulo, String artista, String album, String genero, LocalDate dataLancamento, Pageable pageable);


    default List<Musica> searchBySimilarity(String titulo, String artista, String album, String genero,
            List<Musica> musicas) {
        LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
        int maxDistance = 3;

        return musicas.stream()
            .filter(musica ->
                isSimilarMatch(musica.getTitulo(), titulo, levenshteinDistance, maxDistance) ||
                isSimilarMatch(musica.getArtista().getNome(), artista, levenshteinDistance, maxDistance) ||
                isSimilarMatch(musica.getAlbum(), album, levenshteinDistance, maxDistance) ||
                isSimilarMatch(musica.getGeneroMusical() != null ?
                    musica.getGeneroMusical().getNome() : null, genero, levenshteinDistance, maxDistance)
            )
            .toList();
    }

    private boolean isSimilarMatch(String fieldValue, String searchTerm, LevenshteinDistance levenshtein,
            int maxDistance) {
        if (searchTerm == null || fieldValue == null) {
            return false;
        }
        return levenshtein.apply(fieldValue.toLowerCase(), searchTerm.toLowerCase()) <= maxDistance;
    }

}
