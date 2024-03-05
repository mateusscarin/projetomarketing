package com.fatec.quintosemestre.projetomarketing.service.impl;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fatec.quintosemestre.projetomarketing.model.Casual;
import com.fatec.quintosemestre.projetomarketing.model.dto.UsuarioDTO;
import com.fatec.quintosemestre.projetomarketing.repository.CasualRepository;
import com.fatec.quintosemestre.projetomarketing.service.CasualService;
import com.fatec.quintosemestre.projetomarketing.service.util.ApiResponse;

@Service
public class CasualServiceImpl implements CasualService {

    @Autowired
    private CasualRepository casualRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<Object> cadastrar(UsuarioDTO objeto) throws Exception {
        Casual casual = new Casual();
        BeanUtils.copyProperties(objeto, casual, "id", "tipoUsuario");
        casual.setSenha(passwordEncoder.encode(casual.getSenha()));
        Casual objetoCriado = casualRepository.saveAndFlush(casual);
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
        List<Casual> usuariosCasuais = casualRepository.findAll();
        if (usuariosCasuais.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("Não existem Usuários Casuais cadastrados no sistema!"));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(usuariosCasuais));
        }
    }

    @Override
    public ResponseEntity<Object> editar(Long idObjeto, UsuarioDTO objeto) throws Exception {
        Casual paraEditar = casualRepository.findById(idObjeto)
                .orElseThrow(() -> new NoSuchElementException("O usuário com ID " + idObjeto + " não foi encontrado!"));
        BeanUtils.copyProperties(objeto, paraEditar, "id", "tipoUsuario");
        paraEditar.setSenha(passwordEncoder.encode(paraEditar.getSenha()));
        casualRepository.saveAndFlush(paraEditar);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(paraEditar));
    }

    @Override
    public ResponseEntity<Object> listarPorId(Long idObjeto) throws Exception {
        Casual casual = casualRepository.findById(idObjeto)
                .orElseThrow(() -> new NoSuchElementException("O usuário com ID " + idObjeto + " não foi encontrado!"));
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(casual));
    }

}
