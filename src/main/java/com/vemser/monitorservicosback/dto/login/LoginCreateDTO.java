package com.vemser.monitorservicosback.dto.login;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginCreateDTO {
    @NotNull
    @NotBlank
    @Schema(example = "admin@dbccompany.com.br")
    private String email;

    @NotNull
    @NotBlank
    @Schema(example = "123")
    private String senha;

    @NotNull
    @NotBlank
    @Schema(example = "Admin")
    private String nome;
}

