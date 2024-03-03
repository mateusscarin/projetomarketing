package com.fatec.quintosemestre.projetomarketing.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("0")
public class Administrador extends Usuario {

}
