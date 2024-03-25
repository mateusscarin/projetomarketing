/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fatec.quintosemestre.projetomarketing.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author Carlos Fernandes
 */

@Entity
public class Bot {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column 
   private Long id;
   
   
   @JoinColumn(name = "id_necessidade", unique = true)
   @OneToOne
   @NotNull(message = "A necessidade precisa ser informada para a abertura do Bot!")
   private Necessidade necessidade;
    
   @Column 
   private String modelo;
  
   @Column
   private String mensagemSistema;
   
   @Column
   private boolean ativo;
   
   @Column
   private LocalDateTime dataCriacao;

    public Bot() {
    }

    public Bot(Long id, Necessidade necessidade, String modelo, String mensagemSistema, boolean ativo) {
        this.id = id;
        this.necessidade = necessidade;
        this.modelo = modelo;
        this.mensagemSistema = mensagemSistema;
        this.ativo = ativo;
        this.dataCriacao = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Necessidade getNecessidade() {
        return necessidade;
    }

    public void setNecessidade(Necessidade necessidade) {
        this.necessidade = necessidade;
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

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
   
   
    @PrePersist
    public void prePersist() {
        dataCriacao = LocalDateTime.now();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Bot other = (Bot) obj;
        return Objects.equals(this.id, other.id);
    }
    
    
    
}
 
