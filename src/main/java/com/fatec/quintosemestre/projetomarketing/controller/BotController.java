/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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

import com.fatec.quintosemestre.projetomarketing.model.dto.BotDTO;
import com.fatec.quintosemestre.projetomarketing.service.BotService;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

/**
 *
 * @author Carlos Fernandes
 */
@RestController
@RequestMapping(value = "/bot")
public class BotController {

    @Autowired
    private BotService service;

    @PostMapping
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Criado (Created)", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = BotDTO.class))}),
        @ApiResponse(responseCode = "401", description = "Não Autorizado (Unauthorized)"),
        @ApiResponse(responseCode = "404", description = "Não Encontrado (Not Found)"),
        @ApiResponse(responseCode = "500", description = "Erro interno (Internal Server Error)")
    })
    public ResponseEntity<Object> cadastrar(@RequestBody @Valid BotDTO dto) throws Exception {
        return service.cadastrar(dto);
    }

    @GetMapping
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Criado (Created)", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = BotDTO.class))}),
        @ApiResponse(responseCode = "401", description = "Não Autorizado (Unauthorized)"),
        @ApiResponse(responseCode = "404", description = "Não Encontrado (Not Found)"),
        @ApiResponse(responseCode = "500", description = "Erro interno (Internal Server Error)")
    })
    public ResponseEntity<Object> listar() throws Exception {
        return service.listar();
    }

    @GetMapping("/{id}")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Criado (Created)", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = BotDTO.class))}),
        @ApiResponse(responseCode = "401", description = "Não Autorizado (Unauthorized)"),
        @ApiResponse(responseCode = "404", description = "Não Encontrado (Not Found)"),
        @ApiResponse(responseCode = "500", description = "Erro interno (Internal Server Error)")
    })
    public ResponseEntity<Object> listarPorId(@PathVariable Long id) throws Exception {
        return service.listarPorId(id);
    }

    @PutMapping("/{id}")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Criado (Created)", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = BotDTO.class))}),
        @ApiResponse(responseCode = "401", description = "Não Autorizado (Unauthorized)"),
        @ApiResponse(responseCode = "404", description = "Não Encontrado (Not Found)"),
        @ApiResponse(responseCode = "500", description = "Erro interno (Internal Server Error)")
    })
    public ResponseEntity<Object> editar(@PathVariable Long id, @RequestBody @Valid BotDTO dto) throws Exception {
        return service.editar(id, dto);
    }

    @GetMapping("/necessidade/{idNecessidade}")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Criado (Created)", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = BotDTO.class))}),
        @ApiResponse(responseCode = "401", description = "Não Autorizado (Unauthorized)"),
        @ApiResponse(responseCode = "404", description = "Não Encontrado (Not Found)"),
        @ApiResponse(responseCode = "500", description = "Erro interno (Internal Server Error)")
    })
    public ResponseEntity<Object> listarPorNecessidade(@PathVariable Long id) throws Exception {
        return service.listarPorIdNecessidade(id);
    }

    @PutMapping("/{id}/status")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Criado (Created)", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = BotDTO.class))}),
        @ApiResponse(responseCode = "401", description = "Não Autorizado (Unauthorized)"),
        @ApiResponse(responseCode = "404", description = "Não Encontrado (Not Found)"),
        @ApiResponse(responseCode = "500", description = "Erro interno (Internal Server Error)")
    })
    public ResponseEntity<Object> alterarStatus(@PathVariable Long id) throws Exception {
        return service.alterarStatus(id);
    }
}
