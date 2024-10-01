package br.com.resolveai.melodia.importacao.util;

import br.com.resolveai.melodia.core.util.LogUtil;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public final class ProcessUtil {

    public static void executarComando(String comando, Logger logger) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder(comando.split(" "));
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));
             BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream(), StandardCharsets.UTF_8))) {

            String line;

            while ((line = reader.readLine()) != null) {
                LogUtil.registrarInfo(logger, line);
            }

            while ((line = errorReader.readLine()) != null) {
                LogUtil.registrarAviso(logger, line);
            }

            int exitCode = process.waitFor();

            if (exitCode != 0) {
                LogUtil.registrarAviso(logger, "Processo finalizado com código de saída: " + exitCode);
            }
        }
    }

    private ProcessUtil() {
    }

}
