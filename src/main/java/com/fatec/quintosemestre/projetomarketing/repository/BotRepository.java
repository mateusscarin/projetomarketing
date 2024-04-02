/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fatec.quintosemestre.projetomarketing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fatec.quintosemestre.projetomarketing.model.Bot;

/**
 *
 * @author Carlos Fernandes
 */
@Repository
public interface BotRepository extends JpaRepository<Bot, Long> {

   // @Query(value = "select new com.fatec.quintosemestre.projetomarketing.model.dto.BotDTO(b)"
    //        + " from Bot b")
   // public List<BotDTO> listarPorBotDTO();

    public boolean existsByNecessidadeId(Long idNecessidade);

    public List<Bot> findByNecessidadeId(Long idNecessidade);
}
