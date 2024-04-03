package com.fatec.quintosemestre.projetomarketing.service.impl;

import com.fatec.quintosemestre.projetomarketing.mapper.CustomObjectMapper;
import com.fatec.quintosemestre.projetomarketing.model.Chat;
import com.fatec.quintosemestre.projetomarketing.model.Mensagem;
import com.fatec.quintosemestre.projetomarketing.model.Usuario;
import com.fatec.quintosemestre.projetomarketing.model.dto.MensagemDTO;
import com.fatec.quintosemestre.projetomarketing.model.enumerated.OrigemMensagem;
import com.fatec.quintosemestre.projetomarketing.model.enumerated.TipoAssistente;
import com.fatec.quintosemestre.projetomarketing.repository.ChatRepository;
import com.fatec.quintosemestre.projetomarketing.repository.MensagemRepository;
import com.fatec.quintosemestre.projetomarketing.repository.UsuarioRepository;
import com.fatec.quintosemestre.projetomarketing.service.MensagemService;
import com.fatec.quintosemestre.projetomarketing.service.MyOpenAiService;
import com.fatec.quintosemestre.projetomarketing.service.util.ApiResponse;
import java.util.List;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class MensagemServiceImpl implements MensagemService {

    @Autowired
    private MensagemRepository mensagemRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MyOpenAiService openAiService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private CustomObjectMapper<Mensagem, MensagemDTO> mensagemMapper;

    @Override
    public ResponseEntity<Object> cadastrar(MensagemDTO objeto) throws Exception {

        Chat chat = chatRepository.findById(objeto.getIdChat()).orElseThrow(
                () -> new NoSuchElementException("Chat com ID " + objeto.getId() + " não foi encontrado!"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt token = (Jwt) authentication.getPrincipal();
        Usuario usuarioLogado = usuarioRepository.findByCpf(token.getClaimAsString("principal")).get();

        // Se um usuário tentar enviar uma mensagem em um chat que não é dele, lançar um
        // erro
        if (!chat.getUsuarioAbertura().equals(usuarioLogado)) {
            throw new BadCredentialsException("Operação não autorizada");
        }

        Mensagem mensagem = mensagemMapper.converterParaEntidade(objeto);

        mensagem.setUsuario(usuarioLogado);
        mensagem.setOrigemMensagem(OrigemMensagem.USUARIO_ABERTURA);

        mensagemRepository.saveAndFlush(mensagem);

        simpMessagingTemplate.convertAndSend("/topic/chat/" + chat.getId(), mensagemMapper.converterParaDto(mensagem));

        if (chat.getTipoAssistente().equals(TipoAssistente.IA)) {
            openAiService.responder(chat, mensagem.getTexto());
        }

        return ResponseEntity.created(
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(mensagem.getId())
                        .toUri())
                .build();
    }

    @Override
    public ResponseEntity<Object> listar() throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt token = (Jwt) authentication.getPrincipal();
        Usuario usuarioLogado = usuarioRepository.findByCpf(token.getClaimAsString("principal")).get();

        List<MensagemDTO> mensagem = mensagemMapper
                .converterParaListaDeDtos(mensagemRepository.findByUsuarioId(usuarioLogado.getId()));
        if (mensagem.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("Sua lista de mensagens está vazia!"));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(mensagem));
        }
    }

    @Override
    public ResponseEntity<Object> editar(Long idObjeto, MensagemDTO objeto) throws Exception {

        Chat chat = chatRepository.findById(objeto.getIdChat()).orElseThrow(
                () -> new NoSuchElementException("Chat com ID " + objeto.getId() + " não foi encontrado!"));

        Mensagem dadosDto = mensagemMapper.converterParaEntidade(objeto);
        Mensagem aEditar = mensagemRepository.findById(idObjeto)
                .orElseThrow(
                        () -> new NoSuchElementException("A mensagem com ID " + idObjeto + " não foi encontrada!"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt token = (Jwt) authentication.getPrincipal();
        Usuario usuarioLogado = usuarioRepository.findByCpf(token.getClaimAsString("principal")).get();

        // Se um usuário tentar editar uma mensagem em um chat que não é dele, lançar um
        // erro
        if (!chat.getUsuarioAbertura().equals(usuarioLogado)) {
            throw new BadCredentialsException("Operação não autorizada");
        }

        // Se um usuário tentar editar uma mensagem que foi enviada por outro usuário,
        // lançar um erro
        if (!aEditar.getUsuario().equals(usuarioLogado)) {
            throw new BadCredentialsException("Operação não autorizada");
        }

        BeanUtils.copyProperties(dadosDto, aEditar, "id");
        mensagemRepository.saveAndFlush(aEditar);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(mensagemMapper.converterParaDto(aEditar)));
    }

    @Override
    public ResponseEntity<Object> listarPorId(Long idObjeto) throws Exception {
        Mensagem mensagem = mensagemRepository.findById(idObjeto)
                .orElseThrow(
                        () -> new NoSuchElementException("A mensagem com ID " + idObjeto + " não foi encontrada!"));
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(mensagemMapper.converterParaDto(mensagem)));
    }

    @Override
    public ResponseEntity<Object> listarPorIdDoChat(Long idChat) throws Exception {
        Chat chat = chatRepository.findById(idChat).orElseThrow(
                () -> new NoSuchElementException("Chat com ID " + idChat + " não foi encontrado!"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt token = (Jwt) authentication.getPrincipal();
        Usuario usuarioLogado = usuarioRepository.findByCpf(token.getClaimAsString("principal")).get();

        if (!chat.getUsuarioAbertura().equals(usuarioLogado)) {
            throw new BadCredentialsException("Operação não autorizada");
        }

        List<MensagemDTO> mensagensDoChat = mensagemMapper.converterParaListaDeDtos(mensagemRepository.findByChatId(idChat));

        if (mensagensDoChat.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("A lista de mensagens está vazia para este chat!"));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(mensagensDoChat));
        }

    }

}
