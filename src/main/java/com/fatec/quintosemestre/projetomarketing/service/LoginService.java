package com.fatec.quintosemestre.projetomarketing.service;

import org.springframework.http.ResponseEntity;

import com.fatec.quintosemestre.projetomarketing.model.dto.LoginDTO;

public interface LoginService {
    
    public ResponseEntity<Object> login(LoginDTO dto);

}
