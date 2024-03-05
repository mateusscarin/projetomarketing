package com.fatec.quintosemestre.projetomarketing.model.dto;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class LoginDTO {
    
    @NotNull(message = "O CPF precisa ser informado!")
    @NotBlank(message = "O CPF não pode estar em branco!")
    @CPF
    private String cpf;

    @NotNull(message = "A senha precisa informada!")
    @NotBlank(message = "A senha não pode estar em branco!")
    private String senha;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}
