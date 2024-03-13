package com.fatec.quintosemestre.projetomarketing.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("2")
public class Profissional extends Usuario {

    public Profissional(Long id) {
        super(id);
    }

}
