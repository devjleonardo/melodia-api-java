package br.com.resolveai.melodia.importacao.controller;

import br.com.resolveai.melodia.core.util.ApiEndpoint;
import br.com.resolveai.melodia.importacao.service.SpotifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(ApiEndpoint.SPOTIFY)
@RequiredArgsConstructor
public class SpotifyController {

    private final SpotifyService spotifyService;

    @PostMapping("/buscar-artistas")
    public String buscarArtistas(@RequestBody List<String> nomesArtistas) {
        spotifyService.pesquisarArtistas(nomesArtistas);
        return "Consulta realizada com sucesso!";
    }

}
