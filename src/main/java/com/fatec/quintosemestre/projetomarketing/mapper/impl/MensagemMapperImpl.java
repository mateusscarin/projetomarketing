package com.fatec.quintosemestre.projetomarketing.mapper.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.fatec.quintosemestre.projetomarketing.mapper.CustomObjectMapper;
import com.fatec.quintosemestre.projetomarketing.model.Chat;
import com.fatec.quintosemestre.projetomarketing.model.Mensagem;
import com.fatec.quintosemestre.projetomarketing.model.Usuario;
import com.fatec.quintosemestre.projetomarketing.model.dto.MensagemDTO;

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
        if(entity.getUsuario() != null) {
            dto.setNomeUsuario(entity.getUsuario().getNomeCompleto());
        }
        dto.setTexto(entity.getTexto());
        dto.setOrigemMensagem(entity.getOrigemMensagem());
        return dto;
    }

    @Override
    public Mensagem converterParaEntidade(MensagemDTO dto) {

        Mensagem mensagem = new Mensagem();
        mensagem.setChat(new Chat(dto.getIdChat()));
        mensagem.setTexto(dto.getTexto());
        mensagem.setOrigemMensagem(dto.getOrigemMensagem());
        Optional.ofNullable(dto.getIdUsuario()).ifPresent(idUsuario -> mensagem.setUsuario(new Usuario(idUsuario)));

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
