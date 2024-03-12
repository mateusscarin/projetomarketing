/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fatec.quintosemestre.projetomarketing.model.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 *
 * @author Carlos Fernandes
 */
public class NecessidadeDTO {
    
    
    private Long id;
    
    @NotNull(message = "O nome completo precisa ser informado!")
    @NotBlank(message = "O nome completo não pode estar em branco!")
    private String nome;
   
    
    @NotNull(message = "O nome completo precisa ser informado!")
    @NotBlank(message = "O nome completo não pode estar em branco!")
    private String descricao;

    public NecessidadeDTO() {
    }

    public NecessidadeDTO(Long id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    
}
