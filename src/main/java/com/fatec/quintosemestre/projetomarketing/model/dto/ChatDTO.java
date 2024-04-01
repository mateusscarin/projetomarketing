package com.fatec.quintosemestre.projetomarketing.model.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fatec.quintosemestre.projetomarketing.model.enumerated.TipoAssistente;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public class ChatDTO {

    private Long id;

    @NotNull(message = "o ID da necessidade precisa ser informado para a abertura do chat!")
    private Long idNecessidade;

    private String nomeNecessidade;

    private Long idUsuarioProfissional;

    private String nomeUsuarioProfissional;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(type = "string", pattern = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$", example = "2024-01-01 12:00:00")
    private LocalDateTime dataAbertura;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(type = "string", pattern = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$", example = "2024-01-01 12:00:00")

    private LocalDateTime dataFechamento;

    @NotNull(message = "O tipo de assistente deve ser informado!")
    @Enumerated(EnumType.STRING)
    private TipoAssistente tipoAssistente;

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

    public TipoAssistente getTipoAssistente() {
        return tipoAssistente;
    }

    public void setTipoAssistente(TipoAssistente tipoAssistente) {
        this.tipoAssistente = tipoAssistente;
    }

}
