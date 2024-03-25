package com.fatec.quintosemestre.projetomarketing.service;

import com.fatec.quintosemestre.projetomarketing.model.Chat;

public interface MyOpenAiService {
    
    public void responder(Chat idChat, String prompt);

}
