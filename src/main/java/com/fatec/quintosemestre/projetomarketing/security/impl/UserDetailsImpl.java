package com.fatec.quintosemestre.projetomarketing.security.impl;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fatec.quintosemestre.projetomarketing.model.enumerated.TipoUsuario;

@SuppressWarnings("unused")
public class UserDetailsImpl implements UserDetails {

    private Long id;
    private String cpf;
    private String nomeCompleto;
    private String senha;
    private Collection<? extends GrantedAuthority> permissoes;

    public UserDetailsImpl(Long id, String cpf, String nomeCompleto, String senha, TipoUsuario tipoUsuario) {
        this.id = id;
        this.cpf = cpf;
        this.nomeCompleto = nomeCompleto;
        this.senha = senha;
        this.permissoes = Arrays.asList(new SimpleGrantedAuthority(tipoUsuario.toString()));
    }

    public Long getId() {
        return this.id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.permissoes;
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.nomeCompleto;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
}
