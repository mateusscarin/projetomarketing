/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fatec.quintosemestre.projetomarketing.model.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 *
 * @author Carlos Fernandes
 */
public class BotDTO {

    private Long id;

    @NotNull(message = "o ID da necessidade precisa ser informado para a abertura do Bot!")
    private Long idNecessidade;

    private String nomeNecessidade;

    @NotNull(message = "O modelo precisa ser informado!")
    @NotBlank(message = "O modelo não pode ser em branco!")
    private String modelo;

    @NotNull(message = "A mensagem de configuração do bot deve ser informada!")
    @NotBlank(message = "A mensagem de configuração do bot não pode ser em branco!")
    private String mensagemSistema;

    private Boolean ativo;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataCriacao;

    public BotDTO() {
    }
    /*
     * public BotDTO(Bot bot) {
     * this.id = bot.getId();
     * this.modelo = bot.getModelo();
     * this.mensagemSistema = bot.getMensagemSistema();
     * this.ativo = bot.isAtivo();
     * this.dataCriacao = bot.getDataCriacao();
     * if (bot.getNecessidade() != null) {
     * this.idNecessidade = bot.getNecessidade().getId();
     * this.nomeNecessidade = bot.getNecessidade().getNome();
     * }
     * }
     */

    // public BotDTO(Long id, Long idNecessidade, String nomeNecessidade, String modelo, String mensagemSistema,
    //         boolean ativo) {
    //     this.id = id;
    //     this.idNecessidade = idNecessidade;
    //     this.nomeNecessidade = nomeNecessidade;
    //     this.modelo = modelo;
    //     this.mensagemSistema = mensagemSistema;
    //     this.ativo = ativo;
    //     this.dataCriacao = LocalDateTime.now();
    // }

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

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMensagemSistema() {
        return mensagemSistema;
    }

    public void setMensagemSistema(String mensagemSistema) {
        this.mensagemSistema = mensagemSistema;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

}
