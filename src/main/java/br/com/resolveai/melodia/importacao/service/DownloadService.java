package br.com.resolveai.melodia.importacao.service;

import br.com.resolveai.melodia.core.util.LogUtil;
import br.com.resolveai.melodia.importacao.exception.DownloadException;
import br.com.resolveai.melodia.importacao.exception.ExtracaoVideoIdException;
import br.com.resolveai.melodia.importacao.util.ProcessUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class DownloadService {

    private static final Logger logger = LoggerFactory.getLogger(DownloadService.class);

    public String baixarVideo(String videoUrl) {
        String videoTitulo = obterTituloDoVideo(videoUrl);
        String tituloNormalizado = normalizarTitulo(videoTitulo);

        String baseDir = System.getProperty("os.name")
            .toLowerCase()
            .contains("win") ? "C:/musicas/" : "/var/www/musicas/";

        String caminhoArquivoMp4 = baseDir + tituloNormalizado + ".mp4";

        String comandoDownload = "yt-dlp -f bestvideo[ext=mp4]+bestaudio[ext=m4a]/best[ext=mp4] -o " +
            caminhoArquivoMp4 + " " + videoUrl;

        int maxTentativas = 3;
        int tentativa = 0;

        while (tentativa < maxTentativas) {
            tentativa++;
            try {
                LogUtil.registrarInfo(logger, "Tentativa " + tentativa + " de baixar o vídeo: " + videoUrl);
                ProcessUtil.executarComando(comandoDownload, logger);
                LogUtil.registrarInfo(logger, "Download concluído com sucesso para o vídeo: " + videoUrl);
                return caminhoArquivoMp4;
            } catch (IOException e) {
                LogUtil.registrarExcecao(logger, "Erro ao baixar o vídeo na tentativa " + tentativa, e);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                LogUtil.registrarExcecao(logger, "Thread foi interrompida durante o download do vídeo " +
                    "na tentativa " + tentativa, e);
                throw new DownloadException("Thread foi interrompida durante o download do vídeo", e);
            }

            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                LogUtil.registrarExcecao(logger, "Thread foi interrompida durante a espera entre tentativas", e);
                throw new DownloadException("Thread foi interrompida durante a espera entre tentativas", e);
            }
        }

        throw new DownloadException("Falha ao baixar o vídeo após " + maxTentativas + " tentativas: " + videoUrl);
    }

    public String obterTituloDoVideo(String videoUrl) {
        String comandoObterTitulo = "yt-dlp -e " + videoUrl;
        try {
            return executarComandoERetornarResultado(comandoObterTitulo);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LogUtil.registrarExcecao(logger, "Thread foi interrompida ao obter o título do vídeo", e);
            throw new DownloadException("Thread foi interrompida ao obter o título do vídeo", e);
        } catch (Exception e) {
            LogUtil.registrarExcecao(logger, "Erro ao obter o título do vídeo", e);
            throw new DownloadException("Erro ao obter o título do vídeo", e);
        }
    }

    public String obterThumbnailDoVideo(String videoUrl) {
        String videoId = extrairVideoId(videoUrl);
        return "https://img.youtube.com/vi/" + videoId + "/maxresdefault.jpg";
    }

    private String executarComandoERetornarResultado(String comando) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder(comando.split(" "));
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();

        try (
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8)
            )
        ) {
            String resultado = reader.readLine();
            process.waitFor();

            if (resultado == null || resultado.isEmpty()) {
                String mensagemErro = "Comando não retornou resultado: " + comando;
                LogUtil.registrarExcecao(logger, mensagemErro, new DownloadException(mensagemErro, null));
                throw new DownloadException(mensagemErro, null);
            }
            return resultado;
        }
    }

    private String normalizarTitulo(String titulo) {
        String tituloNormalizado = titulo.trim().replace(" ", "_");
        tituloNormalizado = tituloNormalizado.replaceAll("[^a-zA-Z0-9_\\-]", "");
        LogUtil.registrarInfo(logger, "Título normalizado: " + tituloNormalizado);
        return tituloNormalizado;
    }

    private String extrairVideoId(String videoUrl) throws ExtracaoVideoIdException {
        String pattern = "v=([^&]*)";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(videoUrl);

        if (matcher.find()) {
            return matcher.group(1);
        } else {
            String errorMessage = "ID do vídeo não pôde ser extraído da URL: " + videoUrl;
            LogUtil.registrarAviso(logger, errorMessage);
            throw new ExtracaoVideoIdException(errorMessage);
        }
    }

}
