package com.fatec.quintosemestre.projetomarketing.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fatec.quintosemestre.projetomarketing.mapper.CustomObjectMapper;
import com.fatec.quintosemestre.projetomarketing.model.Chat;
import com.fatec.quintosemestre.projetomarketing.model.Mensagem;
import com.fatec.quintosemestre.projetomarketing.model.dto.MensagemDTO;
import com.fatec.quintosemestre.projetomarketing.model.dto.openai.ChatRequestDTO;
import com.fatec.quintosemestre.projetomarketing.model.dto.openai.ChatResponseDTO;
import com.fatec.quintosemestre.projetomarketing.model.enumerated.OrigemMensagem;
import com.fatec.quintosemestre.projetomarketing.repository.MensagemRepository;
import com.fatec.quintosemestre.projetomarketing.service.MyOpenAiService;

@Service
public class OpenAiServiceImpl implements MyOpenAiService {

    @Qualifier("gptRestTemplate")
    @Autowired
    private RestTemplate restTemplate;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiUrl;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private MensagemRepository mensagemRepository;

    @Autowired
    private CustomObjectMapper<Mensagem, MensagemDTO> mensagemMapper;

    private final Logger logger = LoggerFactory.getLogger(OpenAiServiceImpl.class);

    public void responder(Chat chat, String prompt) {

        MensagemDTO resposta = new MensagemDTO();

        try {

            ChatRequestDTO chatRequest = new ChatRequestDTO(model, "Your are a helpful assistant.", prompt);

            ChatResponseDTO response = restTemplate.postForObject(apiUrl, chatRequest,
                    ChatResponseDTO.class);

            if(response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
                throw new Exception("Desculpe, não conseguimos processar a sua resposta. Tente novamente mais tarde!");
            }

            resposta.setOrigemMensagem(OrigemMensagem.IA);
            resposta.setTexto(response.getChoices().get(0).getMessage().getContent());
            resposta.setIdChat(chat.getId());

            Mensagem mensagem = salvarMensagem(resposta);

            resposta.setId(mensagem.getId());
            resposta.setDataDeEnvio(mensagem.getDataDeEnvio());

            simpMessagingTemplate.convertAndSend("/topic/chat/" + chat.getId(), resposta);

        } catch (Exception e) {

            resposta.setOrigemMensagem(OrigemMensagem.SERVIDOR);
            resposta.setTexto("Desculpe, não conseguimos processar a sua resposta. Tente novamente mais tarde!");
            resposta.setIdChat(chat.getId());

            Mensagem mensagem = salvarMensagem(resposta);

            resposta.setId(mensagem.getId());
            resposta.setDataDeEnvio(mensagem.getDataDeEnvio());

            logger.error(e.getMessage());

            simpMessagingTemplate.convertAndSend("/topic/chat/" + chat.getId(), resposta);

        }

    }

    private Mensagem salvarMensagem(MensagemDTO dto) {
        Mensagem mensagem = mensagemMapper.converterParaEntidade(dto);
        mensagemRepository.saveAndFlush(mensagem);

        return mensagem;
    }

}
