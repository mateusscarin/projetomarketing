package com.fatec.quintosemestre.projetomarketing.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

import com.fatec.quintosemestre.projetomarketing.model.enumerated.OrigemMensagem;

@Entity
public class Mensagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    
    @JoinColumn(nullable = false, name = "id_chat")
    @ManyToOne
    @NotNull(message="O id do do chat precisar ser informado para que a mensagem seja vinculada ao respectivo chat!")
    private Chat chat;
      
    @JoinColumn(nullable = false, name="id_usuario")
    @ManyToOne
    @NotNull(message="O id do usuario precisar ser informado para que a mensagem seja vinculada a um usuario")
    private Usuario usuario;
    
    @Column(name = "data_de_envio")
    private LocalDateTime dataDeEnvio;

    @Column(name = "texto")
    @NotBlank
    @NotNull
    private String texto;

    @Column(name = "origem_mensagem")
    @NotNull(message = "A origem da mensagem deve ser informada!")
    @Enumerated(EnumType.STRING)
    private OrigemMensagem origemMensagem;

    public Mensagem() {

    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

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

    @PrePersist
    @PreUpdate
    public void atribuirDataDeEnvio() {
        this.dataDeEnvio = LocalDateTime.now();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.id);
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
        final Mensagem other = (Mensagem) obj;
        return Objects.equals(this.id, other.id);
    }

}
