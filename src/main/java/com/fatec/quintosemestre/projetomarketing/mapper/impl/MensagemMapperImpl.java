package com.fatec.quintosemestre.projetomarketing.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fatec.quintosemestre.projetomarketing.mapper.CustomObjectMapper;
import com.fatec.quintosemestre.projetomarketing.model.Mensagem;
import com.fatec.quintosemestre.projetomarketing.model.dto.MensagenDTO;
import com.fatec.quintosemestre.projetomarketing.model.Usuario;
import com.fatec.quintosemestre.projetomarketing.model.Chat;

/**
 *
 * @author Adriano
 */

@Component
public class MensagemMapperImpl implements CustomObjectMapper<Mensagem, MensagenDTO> {

    @Override
    public MensagenDTO converterParaDto(Mensagem entity) {
        MensagenDTO dto = new MensagenDTO();
        dto.setDataDeEnvio(entity.getDataDeEnvio());
        dto.setId(entity.getId());
        dto.setIdChat(entity.getChat().getId());
        dto.setIdUsuario(entity.getUsuario().getId());
        dto.setNomeUsuario(entity.getUsuario().getNomeCompleto());
        dto.setTexto(entity.getTexto());
        return dto;
    }

    @Override
    public Mensagem converterParaEntidade(MensagenDTO dto) {
        Mensagem mensagem = new Mensagem();
        mensagem.setChat(new Chat(dto.getIdChat()));
        mensagem.setUsuario(new Usuario(dto.getIdUsuario()));
        mensagem.setTexto(dto.getTexto());
        return mensagem;
    }

    @Override
    public List<MensagenDTO> converterParaListaDeDtos(List<Mensagem> entityList) {
        List<MensagenDTO> lista = new ArrayList<>();
        for (Mensagem entity : entityList) {
            lista.add(converterParaDto(entity));
        }
        return lista;
    }

}
