package com.fatec.quintosemestre.projetomarketing.service;

import com.fatec.quintosemestre.projetomarketing.model.Chat;

public interface OpenAiService {
    
    public void responder(Chat idChat, String prompt);

}
