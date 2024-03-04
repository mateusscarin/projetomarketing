package com.fatec.quintosemestre.projetomarketing.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.fatec.quintosemestre.projetomarketing.config.impl.UserDetailsImpl;
import com.fatec.quintosemestre.projetomarketing.model.Usuario;
import com.fatec.quintosemestre.projetomarketing.repository.UsuarioRepository;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCpf(cpf).orElseThrow(() -> new UsernameNotFoundException("Nenhum usuário foi encontrado com esse CPF!"));
        return new UserDetailsImpl(usuario.getId(), usuario.getCpf(), usuario.getSenha(), usuario.getTipoUsuario());
    }
    
}
