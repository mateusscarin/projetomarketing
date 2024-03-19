package com.fatec.quintosemestre.projetomarketing.model.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ChatDTO {

    private Long id;

    @NotNull(message = "O título do chat precisa ser informado")
    @NotBlank(message = "O título do chat não pode ser em branco!")
    private String titulo;

    @NotNull(message = "o ID da necessidade precisa ser informado para a abertura do chat!")
    private Long idNecessidade;

    private String nomeNecessidade;

    private Long idUsuarioProfissional;

    private String nomeUsuarioProfissional;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataAbertura;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataFechamento;

    public ChatDTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdNecessidade() {
        return idNecessidade;
    }

    public void setIdNecessidade(Long idNecessidade) {
        this.idNecessidade = idNecessidade;
    }

    public String getNomeNecessidade() {
        return nomeNecessidade;
    }

    public void setNomeNecessidade(String nomeNecessidade) {
        this.nomeNecessidade = nomeNecessidade;
    }

    public Long getIdUsuarioProfissional() {
        return idUsuarioProfissional;
    }

    public void setIdUsuarioProfissional(Long idUsuarioProfissional) {
        this.idUsuarioProfissional = idUsuarioProfissional;
    }

    public String getNomeUsuarioProfissional() {
        return nomeUsuarioProfissional;
    }

    public void setNomeUsuarioProfissional(String nomeUsuarioProfissional) {
        this.nomeUsuarioProfissional = nomeUsuarioProfissional;
    }

    public LocalDateTime getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(LocalDateTime dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public LocalDateTime getDataFechamento() {
        return dataFechamento;
    }

    public void setDataFechamento(LocalDateTime dataFechamento) {
        this.dataFechamento = dataFechamento;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

}
