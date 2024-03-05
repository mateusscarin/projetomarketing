package com.fatec.quintosemestre.projetomarketing.config;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fatec.quintosemestre.projetomarketing.model.Administrador;
import com.fatec.quintosemestre.projetomarketing.repository.AdministradorRepository;

@Configuration()
public class InitialDatabaseConfig {

    @Autowired
    private AdministradorRepository administradorRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @EventListener
    public void appReady(ApplicationReadyEvent event) throws Exception {

        Administrador administrador = new Administrador();
        administrador.setCpf("08854272035");
        administrador.setDataNascimento(LocalDate.now());
        administrador.setSenha(passwordEncoder.encode("123Mudar*"));
        administrador.setNomeCompleto("admin uai");

        administradorRepository.save(administrador);
        
    }

}
