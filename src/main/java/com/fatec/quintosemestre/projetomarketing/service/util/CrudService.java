package com.fatec.quintosemestre.projetomarketing.service.util;

import org.springframework.http.ResponseEntity;

public interface CrudService<T, R> {

    ResponseEntity<Object> cadastrar(T objeto) throws Exception;

    ResponseEntity<Object> listar() throws Exception;

    ResponseEntity<Object> editar(R idObjeto, T objeto) throws Exception;
    
    ResponseEntity<Object> listarPorId(R idObjeto) throws Exception;

}
