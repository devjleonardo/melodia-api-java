package br.com.resolveai.melodia.importacao.util;

import br.com.resolveai.melodia.importacao.exception.SslContextCreationException;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import reactor.netty.http.client.HttpClient;

public final class HttpClientUtil {

    private static final String TLS_VERSION = "TLSv1.2";
    private static final String MSG_ERRO_CRIACAO_SSL_CONTEXT = "Falha ao criar o contexto SSL";

    private HttpClientUtil() {
    }

    public static HttpClient createSecureHttpClient() {
        return HttpClient.create()
            .secure(sslContextSpec -> {
                try {
                    sslContextSpec.sslContext(SslContextBuilder.forClient()
                        .protocols(TLS_VERSION)
                        .trustManager(InsecureTrustManagerFactory.INSTANCE)
                        .build());
                } catch (Exception e) {
                    throw new SslContextCreationException(MSG_ERRO_CRIACAO_SSL_CONTEXT, e);
                }
            });
    }

}
