package com.fatec.quintosemestre.projetomarketing.model.dto;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MensagenDTO {
    private Long id;
    

    private LocalDateTime dataDeEnvio;
    
    @NotNull(message = "O texto precisa ser informado!")
    @NotBlank(message = "O campo texto não pode estar em branco!")
    private String texto;

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


    
    
}
