package br.com.resolveai.melodia.core.util;

public final class ApiEndpoint {

    public static final String BASE_API = "/api";
    public static final String API_VERSION = "/v1";

    // Define os endpoints principais
    public static final String MUSICAS = BASE_API + API_VERSION + "/musicas";
    public static final String PLAYLISTS = BASE_API + API_VERSION + "/playlists";
    public static final String PLAYLIST_MUSICAS = BASE_API + API_VERSION + "/playlists/{id}/musicas";
    public static final String USUARIOS = BASE_API + API_VERSION + "/usuarios";
    public static final String RANKING_ARTISTAS = BASE_API + API_VERSION + "/ranking/artistas";
    public static final String HISTORICO_ACOES = BASE_API + API_VERSION + "/historico/acoes";

    // Define os endpoints de "importações"
    public static final String IMPORTACOES = BASE_API + API_VERSION + "/importacoes";

    // Endpoint para Spotify
    public static final String SPOTIFY = BASE_API + API_VERSION + "/spotify";

    // Endpoint para YouTube
    public static final String YOUTUBE = BASE_API + API_VERSION + "/youtube";

    private ApiEndpoint() {
    }

}
