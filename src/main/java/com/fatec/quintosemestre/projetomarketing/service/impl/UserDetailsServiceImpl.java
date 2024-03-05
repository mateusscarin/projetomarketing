package com.fatec.quintosemestre.projetomarketing.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fatec.quintosemestre.projetomarketing.security.impl.UserDetailsImpl;
import com.fatec.quintosemestre.projetomarketing.model.Usuario;
import com.fatec.quintosemestre.projetomarketing.repository.UsuarioRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCpf(cpf).orElseThrow(() -> new UsernameNotFoundException("Nenhum usuário foi encontrado com esse CPF!"));
        return new UserDetailsImpl(usuario.getId(), usuario.getCpf(), usuario.getNomeCompleto(), usuario.getSenha(), usuario.getTipoUsuario());
    }
    
}
