package br.com.resolveai.melodia.core.jackson;

import br.com.resolveai.melodia.api.dto.response.*;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@JsonComponent
public class PageJsonSerializer extends JsonSerializer<Page<?>> {

	private static final String CONTENT = "content";
	private static final String MUSICAS = "musicas";
	private static final String USUARIOS = "usuarios";
	private static final String ARTISTA_RANKINGS = "artistasRanking";
	private static final String HISTORICO_MUSICAS = "historicoMusicas";
	private static final String PLAYLISTS = "playlists";
	private static final String USUARIOS_RESUMO = "usuariosResumo";
	private static final String SIZE = "size";
	private static final String TOTAL_ELEMENTS = "totalElements";
	private static final String TOTAL_PAGES = "totalPages";
	private static final String NUMBER = "number";

	@Override
	public void serialize(Page<?> page, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeStartObject();

		writeContentField(gen, page);
		writePaginationFields(gen, page);

		gen.writeEndObject();
	}

	private void writeContentField(JsonGenerator gen, Page<?> page) throws IOException {
		String contentFieldName = getFieldNameFromDTOType(page);
		gen.writeObjectField(contentFieldName, page.getContent());
	}

	private String getFieldNameFromDTOType(Page<?> page) {
		return page.getContent().stream().findFirst()
			.map(this::getFieldNameForDTO)
			.orElse(CONTENT);
	}

	private String getFieldNameForDTO(Object entity) {
		if (entity instanceof MusicaDTO) {
			return MUSICAS;
		} else if (entity instanceof UsuarioDTO) {
			return USUARIOS;
		} else if (entity instanceof ArtistaRankingDTO) {
			return ARTISTA_RANKINGS;
		} else if (entity instanceof HistoricoMusicaDTO) {
			return HISTORICO_MUSICAS;
		} else if (entity instanceof PlaylistDTO) {
			return PLAYLISTS;
		} else if (entity instanceof UsuarioResumoDTO) {
			return USUARIOS_RESUMO;
		}
		return CONTENT;
	}

	private void writePaginationFields(JsonGenerator gen, Page<?> page) throws IOException {
		Map<String, Object> paginationFields = createPaginationFields(page);
		for (Map.Entry<String, Object> entry : paginationFields.entrySet()) {
			gen.writeObjectField(entry.getKey(), entry.getValue());
		}
	}

	private Map<String, Object> createPaginationFields(Page<?> page) {
		Map<String, Object> fields = new HashMap<>();
		fields.put(SIZE, page.getSize());
		fields.put(TOTAL_ELEMENTS, page.getTotalElements());
		fields.put(TOTAL_PAGES, page.getTotalPages());
		fields.put(NUMBER, page.getNumber());
		return fields;
	}

}
