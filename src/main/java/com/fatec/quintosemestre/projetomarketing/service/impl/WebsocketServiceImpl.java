package com.fatec.quintosemestre.projetomarketing.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

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
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private MyOpenAiService myOpenAiService;

    private final Logger logger = LoggerFactory.getLogger(OpenAiServiceImpl.class);

    @Override
    public void sincronizarMensagem(Long idChat, MensagemDTO dto) {

        // Chat chat = chatRepository.findById(idChat).orElseThrow(
        // () -> new MessageSyncronizationExeption("O chat informado não existe na nossa
        // base de dados!"));
        // Authentication authentication =
        // SecurityContextHolder.getContext().getAuthentication();
        // Jwt token = (Jwt) authentication.getPrincipal();
        // Usuario usuarioLogado =
        // usuarioRepository.findByCpf(token.getClaimAsString("principal")).get();

        // Mensagem objetoCriado = mensagemMapper.converterParaEntity(dto)
        // objetoCriado.setChat(chat);
        // objetoCriado.setUsuario(usuarioLogado)
        // mensagemRepository.saveAndFlush(objetoCriado));
        // dto = mensagemMapper.converterParaDto(objetoCriado);
        /*
         * if(dto.getOrigemMensagem().equals(OrigemMensagem.USUARIO_ABERTURA) &&
         * chat.getTipoAssistente().equals(TipoAssistente.IA)) {
         * openAiService.responder(idChat);
         * }
         */

        Mensagem mensagem = new Mensagem();

        chatRepository.findById(idChat).ifPresentOrElse(c -> {

            salvarMensagem(dto, mensagem);

            dto.setId(mensagem.getId());
            dto.setDataDeEnvio(mensagem.getDataDeEnvio());

            simpMessagingTemplate.convertAndSend("/topic/chat/" + idChat, dto);

            if (dto.getOrigemMensagem().equals(OrigemMensagem.USUARIO_ABERTURA)
                    && c.getTipoAssistente().equals(TipoAssistente.IA)) {
                myOpenAiService.responder(c, dto.getTexto());
            }

        }, () -> {

            dto.setTexto("O chat informado não existe na nossa base de dados!");
            dto.setOrigemMensagem(OrigemMensagem.SERVIDOR);

            logger.error(dto.getTexto());

            simpMessagingTemplate.convertAndSend("/topic/chat/" + idChat, dto);

        });

    }

    private void salvarMensagem(MensagemDTO dto, Mensagem target) {
        BeanUtils.copyProperties(dto, target, "id");
        mensagemRepository.saveAndFlush(target);
    }

}
