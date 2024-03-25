/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fatec.quintosemestre.projetomarketing.service.impl;

import com.fatec.quintosemestre.projetomarketing.mapper.CustomObjectMapper;
import com.fatec.quintosemestre.projetomarketing.model.Bot;
import com.fatec.quintosemestre.projetomarketing.model.Necessidade;
import com.fatec.quintosemestre.projetomarketing.model.dto.BotDTO;
import com.fatec.quintosemestre.projetomarketing.repository.BotRepository;
import com.fatec.quintosemestre.projetomarketing.service.BotService;
import com.fatec.quintosemestre.projetomarketing.service.util.ApiResponse;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
        List<BotDTO> bots = botMapper.converterParaListaDeDtos(botRepository.findByNecessidadeId(id));
        if (bots.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("Não existem chats abertos para essa necessidade!"));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(bots));
        }
    }

    @Override
    public ResponseEntity<Object> cadastrar(BotDTO objeto) throws Exception {

        if (!objeto.getModelo().equals(BOTMODEL)) {
            return ResponseEntity.badRequest().body(new ApiResponse<>("Modelo está incompatível!"));
        }

        if (botRepository.existsByNecessidadeId(objeto.getIdNecessidade())) {
            return ResponseEntity.badRequest().body(new ApiResponse<>("Necessidade informada já possui Bot cadastrado!"));
        }

        Bot bot = new Bot();
        bot.setModelo(objeto.getModelo());
        bot.setMensagemSistema(objeto.getMensagemSistema());
        bot.setAtivo(objeto.isAtivo());
        Necessidade necessidade = new Necessidade(objeto.getIdNecessidade());
        bot.setNecessidade(necessidade);

        Bot objetoCriado = botRepository.saveAndFlush(bot);
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
        //List<BotDTO> botDTOs = botRepository.listarPorBotDTO();

        List<BotDTO> botDTOs = botMapper.converterParaListaDeDtos(botRepository.findAll());
        if (botDTOs.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("Não existe bot cadastrados no sistemas"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(botDTOs));

    }

    @Override
    public ResponseEntity<Object> editar(Long idObjeto, BotDTO objeto) throws Exception {
        Bot dadosDto = botMapper.converterParaEntidade(objeto);
        Bot paraEditar = botRepository.findById(idObjeto)
                .orElseThrow(() -> new NoSuchElementException("O bot com ID " + idObjeto + " não foi encontrada!"));

        List<Bot> bots = botRepository.findByNecessidadeId(objeto.getIdNecessidade());
        if (!bots.isEmpty()) {
            for (Bot bot : bots) {
                if (!bot.getId().equals(idObjeto)) {
                    return ResponseEntity.badRequest().body(new ApiResponse<>("Necessidade informada já possui Bot cadastrado!"));
                }
            }
        }

        BeanUtils.copyProperties(dadosDto, paraEditar, "id");
        Bot objetoAtualizado = botRepository.saveAndFlush(paraEditar);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(botMapper.converterParaDto(objetoAtualizado)));
    }

    @Override
    public ResponseEntity<Object> listarPorId(Long idObjeto) throws Exception {
        Bot bot = botRepository.findById(idObjeto)
                .orElseThrow(() -> new NoSuchElementException("O bot com ID " + idObjeto + " não foi encontrada!"));

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(botMapper.converterParaDto(bot)));
    }

    @Override
    public ResponseEntity<Object> mudancaStatus(Long id) {
        Bot bot = botRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("O bot com ID " + id + " não foi encontrada!"));

        if (bot.isAtivo()) {
            bot.setAtivo(false);
        } else {
            bot.setAtivo(true);
        }

        Bot botAtualizado = botRepository.saveAndFlush(bot);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(botMapper.converterParaDto(botAtualizado)));
    }

}
