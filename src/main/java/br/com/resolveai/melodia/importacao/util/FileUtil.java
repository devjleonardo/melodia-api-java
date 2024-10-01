package br.com.resolveai.melodia.importacao.util;

import br.com.resolveai.melodia.core.util.LogUtil;
import org.slf4j.Logger;

import java.io.File;

public final class FileUtil {

    public static void verificarOuCriarDiretorio(String caminhoDiretorio, Logger logger) {
        LogUtil.registrarInfo(logger, "Verificando se o diretório " + caminhoDiretorio + " existe.");
        File diretorio = new File(caminhoDiretorio);
        if (!diretorio.exists()) {
            LogUtil.registrarInfo(logger, "Diretório " + caminhoDiretorio + " não existe. Tentando criar...");
            boolean criado = diretorio.mkdirs();
            if (criado) {
                LogUtil.registrarInfo(logger, "Diretório " + caminhoDiretorio + " criado com sucesso.");
            } else {
                LogUtil.registrarAviso(logger, "Falha ao criar o diretório " + caminhoDiretorio);
                throw new RuntimeException("Não foi possível criar o diretório: " + caminhoDiretorio);
            }
        } else {
            LogUtil.registrarInfo(logger, "Diretório " + caminhoDiretorio + " já existe.");
        }
    }

    public static void verificarPermissoesDiretorio(String caminhoDiretorio, Logger logger) {
        LogUtil.registrarInfo(logger, "Verificando permissões do diretório " + caminhoDiretorio);
        File diretorio = new File(caminhoDiretorio);

        if (!diretorio.canWrite()) {
            LogUtil.registrarAviso(logger, "Sem permissão de escrita no diretório " + caminhoDiretorio);
            throw new RuntimeException("Sem permissão de escrita no diretório: " + caminhoDiretorio);
        } else {
            LogUtil.registrarInfo(logger, "Permissão de escrita verificada para o diretório " + caminhoDiretorio);
        }

        if (!diretorio.canRead()) {
            LogUtil.registrarAviso(logger, "Sem permissão de leitura no diretório " + caminhoDiretorio);
            throw new RuntimeException("Sem permissão de leitura no diretório: " + caminhoDiretorio);
        } else {
            LogUtil.registrarInfo(logger, "Permissão de leitura verificada para o diretório " + caminhoDiretorio);
        }
    }

    public static void limparDiretorioTemporario(String caminhoDiretorio, Logger logger) {
        LogUtil.registrarInfo(logger, "Limpando o diretório temporário " + caminhoDiretorio);
        File diretorio = new File(caminhoDiretorio);
        if (!diretorio.exists()) {
            LogUtil.registrarAviso(logger, "Diretório temporário " + caminhoDiretorio + " não existe.");
            return;
        }

        File[] arquivos = diretorio.listFiles();
        if (arquivos != null) {
            for (File arquivo : arquivos) {
                LogUtil.registrarInfo(logger, "Tentando excluir o arquivo " + arquivo.getName());
                if (!arquivo.delete()) {
                    LogUtil.registrarAviso(logger, "Falha ao excluir o arquivo: " + arquivo.getName());
                } else {
                    LogUtil.registrarInfo(logger, "Arquivo " + arquivo.getName() + " excluído com sucesso.");
                }
            }
        } else {
            LogUtil.registrarAviso(logger, "Nenhum arquivo encontrado no diretório " + caminhoDiretorio);
        }
    }

    private FileUtil() {
    }

}
