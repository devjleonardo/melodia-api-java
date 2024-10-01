package br.com.resolveai.melodia.importacao.client;

import br.com.resolveai.melodia.importacao.dto.SpotifyArtistaDTO;
import br.com.resolveai.melodia.importacao.exception.SpotifyClientException;
import lombok.RequiredArgsConstructor;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpotifyClient {

    private static final String API_URL = "https://spotify23.p.rapidapi.com/search/";
    private static final String API_KEY = "a3d0a2f70dmshd60faf5f7c66a72p19f3c3jsn1b969b02dff6";
    private static final String API_HOST = "spotify23.p.rapidapi.com";

    public SpotifyArtistaDTO pesquisarArtista(String query) {
        try (AsyncHttpClient client = new DefaultAsyncHttpClient()) {
            String url = construirUrlDeBusca(query);

            String response = client.prepare("GET", url)
                .setHeader("x-rapidapi-key", API_KEY)
                .setHeader("x-rapidapi-host", API_HOST)
                .execute()
                .toCompletableFuture()
                .thenApply(Response::getResponseBody)
                .join();

            return extrairArtistaDaResposta(response);
        } catch (Exception e) {
            throw new SpotifyClientException("Erro ao buscar artista da API do Spotify", e);
        }
    }

    private String construirUrlDeBusca(String query) {
        return API_URL + "?q=" + query + "&type=multi&offset=0&limit=1&numberOfTopResults=1";
    }

    private SpotifyArtistaDTO extrairArtistaDaResposta(String response) {
        JSONObject jsonResponse = new JSONObject(response);
        JSONObject artists = jsonResponse.getJSONObject("artists");
        JSONArray artistItems = artists.getJSONArray("items");

        if (!artistItems.isEmpty()) {
            JSONObject artistData = artistItems.getJSONObject(0).getJSONObject("data");
            String artistName = artistData.getJSONObject("profile").getString("name");
            String avatarUrl = artistData.getJSONObject("visuals")
                .getJSONObject("avatarImage")
                .getJSONArray("sources")
                .getJSONObject(0)
                .getString("url");

            return new SpotifyArtistaDTO(artistName, avatarUrl);
        }

        return null;
    }

}
