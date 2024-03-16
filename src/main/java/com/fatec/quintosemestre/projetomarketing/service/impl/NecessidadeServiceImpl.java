/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fatec.quintosemestre.projetomarketing.service.impl;

import com.fatec.quintosemestre.projetomarketing.model.Necessidade;
import com.fatec.quintosemestre.projetomarketing.model.dto.NecessidadeDTO;
import com.fatec.quintosemestre.projetomarketing.repository.NecessidadeRepository;
import com.fatec.quintosemestre.projetomarketing.service.NecessidadeService;
import com.fatec.quintosemestre.projetomarketing.service.util.ApiResponse;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author Carlos Fernandes
 */
@Service
public class NecessidadeServiceImpl implements NecessidadeService {

    @Autowired
    private NecessidadeRepository necessidadeRepository;

    @Override
    public ResponseEntity<Object> cadastrar(NecessidadeDTO objeto) throws Exception {

        if (necessidadeRepository.existsByNome(objeto.getNome())) {
            return ResponseEntity.badRequest().body(new ApiResponse<>("Não é possível cadastrar a necessidade. Já existe outra necessidade com o mesmo nome."));
        }

        Necessidade necessidade = new Necessidade();
        necessidade.setNome(objeto.getNome());
        necessidade.setDescricao(objeto.getDescricao());

        Necessidade objetoCriado = necessidadeRepository.saveAndFlush(necessidade);
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
        List<Necessidade> necessidades = necessidadeRepository.findAll();
        if (necessidades.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("Não existem necessidades cadastradas no sistema!"));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(necessidades));
        }
    }

    @Override
    public ResponseEntity<Object> editar(Long idObjeto, NecessidadeDTO objeto) throws Exception {

        Necessidade paraEditar = necessidadeRepository.findById(idObjeto)
                .orElseThrow(() -> new NoSuchElementException("A necessidade com ID " + idObjeto + " não foi encontrada!"));

        Optional<Necessidade> opt = necessidadeRepository.findByNome(objeto.getNome());
        if (opt.isPresent() && !opt.get().getId().equals(idObjeto)) {
            return ResponseEntity.badRequest().body(new ApiResponse<>("Não é possível cadastrar a necessidade. Já existe outra necessidade com o mesmo nome."));
        }

        BeanUtils.copyProperties(objeto, paraEditar, "id");
        Necessidade objetoAtualizado = necessidadeRepository.saveAndFlush(paraEditar);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(objetoAtualizado));
    }

    @Override
    public ResponseEntity<Object> listarPorId(Long idObjeto) throws Exception {
        Necessidade necessidade = necessidadeRepository.findById(idObjeto)
                .orElseThrow(() -> new NoSuchElementException("A necessidade com ID " + idObjeto + " não foi encontrada!"));
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(necessidade));
    }

}
