package com.fatec.quintosemestre.projetomarketing.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fatec.quintosemestre.projetomarketing.mapper.CustomObjectMapper;
import com.fatec.quintosemestre.projetomarketing.model.Mensagem;
import com.fatec.quintosemestre.projetomarketing.model.dto.MensagemDTO;
import com.fatec.quintosemestre.projetomarketing.model.Usuario;
import com.fatec.quintosemestre.projetomarketing.model.Chat;

/**
 *
 * @author Adriano
 */

@Component
public class MensagemMapperImpl implements CustomObjectMapper<Mensagem, MensagemDTO> {

    @Override
    public MensagemDTO converterParaDto(Mensagem entity) {
        MensagemDTO dto = new MensagemDTO();
        dto.setDataDeEnvio(entity.getDataDeEnvio());
        dto.setId(entity.getId());
        dto.setIdChat(entity.getChat().getId());
        dto.setIdUsuario(entity.getUsuario().getId());
        dto.setNomeUsuario(entity.getUsuario().getNomeCompleto());
        dto.setTexto(entity.getTexto());
        return dto;
    }

    @Override
    public Mensagem converterParaEntidade(MensagemDTO dto) {
        Mensagem mensagem = new Mensagem();
        mensagem.setChat(new Chat(dto.getIdChat()));
        mensagem.setUsuario(new Usuario(dto.getIdUsuario()));
        mensagem.setTexto(dto.getTexto());
        return mensagem;
    }

    @Override
    public List<MensagemDTO> converterParaListaDeDtos(List<Mensagem> entityList) {
        List<MensagemDTO> lista = new ArrayList<>();
        for (Mensagem entity : entityList) {
            lista.add(converterParaDto(entity));
        }
        return lista;
    }

}
