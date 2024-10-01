package br.com.resolveai.melodia.importacao.service;

import br.com.resolveai.melodia.core.util.LogUtil;
import br.com.resolveai.melodia.importacao.exception.FTPUploadException;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FTPUploadService {

    private static final Logger logger = LoggerFactory.getLogger(FTPUploadService.class);

    private static final String FTP_HOST = "191.252.194.153";
    private static final String FTP_USER = "root";
    private static final String FTP_PASSWORD = "h4@FROK!{ye5w0P";
    private static final String FTP_UPLOAD_DIR = "/var/www/musicas/";

    public void enviarArquivoViaFTP(String caminhoArquivoMp3) {
        Path caminhoArquivoPath = Paths.get(caminhoArquivoMp3);
        String caminhoNormalizado = caminhoArquivoPath.normalize().toString();

        FTPClient ftpClient = new FTPClient();
        try {
            conectarFTP(ftpClient);
            realizarUploadArquivo(ftpClient, caminhoNormalizado);
        } catch (IOException e) {
            LogUtil.registrarExcecao(logger, "Erro ao enviar o arquivo via FTP", e);
            throw new FTPUploadException("Erro ao enviar o arquivo via FTP", e);
        } finally {
            desconectarFTP(ftpClient);
            LogUtil.registrarInfo(logger, "Desconexão do FTP realizada com sucesso.");
        }
    }

    private void conectarFTP(FTPClient ftpClient) throws IOException {
        ftpClient.connect(FTP_HOST);
        ftpClient.login(FTP_USER, FTP_PASSWORD);
        ftpClient.enterLocalPassiveMode();
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
    }

    private void realizarUploadArquivo(FTPClient clienteFtp, String caminhoArquivoMp3) throws IOException {
        File mp3File = new File(caminhoArquivoMp3);
        if (!mp3File.exists()) {
            LogUtil.registrarAviso(logger, "Arquivo MP3 não encontrado para upload: " + caminhoArquivoMp3);
        }

        // Usar a barra correta do sistema operacional para concatenar o caminho do diretório e o nome do arquivo
        String caminhoArquivoNoFtp = FTP_UPLOAD_DIR + mp3File.getName().replace("\\", "/");

        try (FileInputStream arquivoEntrada = new FileInputStream(mp3File)) {
            boolean sucesso = clienteFtp.storeFile(caminhoArquivoNoFtp, arquivoEntrada);
            if (sucesso) {
                LogUtil.registrarInfo(logger, "Arquivo MP3 enviado com sucesso via FTP: " + caminhoArquivoMp3);
            } else {
                LogUtil.registrarAviso(logger, "Falha ao enviar o arquivo MP3 via FTP: " + caminhoArquivoMp3);
            }
        }
    }

    private void desconectarFTP(FTPClient ftpClient) {
        try {
            if (ftpClient.isConnected()) {
                ftpClient.logout();
                ftpClient.disconnect();
            }
        } catch (IOException ex) {
            LogUtil.registrarExcecao(logger, "Erro ao desconectar do FTP", ex);
        }
    }
}
