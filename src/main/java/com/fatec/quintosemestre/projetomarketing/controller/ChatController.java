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

import com.fatec.quintosemestre.projetomarketing.model.dto.ChatDTO;
import com.fatec.quintosemestre.projetomarketing.service.ChatService;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/chat")
public class ChatController {
    
    @Autowired
    private ChatService service;

    @PostMapping
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Criado (Created)", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ChatDTO.class)) }),
            @ApiResponse(responseCode = "401", description = "Não Autorizado (Unauthorized)"),
            @ApiResponse(responseCode = "404", description = "Não Encontrado (Not Found)"),
            @ApiResponse(responseCode = "500", description = "Erro Interno (Internal Server Error)")
    })
    public ResponseEntity<Object> cadastrar(@RequestBody @Valid ChatDTO dto) throws Exception {
        return service.cadastrar(dto);
    }

    @GetMapping
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sucesso (OK)", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ChatDTO.class)) }),
            @ApiResponse(responseCode = "401", description = "Não Autorizado (Unauthorized)"),
            @ApiResponse(responseCode = "404", description = "Não Encontrado (Not Found)"),
            @ApiResponse(responseCode = "500", description = "Erro Interno (Internal Server Error)")
    })
    public ResponseEntity<Object> listar() throws Exception {
        return service.listar();
    }
    
    @GetMapping("/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sucesso (OK)", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ChatDTO.class)) }),
            @ApiResponse(responseCode = "401", description = "Não Autorizado (Unauthorized)"),
            @ApiResponse(responseCode = "404", description = "Não Encontrado (Not Found)"),
            @ApiResponse(responseCode = "500", description = "Erro Interno (Internal Server Error)")
    })
    public ResponseEntity<Object> listarPorId(@PathVariable Long id) throws Exception {
        return service.listarPorId(id);
    }

    @GetMapping("/meus-chats")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sucesso (OK)", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ChatDTO.class)) }),
            @ApiResponse(responseCode = "401", description = "Não Autorizado (Unauthorized)"),
            @ApiResponse(responseCode = "404", description = "Não Encontrado (Not Found)"),
            @ApiResponse(responseCode = "500", description = "Erro Interno (Internal Server Error)")
    })
    public ResponseEntity<Object> listarPorUsuarioAbertura() throws Exception {
        return service.listarPorUsuarioAbertura();
    }

    @GetMapping("/necessidade/{idNecessidade}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sucesso (OK)", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ChatDTO.class)) }),
            @ApiResponse(responseCode = "401", description = "Não Autorizado (Unauthorized)"),
            @ApiResponse(responseCode = "404", description = "Não Encontrado (Not Found)"),
            @ApiResponse(responseCode = "500", description = "Erro Interno (Internal Server Error)")
    })
    public ResponseEntity<Object> listarPorUsuarioAbertura(@PathVariable Long idNecessidade) throws Exception {
        return service.listarPorNecessidade(idNecessidade);
    }
    
    @PutMapping("/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sucesso (OK)", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ChatDTO.class)) }),
            @ApiResponse(responseCode = "401", description = "Não Autorizado (Unauthorized)"),
            @ApiResponse(responseCode = "404", description = "Não Encontrado (Not Found)"),
            @ApiResponse(responseCode = "500", description = "Erro Interno (Internal Server Error)")
    })
    public ResponseEntity<Object> editar(@PathVariable Long id, @RequestBody @Valid ChatDTO dto) throws Exception {
        return service.editar(id, dto);
    }

    @PutMapping("/finalizar-ou-reabrir/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sucesso (OK)", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ChatDTO.class)) }),
            @ApiResponse(responseCode = "401", description = "Não Autorizado (Unauthorized)"),
            @ApiResponse(responseCode = "404", description = "Não Encontrado (Not Found)"),
            @ApiResponse(responseCode = "500", description = "Erro Interno (Internal Server Error)")
    })
    public ResponseEntity<Object> finalizarOuReabrirChat(@PathVariable Long id) throws Exception {
        throw new UnsupportedOperationException("Método não suportado");
    }

}
