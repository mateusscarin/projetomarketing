package com.fatec.quintosemestre.projetomarketing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.quintosemestre.projetomarketing.model.dto.LoginDTO;
import com.fatec.quintosemestre.projetomarketing.service.LoginService;

@RestController
@RequestMapping(value = "/auth")
public class LoginController {
    
    @Autowired
    private LoginService loginService;

    @PostMapping(value = "/token")
    public ResponseEntity<Object> login(@RequestBody LoginDTO dto) {
        return loginService.login(dto);
    }
    
}
