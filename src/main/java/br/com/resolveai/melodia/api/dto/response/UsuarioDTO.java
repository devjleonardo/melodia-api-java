package br.com.resolveai.melodia.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class UsuarioDTO {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "julio@gmail.com")
    private String email;

    @Schema(example = "Júlio Silva")
    private String nome;

    @Schema(example = "1999-02-12")
    private LocalDate dataNascimento;

    @Schema(example = "2024-09-07T12:45:00")
    private LocalDateTime dataUltimoLogin;

    @Schema(example = "https://julio-foto-perfil.jpg")
    private String fotoPerfilUrl;

//    @Schema(example = "Masculino")
//    private String genero;
//
//    @Schema(example = "Premium")
//    private String plano;
//
//    @Schema(example = "Feliz")
//    private String estadoEmocionalAtual;
//
//    @Schema(example = "[\"Rock\", \"Pop\", \"MPB\"]")
//    private List<String> preferenciasMusicais;
//
//    @Schema(example = "[\"Bohemian Rhapsody\", \"Yesterday\", \"Imagine\"]")
//    private List<String> historicoMusicas;
//
//    @Schema(example = "[\"Email\", \"SMS\"]")
//    private List<String> preferenciasNotificacao;
//
//    @Schema(example = "[\"Público\", \"Privado\"]")
//    private List<String> configuracoesPrivacidade;
//
//    private List<PlaylistDTO> playlists;
//
//    private List<UsuarioResumoDTO> seguidores;
//
//    private List<UsuarioResumoDTO> seguindo;

}
