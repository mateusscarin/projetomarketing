package com.fatec.quintosemestre.projetomarketing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fatec.quintosemestre.projetomarketing.model.Chat;
import com.fatec.quintosemestre.projetomarketing.model.dto.ChatDTO;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    
    @Query("select new com.fatec.quintosemestre.projetomarketing.model.dto.ChatDTO(chat) from Chat chat where chat.usuarioAbertura.id = :idUsuarioAbertura order by chat.dataAbertura desc")
    List<ChatDTO> listarPorUsuarioAbertura(@Param(value = "idUsuarioAbertura") Long idUsuarioAbertura);

    @Query("select new com.fatec.quintosemestre.projetomarketing.model.dto.ChatDTO(chat) from Chat chat order by chat.dataAbertura desc")
    List<ChatDTO> listar();
    
}
