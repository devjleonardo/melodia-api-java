package br.com.resolveai.melodia.importacao.service;

import br.com.resolveai.melodia.core.util.LogUtil;
import br.com.resolveai.melodia.domain.model.Artista;
import br.com.resolveai.melodia.domain.model.Musica;
import br.com.resolveai.melodia.domain.repository.MusicaRepository;
import br.com.resolveai.melodia.importacao.exception.DuracaoMusicaException;
import br.com.resolveai.melodia.importacao.util.FileUtil;
import com.mpatric.mp3agic.Mp3File;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class YouTubeDownloadService {

    private static final Logger logger = LoggerFactory.getLogger(YouTubeDownloadService.class);

    private static final String BASE_AUDIO_URL = "http://191.252.194.153/musicas/";

    private final DownloadService downloadService;
    private final ConverterService converterService;
    private final FTPUploadService ftpUploadService;
    private final MusicaRepository musicaRepository;
    private final SpotifyService spotifyService;

    public void baixarMusicas(List<String> videoUrls) {
        LogUtil.registrarInfo(logger, "Iniciando processo de download de músicas.");

        LogUtil.registrarInfo(logger, "Verificando se o diretório base existe.");

        String baseDir;

        String osName = System.getProperty("os.name").toLowerCase();

        if (osName.contains("win")) {
            baseDir = "C:/musicas/";
        } else {
            baseDir = "/var/www/musicas/";
        }

        FileUtil.verificarOuCriarDiretorio(baseDir, logger);

        for (String videoUrl : videoUrls) {
            LogUtil.registrarInfo(logger, "Baixando música do URL: " + videoUrl);
            try {
                baixar(videoUrl);
            } catch (Exception e) {
                LogUtil.registrarExcecao(logger, "Erro ao baixar e converter a URL: " + videoUrl, e);
            }
        }
        LogUtil.registrarInfo(logger, "Processo de download de todas as músicas finalizado.");
    }

    private void baixar(String videoUrl) {
        String mp4FilePath = null;
        String mp3FilePath = null;

        try {
            LogUtil.registrarInfo(logger, "Obtendo título do vídeo.");
            String titulo = downloadService.obterTituloDoVideo(videoUrl);
            LogUtil.registrarInfo(logger, "Título do vídeo: " + titulo);

            if (tituloJaExiste(titulo)) {
                LogUtil.registrarInfo(logger, "A música com o título \"" + titulo +
                    "\" já existe. Pulando download.");
                return;
            }

            LogUtil.registrarInfo(logger, "Baixando o vídeo.");

            mp4FilePath = downloadService.baixarVideo(videoUrl);

            if (mp4FilePath == null) {
                LogUtil.registrarAviso(logger, "O caminho do arquivo MP4 é nulo.");
                return;
            }

            File mp4File = new File(mp4FilePath);

            if (!mp4File.exists()) {
                LogUtil.registrarAviso(
                    logger, "Arquivo MP4 não encontrado após o download: " + mp4FilePath
                );
                return;
            } else {
                LogUtil.registrarInfo(
                    logger, "Arquivo MP4 baixado com sucesso: " + mp4FilePath
                );
            }

            if (!mp4File.setReadable(true, false)) {
                LogUtil.registrarAviso(
                    logger, "Falha ao definir permissões de leitura para o arquivo: " + mp4FilePath
                );
            }

            if (!mp4File.setWritable(true, false)) {
                LogUtil.registrarAviso(
                    logger, "Falha ao definir permissões de escrita para o arquivo: " + mp4FilePath
                );
            }

            LogUtil.registrarInfo(logger, "Convertendo vídeo para MP3.");

            mp3FilePath = converterService.converterParaMp3(mp4FilePath);

            File mp3File = new File(mp3FilePath);

            if (mp4File.delete()) {
                LogUtil.registrarInfo(logger, "Arquivo MP4 deletado com sucesso: " + mp4FilePath);
            }

            if (!mp3File.exists()) {
                LogUtil.registrarAviso(logger, "Arquivo MP3 não criado: " + mp3FilePath);
                return;
            } else {
                LogUtil.registrarInfo(logger, "Arquivo MP3 criado com sucesso: " + mp3FilePath);
            }

            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                LogUtil.registrarInfo(logger, "Enviando arquivo MP3 via FTP.");
                ftpUploadService.enviarArquivoViaFTP(mp3FilePath);
            }

            String audioUrl = BASE_AUDIO_URL + mp3File.getName();
            String capaUrl = downloadService.obterThumbnailDoVideo(videoUrl);

            int duracao = calcularDuracaoMusica(mp3FilePath);

            Artista artista = buscarOuCriarArtista(titulo);

            Musica musica = criarMusica(titulo, audioUrl, capaUrl, duracao, artista);

            LogUtil.registrarAviso(logger, "Musica: " + musica);

            Musica musicaSalva = salvarMusica(musica);

            LogUtil.registrarAviso(logger, "Musica SALVAAAAA: " + musicaSalva);


            LogUtil.registrarInfo(
                logger, "Processo de download, conversão, upload e salvamento da música concluído!"
            );

        } catch (Exception e) {
            LogUtil.registrarExcecao(logger, "Erro ao processar o download e conversão", e);
        }
    }

    private synchronized boolean tituloJaExiste(String titulo) {
        LogUtil.registrarInfo(logger, "Verificando se o título já existe no banco de dados: " + titulo);
        Optional<Musica> musicaExistente = musicaRepository.findByTitulo(titulo);
        boolean existe = musicaExistente.isPresent();
        if (existe) {
            LogUtil.registrarInfo(logger, "Título já existe no banco de dados.");
        }
        return existe;
    }

    private int calcularDuracaoMusica(String mp3FilePath) throws DuracaoMusicaException {
        try {
            Mp3File mp3file = new Mp3File(mp3FilePath);
            return (int) mp3file.getLengthInSeconds();
        } catch (Exception e) {
            String mensagemErro = "Erro ao calcular a duração da música: " + new File(mp3FilePath).getName();
            LogUtil.registrarExcecao(logger, mensagemErro, e);
            throw new DuracaoMusicaException(mensagemErro, e);
        }
    }

    private Artista buscarOuCriarArtista(String titulo) {
        String nomeArtista = extrairNomeDoArtista(titulo);
        return spotifyService.pesquisarOuSalvarArtistaPorNome(nomeArtista);
    }

    private String extrairNomeDoArtista(String titulo) {
        if (titulo.contains(" - ")) {
            return titulo.split(" - ")[0].trim();
        } else if (titulo.contains("feat.")) {
            return titulo.split("feat.")[0].trim();
        } else if (titulo.contains("Part.")) {
            return titulo.split("Part.")[0].trim();
        }
        return titulo;
    }

    private Musica criarMusica(String titulo, String audioUrl, String capaUrl, int duracao, Artista artista) {
        Musica musica = new Musica();
        musica.setTitulo(titulo);
        musica.setAudioUrl(audioUrl);
        musica.setCapaUrl(capaUrl);
        musica.setDuracao(duracao);
        musica.setArtista(artista);
        return musica;
    }

    private Musica salvarMusica(Musica musica) {
        return musicaRepository.save(musica);
    }

}
