package br.com.resolveai.melodia.domain.service;


import br.com.resolveai.melodia.domain.model.HistoricoMusica;
import br.com.resolveai.melodia.domain.model.Musica;
import br.com.resolveai.melodia.domain.model.Usuario;
import br.com.resolveai.melodia.domain.repository.HistoricoMusicaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HistoricoMusicaService {

    private final HistoricoMusicaRepository historicoMusicaRepository;

    public HistoricoMusica buscarHistoricoPorUsuarioEMusica(Usuario usuario, Musica musica) {
        return historicoMusicaRepository.findByUsuarioAndMusica(usuario, musica);
    }

}
