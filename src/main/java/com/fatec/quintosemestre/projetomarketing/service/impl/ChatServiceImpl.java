package com.fatec.quintosemestre.projetomarketing.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fatec.quintosemestre.projetomarketing.mapper.CustomObjectMapper;
import com.fatec.quintosemestre.projetomarketing.model.Chat;
import com.fatec.quintosemestre.projetomarketing.model.Profissional;
import com.fatec.quintosemestre.projetomarketing.model.Usuario;
import com.fatec.quintosemestre.projetomarketing.model.dto.ChatDTO;
import com.fatec.quintosemestre.projetomarketing.repository.ChatRepository;
import com.fatec.quintosemestre.projetomarketing.repository.UsuarioRepository;
import com.fatec.quintosemestre.projetomarketing.service.ChatService;
import com.fatec.quintosemestre.projetomarketing.service.util.ApiResponse;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CustomObjectMapper<Chat, ChatDTO> chatMapper;

    @Override
    public ResponseEntity<Object> cadastrar(ChatDTO objeto) throws Exception {
        Chat chat = chatMapper.converterParaEntidade(objeto);

        /**
         * Utilizando o token de autenticação para recuperar os dados do usuário logado
         * Dessa forma, o front não precisa passar o id do usuário via parâmetro ou no
         * corpo da request
         */
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt token = (Jwt) authentication.getPrincipal();
        Usuario usuarioLogado = usuarioRepository.findByCpf(token.getClaimAsString("principal")).get();

        chat.setUsuarioAbertura(usuarioLogado);

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
        List<ChatDTO> chats = chatMapper.converterParaListaDeDtos(chatRepository.findAll());
        if (chats.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("Não existem chats cadastrados no sistema!"));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(chats));
        }
    }

    @Override
    public ResponseEntity<Object> editar(Long idObjeto, ChatDTO objeto) throws Exception {
        Chat dadosDto = chatMapper.converterParaEntidade(objeto);
        Chat paraEditar = chatRepository.findById(idObjeto)
                .orElseThrow(() -> new NoSuchElementException("O chat com ID " + idObjeto + " não foi encontrado!"));
        BeanUtils.copyProperties(dadosDto, paraEditar, "id", "usuarioAbertura", "usuarioProfissional", "dataAbertura",
                "dataFechamento");
        chatRepository.saveAndFlush(paraEditar);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(chatMapper.converterParaDto(paraEditar)));
    }

    @Override
    public ResponseEntity<Object> listarPorId(Long idObjeto) throws Exception {
        Chat chat = chatRepository.findById(idObjeto)
                .orElseThrow(() -> new NoSuchElementException("O chat com ID " + idObjeto + " não foi encontrado!"));
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(chatMapper.converterParaDto(chat)));
    }

    @Override
    public ResponseEntity<Object> listarPorUsuarioAbertura() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt token = (Jwt) authentication.getPrincipal();
        Usuario usuarioLogado = usuarioRepository.findByCpf(token.getClaimAsString("principal")).get();

        List<ChatDTO> chats = chatMapper.converterParaListaDeDtos(
                chatRepository.findByUsuarioAberturaIdOrderByDataAberturaDesc(usuarioLogado.getId()));
        if (chats.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("Sua lista de chats está vazia!"));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(chats));
        }
    }

    @Override
    public ResponseEntity<Object> listarPorNecessidade(Long idNecessidade) {
        List<ChatDTO> chats = chatMapper.converterParaListaDeDtos(
                chatRepository.findByNecessidadeId(idNecessidade));
        if (chats.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("Não existem chats abertos para essa necessidade!"));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(chats));
        }
    }

    @Override
    public ResponseEntity<Object> finalizarOuReabrirChat(Long idChat) {

        Chat paraFinalizar = chatRepository.findById(idChat)
                .orElseThrow(() -> new NoSuchElementException("O chat com ID " + idChat + " não foi encontrado!"));
        if (paraFinalizar.getDataFechamento() == null) {
            paraFinalizar.setDataFechamento(LocalDateTime.now());
        } else {
            paraFinalizar.setDataFechamento(null);
        }

        chatRepository.saveAndFlush(paraFinalizar);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Status do chat alterado com sucesso!"));

    }

    @Override
    public ResponseEntity<Object> atenderChat(Long idChat) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt token = (Jwt) authentication.getPrincipal();
        Usuario usuarioLogado = usuarioRepository.findByCpf(token.getClaimAsString("principal")).get();

        Chat paraAtender = chatRepository.findById(idChat)
                .orElseThrow(() -> new NoSuchElementException("O chat com ID " + idChat + " não foi encontrado!"));

        paraAtender.setUsuarioProfissional(new Profissional(usuarioLogado.getId()));

        chatRepository.saveAndFlush(paraAtender);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Atendimento inciado com sucesso!"));

    }

}
