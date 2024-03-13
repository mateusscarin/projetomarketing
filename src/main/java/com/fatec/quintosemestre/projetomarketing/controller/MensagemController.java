package com.fatec.quintosemestre.projetomarketing.controller;

import com.fatec.quintosemestre.projetomarketing.model.dto.MensagenDTO;
import com.fatec.quintosemestre.projetomarketing.service.MensagemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/mensagem")
public class MensagemController {

    @Autowired
    private MensagemService service;

    @PostMapping
    public ResponseEntity<Object> cadastrar(@RequestBody @Valid MensagenDTO dto) throws Exception {
        return service.cadastrar(dto);
    }

    @GetMapping
    public ResponseEntity<Object> listar() throws Exception {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> listarPorId(@PathVariable Long id) throws Exception {
        return service.listarPorId(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> editar(@PathVariable Long id, @RequestBody @Valid MensagenDTO dto) throws Exception {
        return service.editar(id, dto);
    }
    
}
