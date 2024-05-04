package com.fatec.quintosemestre.projetomarketing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.quintosemestre.projetomarketing.model.dto.MensagemDTO;
import com.fatec.quintosemestre.projetomarketing.service.EmailService;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping(value = "/email-sender")
public class EmailController {

    @Autowired
    private EmailService service;

    @GetMapping
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Criado (Created)", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = MensagemDTO.class)) }),
            @ApiResponse(responseCode = "401", description = "Não Autorizado (Unauthorized)"),
            @ApiResponse(responseCode = "404", description = "Não Encontrado (Not Found)"),
            @ApiResponse(responseCode = "500", description = "Erro Interno (Internal Server Error)")
    })
    public ResponseEntity<Object> listar() throws Exception {
        String to = "mateus.scarin@gmail.com";
        String subject = "TESTE UAI";
        String text = "Teste de corpo de e-mail!";
        service.sendSimpleMessage(to, subject, text);
        return ResponseEntity.ok().body("Deu certo!");
    }
}
