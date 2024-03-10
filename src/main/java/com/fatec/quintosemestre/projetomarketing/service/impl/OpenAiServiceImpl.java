package com.fatec.quintosemestre.projetomarketing.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fatec.quintosemestre.projetomarketing.model.dto.ChatRequestDTO;
import com.fatec.quintosemestre.projetomarketing.model.dto.ChatResponseDTO;
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

    public String responder(String prompt) {
        
        ChatRequestDTO chatRequest = new ChatRequestDTO(model, "Você é um especialista em segurança de dados.", prompt);

        ChatResponseDTO response = restTemplate.postForObject(apiUrl, chatRequest, ChatResponseDTO.class);

        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            return "Desculpe, não conseguimos gerar a sua resposta no momento. Tente novamente mais tarde!";
        }

        return response.getChoices().get(0).getMessage().getContent();
    }

}
