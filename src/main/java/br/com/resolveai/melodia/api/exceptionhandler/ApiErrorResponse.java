package br.com.resolveai.melodia.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Getter
@Schema(name = "Erro")
public class ApiErrorResponse {

    @Schema(example = "400")
    private Integer status;

    @Schema(example = "Dados inválidos")
    private String titulo;

    @Schema(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente")
    private String detalhe;

    @Schema(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente")
    private String message;

    @Schema(description = "Lista de campos que gerarm o erro")
    private List<CampoComErro> campoComErros;

    public static class ApiErrorResponseBuilder {
        public ApiErrorResponseBuilder message(Object message) {
            this.message = ApiErrorFormatter.extrairMensagemException(message);
            return this;
        }
    }

    @Builder
    @Getter
    @Schema(name = "CampoErro")
    public static class CampoComErro {

        @Schema(example = "nome")
        private String nome;

        @Schema(example = "O nome é obrigatório")
        private String erro;

    }

}
