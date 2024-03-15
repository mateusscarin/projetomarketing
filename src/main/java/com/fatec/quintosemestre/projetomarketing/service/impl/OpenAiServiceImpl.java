package com.fatec.quintosemestre.projetomarketing.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fatec.quintosemestre.projetomarketing.model.Chat;
import com.fatec.quintosemestre.projetomarketing.model.Mensagem;
import com.fatec.quintosemestre.projetomarketing.model.dto.MensagemDTO;
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

    private final Logger logger = LoggerFactory.getLogger(OpenAiServiceImpl.class);

    public void responder(Chat chat, String prompt) {

        // Bot bot = botRepository.findByNecessidadeId(chat.getNecessidade().getId()).orElseThrow(() -> new MessageSyncronizationExeption("Não existe um assistente cadastrado para o(a) " + chat.getNecessidade().getNome()));

        // ChatRequestDTO chatRequest = new ChatRequestDTO(model, "Your are a helpful assistant.", prompt);

        // ChatResponseDTO response = restTemplate.postForObject(apiUrl, chatRequest,
        // ChatResponseDTO.class);

        // if (response == null || response.getChoices() == null ||
        // response.getChoices().isEmpty()) {
        // return "Desculpe, não conseguimos gerar a sua resposta no momento. Tente
        // novamente mais tarde!";
        // }

        // return response.getChoices().get(0).getMessage().getContent();

        MensagemDTO resposta = new MensagemDTO();
        Mensagem mensagem = new Mensagem();

        try {

            resposta.setOrigemMensagem(OrigemMensagem.IA);
            resposta.setTexto("[GPT] resposta da sua pergunta"); // Resposta vinda do chat gpt

            salvarMensagem(resposta, mensagem);

            resposta.setId(mensagem.getId());
            resposta.setDataDeEnvio(mensagem.getDataDeEnvio());

            simpMessagingTemplate.convertAndSend("/topic/chat/" + chat.getId(), resposta);

        } catch (Exception e) {

            resposta.setOrigemMensagem(OrigemMensagem.SERVIDOR);
            resposta.setTexto("Desculpe, não conseguimos processar a sua resposta. Tente novamente mais tarde!");
            
            salvarMensagem(resposta, mensagem);

            resposta.setId(mensagem.getId());
            resposta.setDataDeEnvio(mensagem.getDataDeEnvio());

            logger.error(resposta.getTexto());

            simpMessagingTemplate.convertAndSend("/topic/chat/" + chat.getId(), resposta);

        }

    }

    private void salvarMensagem(MensagemDTO dto, Mensagem target) {
        BeanUtils.copyProperties(dto, target, "id");
        mensagemRepository.saveAndFlush(target);
    }

}
