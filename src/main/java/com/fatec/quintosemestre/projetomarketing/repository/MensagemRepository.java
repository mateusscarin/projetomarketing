package com.fatec.quintosemestre.projetomarketing.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fatec.quintosemestre.projetomarketing.model.Mensagem;

public interface MensagemRepository extends JpaRepository<Mensagem, Long>{
    
}
