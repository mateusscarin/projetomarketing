package com.fatec.quintosemestre.projetomarketing.service;

import org.springframework.http.ResponseEntity;

import com.fatec.quintosemestre.projetomarketing.model.dto.ChatDTO;
import com.fatec.quintosemestre.projetomarketing.service.util.CrudService;

public interface ChatService extends CrudService<ChatDTO, Long> {

    ResponseEntity<Object> listarPorUsuarioAbertura() throws Exception;

    ResponseEntity<Object> listarPorNecessidade(Long idNecessidade) throws Exception;

    ResponseEntity<Object> finalizarOuReabrirChat(Long idChat) throws Exception;

    ResponseEntity<Object> atenderChat(Long idChat) throws Exception;


}
