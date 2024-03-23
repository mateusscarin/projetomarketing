package com.fatec.quintosemestre.projetomarketing.service.impl;

import com.fatec.quintosemestre.projetomarketing.mapper.CustomObjectMapper;
import com.fatec.quintosemestre.projetomarketing.model.Mensagem;
import com.fatec.quintosemestre.projetomarketing.model.dto.MensagenDTO;
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

    @Autowired
    private CustomObjectMapper<Mensagem, MensagenDTO> mensagemMapper;

    @Override
    public ResponseEntity<Object> cadastrar(MensagenDTO objeto) throws Exception {
        Mensagem mensagem = mensagemMapper.converterParaEntidade(objeto);

        mensagemRepository.saveAndFlush(mensagem);
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
        List<MensagenDTO> mensagem = mensagemMapper.converterParaListaDeDtos(mensagemRepository.findAll());
        if (mensagem.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("Não existem mensagem cadastradas no sistema!"));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(mensagem));
        }
    }

    @Override
    public ResponseEntity<Object> editar(Long idObjeto, MensagenDTO objeto) throws Exception {
        Mensagem dadosDto = mensagemMapper.converterParaEntidade(objeto);
        Mensagem aEditar = mensagemRepository.findById(idObjeto)
                .orElseThrow(
                        () -> new NoSuchElementException("A mensagem com ID " + idObjeto + " não foi encontrada!"));
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

}
