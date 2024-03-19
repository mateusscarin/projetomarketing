package com.fatec.quintosemestre.projetomarketing.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(unique = true)
    @NotNull(message = "O título do chat precisa ser informado")
    @NotBlank(message = "O título do chat não pode ser em branco!")
    private String titulo;

    @JoinColumn(name = "id_necessidade")
    @ManyToOne
    @NotNull(message = "A necessidade precisa ser informada para a abertura do chat!")
    private Necessidade necessidade;

    @JoinColumn(name = "id_usuario_abertura")
    @ManyToOne
    @NotNull(message = "O ID do usuário que iniciou o chat precisa ser informado!")
    private Usuario usuarioAbertura;

    @JoinColumn(name = "id_usuario_profissional")
    @ManyToOne
    private Profissional usuarioProfissional;

    @Column(name = "data_abertura")
    private LocalDateTime dataAbertura;

    @Column(name = "data_fechamento")
    private LocalDateTime dataFechamento;

    public Chat() {

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

    public Usuario getUsuarioAbertura() {
        return usuarioAbertura;
    }

    public void setUsuarioAbertura(Usuario usuarioAbertura) {
        this.usuarioAbertura = usuarioAbertura;
    }

    public Profissional getUsuarioProfissional() {
        return usuarioProfissional;
    }

    public void setUsuarioProfissional(Profissional usuarioProfissional) {
        this.usuarioProfissional = usuarioProfissional;
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

    @PrePersist
    @PreUpdate
    public void inicializarDataAbertura() {
        this.dataAbertura = LocalDateTime.now();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Chat other = (Chat) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
