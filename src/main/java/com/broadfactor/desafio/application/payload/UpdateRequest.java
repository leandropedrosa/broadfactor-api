package com.broadfactor.desafio.application.payload;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UpdateRequest {

    @NotBlank
    private Long id;

    @Size(min = 4, max = 40)
    private String name;

    @Size(min = 3, max = 15)
    private String username;

    @Size(max = 15)
    @Email
    private String cnpj;

    @Size(max = 40)
    @Email
    private String email;

    @Size(min = 6, max = 20)
    private String password;
}
