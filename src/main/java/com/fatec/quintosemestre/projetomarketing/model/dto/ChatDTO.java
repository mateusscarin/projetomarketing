package com.fatec.quintosemestre.projetomarketing.model.dto;

import java.time.LocalDateTime;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fatec.quintosemestre.projetomarketing.model.Chat;
import com.fatec.quintosemestre.projetomarketing.model.Necessidade;
import com.fatec.quintosemestre.projetomarketing.model.Profissional;
import com.fatec.quintosemestre.projetomarketing.model.Usuario;

import jakarta.validation.constraints.NotNull;

public class ChatDTO {

    private Long id;

    @NotNull(message = "o ID da necessidade precisa ser informado para a abertura do chat!")
    private Long idNecessidade;

    private String nomeNecessidade;

    @NotNull(message = "O ID do usuário que iniciou o chat precisa ser informado!")
    private Long idUsuarioAbertura;

    private String nomeUsuarioAbertura;

    private Long idUsuarioProfissional;

    private String nomeUsuarioProfissional;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataAbertura;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataFechamento;

    public ChatDTO() {

    }

    public ChatDTO(Chat chat) {

        this.id = chat.getId();
        this.idNecessidade = chat.getNecessidade().getId();
        this.nomeNecessidade = chat.getNecessidade().getNome();
        this.idUsuarioAbertura = chat.getUsuarioAbertura().getId();
        this.nomeUsuarioAbertura = chat.getUsuarioAbertura().getNomeCompleto();
        Optional.ofNullable(chat.getUsuarioProfissional()).ifPresent(u -> {
            this.idUsuarioProfissional = u.getId();
            this.nomeUsuarioProfissional = u.getNomeCompleto();
        });
        this.dataAbertura = chat.getDataAbertura();
        this.dataFechamento = chat.getDataFechamento();

    }

    public Chat toChat() {

        Chat chat = new Chat();
        chat.setDataAbertura(this.getDataAbertura());
        chat.setDataFechamento(this.getDataFechamento());
        chat.setId(this.getId());
        chat.setNecessidade(new Necessidade(this.getIdNecessidade()));
        chat.setUsuarioAbertura(new Usuario(this.getIdUsuarioAbertura()));
        Optional.ofNullable(this.getIdUsuarioProfissional()).ifPresent(
                idUsuarioProfissional -> chat.setUsuarioProfissional(new Profissional(idUsuarioProfissional)));
        return chat;

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

    public Long getIdUsuarioAbertura() {
        return idUsuarioAbertura;
    }

    public void setIdUsuarioAbertura(Long idUsuarioAbertura) {
        this.idUsuarioAbertura = idUsuarioAbertura;
    }

    public String getNomeUsuarioAbertura() {
        return nomeUsuarioAbertura;
    }

    public void setNomeUsuarioAbertura(String nomeUsuarioAbertura) {
        this.nomeUsuarioAbertura = nomeUsuarioAbertura;
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

}
