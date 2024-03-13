package com.fatec.quintosemestre.projetomarketing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fatec.quintosemestre.projetomarketing.model.Chat;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    
    List<Chat> findByUsuarioAberturaIdOrderByDataAberturaDesc(Long idUsuarioAbertura);

    List<Chat> findByNecessidadeId(Long idNecessidade);

}
