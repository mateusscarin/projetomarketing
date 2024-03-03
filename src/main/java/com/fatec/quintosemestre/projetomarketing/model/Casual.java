package com.fatec.quintosemestre.projetomarketing.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("1")
public class Casual extends Usuario {
    
}
