/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fatec.quintosemestre.projetomarketing.mapper.impl;

import com.fatec.quintosemestre.projetomarketing.mapper.CustomObjectMapper;
import com.fatec.quintosemestre.projetomarketing.model.Bot;
import com.fatec.quintosemestre.projetomarketing.model.Necessidade;
import com.fatec.quintosemestre.projetomarketing.model.dto.BotDTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 * @author Carlos Fernandes
 */

@Component
public class BotMapperImpl implements CustomObjectMapper<Bot, BotDTO>{
    
    
    @Override
    public BotDTO converterParaDto(Bot entity){
        
        BotDTO dto = new BotDTO();
        dto.setId(entity.getId());
        dto.setIdNecessidade(entity.getNecessidade().getId());
        dto.setMensagemSistema(entity.getMensagemSistema());
        dto.setModelo(entity.getModelo());
        dto.setAtivo(entity.getAtivo());
        dto.setDataCriacao(entity.getDataCriacao());
        
        return dto;
    }

    @Override
    public Bot converterParaEntidade(BotDTO dto) {
        
        Bot bot = new Bot();
        bot.setId(dto.getId());
        bot.setMensagemSistema(dto.getMensagemSistema());
        bot.setModelo(dto.getModelo());
        bot.setAtivo(dto.getAtivo());
        bot.setDataCriacao(dto.getDataCriacao());
        bot.setNecessidade(new Necessidade(dto.getIdNecessidade()));
        return bot;
    }

    @Override
    public List<BotDTO> converterParaListaDeDtos(List<Bot> entityList) {
        List<BotDTO> lista = new ArrayList<>();
        for(Bot entity : entityList) {
            lista.add(converterParaDto(entity));
        }
        return lista;
    }
}
