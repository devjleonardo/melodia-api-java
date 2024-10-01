package br.com.resolveai.melodia.core.util;

import org.slf4j.Logger;

public final class LogUtil {

    private static final String MENSAGEM_ERRO = "{}: {}";
    private static final String DETALHES_ERRO = "Detalhes do erro. ";
    private static final String MENSAGEM_PADRAO = "{}";

    private LogUtil() {
    }

    public static void registrarExcecao(Logger logger, String mensagemErro, Exception e) {
        logger.error(MENSAGEM_ERRO, mensagemErro, e.getMessage());
        logger.debug(DETALHES_ERRO, e);
    }

    public static void registrarAviso(Logger logger, String mensagemWarn) {
        logger.warn(MENSAGEM_PADRAO, mensagemWarn);
    }

    public static void registrarInfo(Logger logger, String mensagemInfo) {
        logger.info(MENSAGEM_PADRAO, mensagemInfo);
    }

}
