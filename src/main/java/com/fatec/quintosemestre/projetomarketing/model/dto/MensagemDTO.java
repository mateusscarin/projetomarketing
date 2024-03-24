package com.fatec.quintosemestre.projetomarketing.model.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fatec.quintosemestre.projetomarketing.model.enumerated.OrigemMensagem;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MensagemDTO {

    private Long id;

    @NotNull(message = "O id do do chat precisar ser informado para que a mensagem seja vinculada ao respectivo chat!")
    private Long idChat;

    private String nomeUsuario;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(type = "string", pattern = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$", example = "2024-01-01 12:00:00")
    private LocalDateTime dataDeEnvio;

    @NotNull(message = "O texto precisa ser informado!")
    @NotBlank(message = "O campo texto não pode estar em branco!")
    private String texto;

    @NotNull(message = "A origem da mensagem deve ser informada!")
    @Enumerated(EnumType.STRING)
    private OrigemMensagem origemMensagem;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataDeEnvio() {
        return dataDeEnvio;
    }

    public void setDataDeEnvio(LocalDateTime dataDeEnvio) {
        this.dataDeEnvio = dataDeEnvio;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public OrigemMensagem getOrigemMensagem() {
        return origemMensagem;
    }

    public void setOrigemMensagem(OrigemMensagem origemMensagem) {
        this.origemMensagem = origemMensagem;
    }

    public Long getIdChat() {
        return idChat;
    }

    public void setIdChat(Long idChat) {
        this.idChat = idChat;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

}
