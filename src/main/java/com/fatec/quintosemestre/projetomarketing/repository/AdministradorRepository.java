package com.fatec.quintosemestre.projetomarketing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fatec.quintosemestre.projetomarketing.model.Administrador;

@Repository
public interface AdministradorRepository extends JpaRepository<Administrador, Long> {
    
}
