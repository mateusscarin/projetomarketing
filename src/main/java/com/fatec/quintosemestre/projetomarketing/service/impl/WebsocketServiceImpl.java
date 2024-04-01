package com.fatec.quintosemestre.projetomarketing.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.fatec.quintosemestre.projetomarketing.mapper.CustomObjectMapper;
import com.fatec.quintosemestre.projetomarketing.model.Mensagem;
import com.fatec.quintosemestre.projetomarketing.model.dto.MensagemDTO;
import com.fatec.quintosemestre.projetomarketing.model.enumerated.OrigemMensagem;
import com.fatec.quintosemestre.projetomarketing.model.enumerated.TipoAssistente;
import com.fatec.quintosemestre.projetomarketing.repository.ChatRepository;
import com.fatec.quintosemestre.projetomarketing.repository.MensagemRepository;
import com.fatec.quintosemestre.projetomarketing.service.MyOpenAiService;
import com.fatec.quintosemestre.projetomarketing.service.WebsocketService;

@Service
public class WebsocketServiceImpl implements WebsocketService {

    @Autowired
    private MensagemRepository mensagemRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private CustomObjectMapper<Mensagem, MensagemDTO> mensagemMapper;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private MyOpenAiService myOpenAiService;

    private final Logger logger = LoggerFactory.getLogger(OpenAiServiceImpl.class);

    @Override
    public void sincronizarMensagem(Long idChat, MensagemDTO dto) {

        chatRepository.findById(idChat).ifPresentOrElse(c -> {

            dto.setIdChat(idChat);

            Mensagem mensagemCriada = salvarMensagem(dto);

            dto.setId(mensagemCriada.getId());
            dto.setDataDeEnvio(mensagemCriada.getDataDeEnvio());

            simpMessagingTemplate.convertAndSend("/topic/chat/" + idChat, dto);

            if (c.getTipoAssistente().equals(TipoAssistente.IA)) {
                myOpenAiService.responder(c, dto.getTexto());
            }

        }, () -> {

            dto.setTexto("O chat informado não existe na nossa base de dados!");
            dto.setOrigemMensagem(OrigemMensagem.SERVIDOR);

            logger.error(dto.getTexto());

            simpMessagingTemplate.convertAndSend("/topic/chat/" + idChat, dto);

        });

    }

    private Mensagem salvarMensagem(MensagemDTO dto) {
        Mensagem mensagem = mensagemMapper.converterParaEntidade(dto);
        mensagemRepository.saveAndFlush(mensagem);
        return mensagem;
    }

}
