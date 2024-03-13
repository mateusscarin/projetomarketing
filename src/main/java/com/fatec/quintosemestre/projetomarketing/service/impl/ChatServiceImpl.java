package com.fatec.quintosemestre.projetomarketing.service.impl;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fatec.quintosemestre.projetomarketing.model.Chat;
import com.fatec.quintosemestre.projetomarketing.model.dto.ChatDTO;
import com.fatec.quintosemestre.projetomarketing.repository.ChatRepository;
import com.fatec.quintosemestre.projetomarketing.service.ChatService;
import com.fatec.quintosemestre.projetomarketing.service.util.ApiResponse;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Override
    public ResponseEntity<Object> cadastrar(ChatDTO objeto) throws Exception {
        Chat dadosDto = objeto.toChat();
        Chat chat = new Chat();
        BeanUtils.copyProperties(dadosDto, chat, "id", "dataAbertura");
        Chat objetoCriado = chatRepository.saveAndFlush(chat);
        return ResponseEntity.created(
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(objetoCriado.getId())
                        .toUri())
                .build();
    }

    @Override
    public ResponseEntity<Object> listar() throws Exception {
        List<ChatDTO> chats = chatRepository.listar();
        if (chats.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("Não existem chats cadastrados no sistema!"));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(chats));
        }
    }

    @Override
    public ResponseEntity<Object> editar(Long idObjeto, ChatDTO objeto) throws Exception {
        Chat dadosDto = objeto.toChat();
        Chat paraEditar = chatRepository.findById(idObjeto)
                .orElseThrow(() -> new NoSuchElementException("O chat com ID " + idObjeto + " não foi encontrado!"));
        BeanUtils.copyProperties(dadosDto, paraEditar, "id", "dataAbertura");
        chatRepository.saveAndFlush(paraEditar);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(new ChatDTO(paraEditar)));
    }

    @Override
    public ResponseEntity<Object> listarPorId(Long idObjeto) throws Exception {
        Chat chat = chatRepository.findById(idObjeto)
                .orElseThrow(() -> new NoSuchElementException("O chat com ID " + idObjeto + " não foi encontrado!"));
        ChatDTO dto = new ChatDTO(chat);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(dto));
    }

    @Override
    public ResponseEntity<Object> listarPorUsuarioAbertura(Long idUsuarioAbertura) {
        List<ChatDTO> chats = chatRepository.listarPorUsuarioAbertura(idUsuarioAbertura);
        if (chats.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("O usuário com ID " + idUsuarioAbertura + " cadastrados no sistema!"));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(chats));
        }
    }

}
