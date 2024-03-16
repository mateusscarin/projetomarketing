/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fatec.quintosemestre.projetomarketing.repository;

import com.fatec.quintosemestre.projetomarketing.model.Necessidade;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Carlos Fernandes
 */

@Repository
public interface NecessidadeRepository extends JpaRepository<Necessidade, Long>{
    
   public boolean existsByNome(String nome); 
 
   public Optional<Necessidade> findByNome(String nome);
    
}
