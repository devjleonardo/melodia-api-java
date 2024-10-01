package br.com.resolveai.melodia.domain.service;

import br.com.resolveai.melodia.domain.model.Artista;
import br.com.resolveai.melodia.domain.repository.ArtistaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SincronizacaoService {

    private final RedisTemplate<String, String> redisTemplate;

    private final ArtistaRepository artistaRepository;

    // Sincronização a cada 1 minuto
    @Scheduled(fixedRate = 60000) 
    public void sincronizarDadosComBancoDeDados() {
        // Busca todas as chaves do Redis relacionadas às execuções de artistas
        Set<String> chavesArtistas = redisTemplate.keys("artista:*:execucoes");

        if (chavesArtistas != null) {
            for (String chave : chavesArtistas) {
                // Extrai o ID do artista da chave
                String artistaId = chave.split(":")[1];

                // Recupera o valor de execuções do Redis
                Long execucoes = Long.valueOf(Objects.requireNonNull(redisTemplate.opsForValue().get(chave)));

                // Busca o artista no banco de dados
                Artista artista = artistaRepository.findById(Long.valueOf(artistaId))
                        .orElseThrow(() -> new RuntimeException("Artista não encontrado"));

                // Atualiza o número de execuções do artista
                artista.setExecucoes(execucoes);
                // Salva as informações atualizadas no banco de dados
                artistaRepository.save(artista);

                // Após salvar no banco de dados, você pode limpar os dados do Redis se necessário
                redisTemplate.delete(chave);
            }
        }
    }

}
