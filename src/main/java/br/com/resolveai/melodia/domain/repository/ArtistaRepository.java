package br.com.resolveai.melodia.domain.repository;

import br.com.resolveai.melodia.domain.model.Artista;
import br.com.resolveai.melodia.domain.projecao.ArtistaRankingProjecao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArtistaRepository extends JpaRepository<Artista, Long> {

    Optional<Artista> findByNome(String nome);

    @Query("""
    SELECT a
    FROM Artista a
    WHERE LOWER(a.nome) LIKE LOWER(CONCAT('%', :nome, '%'))
    """)
    List<Artista> findByNomeAproximado(String nome);

    @Query("""
    SELECT
        a.id AS artistaId,
        a.nome AS nomeArtista,
        a.fotoUrl AS artistaFotoUrl
    FROM
        Artista a
    WHERE
        a.id = :id
    """)
    Optional<ArtistaRankingProjecao> findResumoDeArtistaPorId(Long id);

}
