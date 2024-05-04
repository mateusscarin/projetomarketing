package com.fatec.quintosemestre.projetomarketing.model.dto;

import java.time.LocalDate;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UsuarioDTO {

    private Long id;

    @NotNull(message = "O nome completo precisa ser informado!")
    @NotBlank(message = "O nome completo não pode estar em branco!")
    private String nomeCompleto;

    @NotNull(message = "A data de nascimento precisa ser informada!")
    private LocalDate dataNascimento;

    @NotNull(message = "O CPF precisa ser informado!")
    @NotBlank
    @CPF(message = "O CPF precisa ser válido!")
    private String cpf;

    @NotNull(message = "O email precisa ser informado!")
    @NotBlank(message = "O email não pode estar em branco!")
    @Email(message = "O email informado é inválido!")
    private String email;

    @NotNull(message = "A senha precisa ser informada!")
    @NotBlank(message = "A senha não pode estar em branco!")
    private String senha;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}
