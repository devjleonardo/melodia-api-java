package br.com.resolveai.melodia.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioCadastroRequestDTO {

    @NotBlank
    @Email
    @Schema(example = "julio@gmail.com")
    private String email;

    @NotBlank
    @Size(min = 6)
    @Schema(example = "123")
    private String senha;

    @NotBlank
    @Schema(example = "Júlio Silva")
    private String nome;

    @NotNull
    @Schema(example = "1999-02-12")
    private LocalDate dataNascimento;

}
