package com.fatec.quintosemestre.projetomarketing.service;

import com.fatec.quintosemestre.projetomarketing.model.dto.MensagemDTO;

public interface WebsocketService {

    void sincronizarMensagem(Long idChat, MensagemDTO dto);
    
}
