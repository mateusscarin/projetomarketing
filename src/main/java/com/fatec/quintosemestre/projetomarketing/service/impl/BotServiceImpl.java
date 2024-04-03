/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fatec.quintosemestre.projetomarketing.service.impl;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fatec.quintosemestre.projetomarketing.mapper.CustomObjectMapper;
import com.fatec.quintosemestre.projetomarketing.model.Bot;
import com.fatec.quintosemestre.projetomarketing.model.dto.BotDTO;
import com.fatec.quintosemestre.projetomarketing.repository.BotRepository;
import com.fatec.quintosemestre.projetomarketing.service.BotService;
import com.fatec.quintosemestre.projetomarketing.service.util.ApiResponse;

/**
 *
 * @author Carlos Fernandes
 */
@Service
public class BotServiceImpl implements BotService {

    public static final String BOTMODEL = "gpt-3.5-turbo";

    @Autowired
    private BotRepository botRepository;

    @Autowired
    private CustomObjectMapper<Bot, BotDTO> botMapper;

    @Override
    public ResponseEntity<Object> listarPorIdNecessidade(Long id) {
        Bot entity = botRepository.findByNecessidadeId(id)
                .orElseThrow(() -> new NoSuchElementException("Não existe um bot cadastrado para essa necessidade!"));
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(botMapper.converterParaDto(entity)));
    }

    @Override
    public ResponseEntity<Object> cadastrar(BotDTO objeto) throws Exception {

        if (!objeto.getModelo().equals(BOTMODEL)) {
            return ResponseEntity.badRequest().body(new ApiResponse<>("Modelo está incompatível!"));
        }

        // if (botRepository.existsByNecessidadeId(objeto.getIdNecessidade())) {
        // return ResponseEntity.badRequest().body(new ApiResponse<>("Necessidade
        // informada já possui Bot cadastrado!"));
        // }

        Bot objetoCriado = botRepository.saveAndFlush(botMapper.converterParaEntidade(objeto));
        return ResponseEntity.created(
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(objetoCriado.getId())
                        .toUri())
                .build();
    }

    @Override
    public ResponseEntity<Object> listar() throws Exception {
        // List<BotDTO> botDTOs = botRepository.listarPorBotDTO();

        List<BotDTO> botDTOs = botMapper.converterParaListaDeDtos(botRepository.findAll());
        if (botDTOs.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("Não existem bots cadastrados no sistema"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(botDTOs));

    }

    @Override
    public ResponseEntity<Object> editar(Long idObjeto, BotDTO objeto) throws Exception {
        Bot dadosDto = botMapper.converterParaEntidade(objeto);
        Bot paraEditar = botRepository.findById(idObjeto)
                .orElseThrow(() -> new NoSuchElementException("O bot com ID " + idObjeto + " não foi encontrado!"));

        botRepository.findByNecessidadeId(objeto.getIdNecessidade()).ifPresent(botExistente -> {
            if (!botExistente.getId().equals(paraEditar.getId())) {
                throw new DataIntegrityViolationException("Já existe um bot cadastrado para a necessidade informada!");
            }
        });

        BeanUtils.copyProperties(dadosDto, paraEditar, "id");
        Bot objetoAtualizado = botRepository.saveAndFlush(paraEditar);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(botMapper.converterParaDto(objetoAtualizado)));
    }

    @Override
    public ResponseEntity<Object> listarPorId(Long idObjeto) throws Exception {
        Bot bot = botRepository.findById(idObjeto)
                .orElseThrow(() -> new NoSuchElementException("O bot com ID " + idObjeto + " não foi encontrado!"));

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(botMapper.converterParaDto(bot)));
    }

    @Override
    public ResponseEntity<Object> alterarStatus(Long id) {
        Bot bot = botRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("O bot com ID " + id + " não foi encontrado!"));
        bot.setAtivo(!bot.getAtivo());
        Bot botAtualizado = botRepository.saveAndFlush(bot);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(botMapper.converterParaDto(botAtualizado)));
    }

}
