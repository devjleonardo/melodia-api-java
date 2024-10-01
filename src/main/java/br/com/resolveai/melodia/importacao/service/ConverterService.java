package br.com.resolveai.melodia.importacao.service;

import br.com.resolveai.melodia.core.util.LogUtil;
import br.com.resolveai.melodia.importacao.exception.ConverterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class ConverterService {

    private static final Logger logger = LoggerFactory.getLogger(ConverterService.class);

    public String converterParaMp3(String mp4FilePath) {
        File mp4File = new File(mp4FilePath);
        if (!mp4File.exists()) {
            LogUtil.registrarAviso(logger, "Arquivo MP4 não existe: " + mp4FilePath);
            return null;
        }

        String mp3FileName = mp4FilePath.replace(".mp4", ".mp3");

        ProcessBuilder builder = new ProcessBuilder(
            "ffmpeg", "-i", mp4FilePath, "-vn", "-ab", "192k", "-ar", "44100", "-y", mp3FileName
        );

        builder.redirectErrorStream(true);

        try {
            LogUtil.registrarInfo(
                logger, "Executando comando FFmpeg: " + String.join(" ", builder.command()))
            ;

            Process process = builder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                LogUtil.registrarInfo(logger, line);
            }

            int exitCode = process.waitFor();
            LogUtil.registrarInfo(logger, "Comando FFmpeg finalizado com código de saída: " + exitCode);

            File mp3File = new File(mp3FileName);
            if (exitCode != 0 || !mp3File.exists()) {
                LogUtil.registrarAviso(logger, "Falha ao criar o arquivo MP3: " + mp3FileName);
                return null;
            }

            LogUtil.registrarInfo(logger, "Arquivo MP3 criado com sucesso: " + mp3FileName);
            return mp3FileName;

        } catch (IOException e) {
            LogUtil.registrarExcecao(logger, "Erro ao converter o vídeo para MP3", e);
            throw new ConverterException("Erro ao converter o vídeo para MP3", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LogUtil.registrarExcecao(logger, "Thread foi interrompida durante a conversão para MP3", e);
            throw new ConverterException("Thread foi interrompida durante a conversão para MP3", e);
        }
    }

}
