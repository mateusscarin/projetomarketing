package com.fatec.quintosemestre.projetomarketing.mapper.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.fatec.quintosemestre.projetomarketing.mapper.CustomObjectMapper;
import com.fatec.quintosemestre.projetomarketing.model.Chat;
import com.fatec.quintosemestre.projetomarketing.model.Necessidade;
import com.fatec.quintosemestre.projetomarketing.model.dto.ChatDTO;

@Component
public class ChatMapperImpl implements CustomObjectMapper<Chat, ChatDTO> {

    

    @Override
    public ChatDTO converterParaDto(Chat entity) {

        ChatDTO dto = new ChatDTO();
        dto.setDataAbertura(entity.getDataAbertura());
        dto.setDataFechamento(entity.getDataFechamento());
        dto.setId(entity.getId());
        dto.setIdNecessidade(entity.getNecessidade().getId());
        dto.setNomeNecessidade(entity.getNecessidade().getNome());
        dto.setTitulo(entity.getTitulo());
        Optional.ofNullable(entity.getUsuarioProfissional()).ifPresent(profissional -> {
            dto.setIdUsuarioProfissional(profissional.getId());
            dto.setNomeUsuarioProfissional(profissional.getNomeCompleto());
        });
        return dto;
    }

    @Override
    public Chat converterParaEntidade(ChatDTO dto) {

        Chat chat = new Chat();
        chat.setDataAbertura(dto.getDataAbertura());
        chat.setDataFechamento(dto.getDataFechamento());
        chat.setNecessidade(new Necessidade(dto.getIdNecessidade()));
        chat.setTitulo(dto.getTitulo());
        return chat;

    }

    @Override
    public List<ChatDTO> converterParaListaDeDtos(List<Chat> entityList) {
        List<ChatDTO> lista = new ArrayList<>();
        for(Chat entity : entityList) {
            lista.add(converterParaDto(entity));
        }
        return lista;
    }

}
