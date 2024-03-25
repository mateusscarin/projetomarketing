package com.fatec.quintosemestre.projetomarketing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import com.fatec.quintosemestre.projetomarketing.model.dto.MensagemDTO;
import com.fatec.quintosemestre.projetomarketing.service.WebsocketService;

@Controller
public class WebSocketController {

    @Autowired
    private WebsocketService service;

    @MessageMapping("/chat/{idChat}")
    @SendTo("/topic/chat/{idChat}")
    public void handleMessage(@DestinationVariable String idChat, @RequestBody MensagemDTO dto) {
        service.sincronizarMensagem(Long.valueOf(idChat), dto);
    }

}
