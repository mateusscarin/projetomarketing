package com.fatec.quintosemestre.projetomarketing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.quintosemestre.projetomarketing.model.dto.MensagemDTO;
import com.fatec.quintosemestre.projetomarketing.service.MensagemService;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/mensagem")
public class MensagemController {

    @Autowired
    private MensagemService service;

    @PostMapping
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Criado (Created)", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = MensagemDTO.class)) }),
            @ApiResponse(responseCode = "401", description = "Não Autorizado (Unauthorized)"),
            @ApiResponse(responseCode = "404", description = "Não Encontrado (Not Found)"),
            @ApiResponse(responseCode = "500", description = "Erro Interno (Internal Server Error)")
    })
    public ResponseEntity<Object> cadastrar(@RequestBody @Valid MensagemDTO dto) throws Exception {
        return service.cadastrar(dto);
    }

    @GetMapping
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Criado (Created)", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = MensagemDTO.class)) }),
            @ApiResponse(responseCode = "401", description = "Não Autorizado (Unauthorized)"),
            @ApiResponse(responseCode = "404", description = "Não Encontrado (Not Found)"),
            @ApiResponse(responseCode = "500", description = "Erro Interno (Internal Server Error)")
    })
    public ResponseEntity<Object> listar() throws Exception {
        return service.listar();
    }

    @GetMapping("/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Criado (Created)", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = MensagemDTO.class)) }),
            @ApiResponse(responseCode = "401", description = "Não Autorizado (Unauthorized)"),
            @ApiResponse(responseCode = "404", description = "Não Encontrado (Not Found)"),
            @ApiResponse(responseCode = "500", description = "Erro Interno (Internal Server Error)")
    })
    public ResponseEntity<Object> listarPorId(@PathVariable Long id) throws Exception {
        return service.listarPorId(id);
    }

    @PutMapping("/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Criado (Created)", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = MensagemDTO.class)) }),
            @ApiResponse(responseCode = "401", description = "Não Autorizado (Unauthorized)"),
            @ApiResponse(responseCode = "404", description = "Não Encontrado (Not Found)"),
            @ApiResponse(responseCode = "500", description = "Erro Interno (Internal Server Error)")
    })
    public ResponseEntity<Object> editar(@PathVariable Long id, @RequestBody @Valid MensagemDTO dto) throws Exception {
        return service.editar(id, dto);
    }

}
