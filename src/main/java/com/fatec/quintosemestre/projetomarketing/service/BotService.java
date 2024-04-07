/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fatec.quintosemestre.projetomarketing.service;

import com.fatec.quintosemestre.projetomarketing.model.dto.BotDTO;
import com.fatec.quintosemestre.projetomarketing.service.util.CrudService;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Carlos Fernandes
 */
public interface BotService extends CrudService<BotDTO>{
    
    ResponseEntity<Object> alterarStatus(Long id);
    
    ResponseEntity<Object> listarPorIdNecessidade(Long id);

}
