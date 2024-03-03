package com.fatec.quintosemestre.projetomarketing.service.impl;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fatec.quintosemestre.projetomarketing.model.Administrador;
import com.fatec.quintosemestre.projetomarketing.model.dto.UsuarioDTO;
import com.fatec.quintosemestre.projetomarketing.repository.AdministradorRepository;
import com.fatec.quintosemestre.projetomarketing.service.AdministradorService;
import com.fatec.quintosemestre.projetomarketing.service.util.ApiResponse;

@Service
public class AdministradorServiceImpl implements AdministradorService {

    @Autowired
    private AdministradorRepository administradorRepository;

    @Override
    public ResponseEntity<Object> cadastrar(UsuarioDTO objeto) throws Exception {
        Administrador administrador = new Administrador();
        BeanUtils.copyProperties(objeto, administrador, "id", "tipoUsuario");
        Administrador objetoCriado = administradorRepository.saveAndFlush(administrador);
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
        List<Administrador> administradores = administradorRepository.findAll();
        if (administradores.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("Não existem administradores cadastrados no sistema!"));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(administradores));
        }
    }

    @Override
    public ResponseEntity<Object> editar(Long idObjeto, UsuarioDTO objeto) throws Exception {
        Administrador paraEditar = administradorRepository.findById(idObjeto).orElseThrow(() -> new NoSuchElementException("O administrador com ID " + idObjeto + " não foi encontrado!"));
        BeanUtils.copyProperties(objeto, paraEditar, "id", "tipoUsuario");
        administradorRepository.saveAndFlush(paraEditar);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(paraEditar));
    }

    @Override
    public ResponseEntity<Object> listarPorId(Long idObjeto) throws Exception {
        Administrador administrador = administradorRepository.findById(idObjeto).orElseThrow(() -> new NoSuchElementException("O administrador com ID " + idObjeto + " não foi encontrado!"));
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(administrador));
    }

}
