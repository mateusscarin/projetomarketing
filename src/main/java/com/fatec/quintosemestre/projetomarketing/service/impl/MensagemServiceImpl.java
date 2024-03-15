
package com.fatec.quintosemestre.projetomarketing.service.impl;

import com.fatec.quintosemestre.projetomarketing.model.Mensagem;
import com.fatec.quintosemestre.projetomarketing.model.dto.MensagemDTO;
import com.fatec.quintosemestre.projetomarketing.repository.MensagemRepository;
import com.fatec.quintosemestre.projetomarketing.service.MensagemService;
import com.fatec.quintosemestre.projetomarketing.service.util.ApiResponse;
import java.util.List;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class MensagemServiceImpl implements MensagemService {
    
    @Autowired
    private MensagemRepository mensagemRepository;

    @Override
    public ResponseEntity<Object> cadastrar(MensagemDTO objeto) throws Exception {
        Mensagem mensagem = new Mensagem();
        BeanUtils.copyProperties(objeto, mensagem, "id");
        Mensagem objetoCriado = mensagemRepository.saveAndFlush(mensagem);
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
        List<Mensagem> mensagem = mensagemRepository.findAll();
        if (mensagem.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("Não existem mensagem cadastradas no sistema!"));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(mensagem));
        }
    }

    @Override
    public ResponseEntity<Object> editar(Long idObjeto, MensagemDTO objeto) throws Exception {
        Mensagem aEditar = mensagemRepository.findById(idObjeto)
                .orElseThrow(
                        () -> new NoSuchElementException("A mensagem com ID " + idObjeto + " não foi encontrada!"));
        BeanUtils.copyProperties(objeto, aEditar, "id");
        Mensagem objetoAtualizado = mensagemRepository.saveAndFlush(aEditar);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(objetoAtualizado));
    }

    @Override
    public ResponseEntity<Object> listarPorId(Long idObjeto) throws Exception {
        Mensagem mensagem = mensagemRepository.findById(idObjeto)
                .orElseThrow(
                        () -> new NoSuchElementException("A mensagem com ID " + idObjeto + " não foi encontrada!"));
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(mensagem));
    }

}
