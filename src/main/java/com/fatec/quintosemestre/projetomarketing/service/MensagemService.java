package com.fatec.quintosemestre.projetomarketing.service;

import org.springframework.http.ResponseEntity;

import com.fatec.quintosemestre.projetomarketing.model.dto.MensagemDTO;
import com.fatec.quintosemestre.projetomarketing.service.util.CrudService;

public interface MensagemService extends CrudService<MensagemDTO> {

    ResponseEntity<Object> listarPorIdDoChat(Long idChat) throws Exception;

}
