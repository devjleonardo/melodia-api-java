package br.com.resolveai.melodia.importacao.controller;

import br.com.resolveai.melodia.core.util.ApiEndpoint;
import br.com.resolveai.melodia.importacao.service.YouTubeDownloadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(ApiEndpoint.YOUTUBE)
@RequiredArgsConstructor
public class YouTubeController {

    private final YouTubeDownloadService youtubeDownloadService;
    @PostMapping("/baixar-musicas")
    public String baixarMusicas(@RequestBody List<String> videoUrls) {
        try {
            youtubeDownloadService.baixarMusicas(videoUrls);
            return "Download e conversão de todas as músicas concluídos!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao realizar download e conversão: " + e.getMessage();
        }
    }

}
