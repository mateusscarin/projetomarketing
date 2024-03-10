package com.fatec.quintosemestre.projetomarketing.model.dto;

import java.util.ArrayList;
import java.util.List;

public class ChatRequestDTO {

    private String model;
    private List<ChatMessageDTO> messages;
    private int n = 1;
    private double temperature;

    public ChatRequestDTO() {
    }

    public ChatRequestDTO(String model, String systemPrompt, String userPrompt) {
        this.model = model;
        
        this.messages = new ArrayList<>();
        this.messages.add(new ChatMessageDTO("system", systemPrompt));
        this.messages.add(new ChatMessageDTO("user", userPrompt));
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<ChatMessageDTO> getMessages() {
        return messages;
    }

    public void setMessages(List<ChatMessageDTO> messages) {
        this.messages = messages;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

}
