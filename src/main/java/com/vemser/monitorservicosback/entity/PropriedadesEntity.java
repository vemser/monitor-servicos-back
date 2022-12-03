package com.vemser.monitorservicosback.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "PROPRIEDADES")
public class PropriedadesEntity {
    @Id
    @Column(name = "chave")
    private String chave;

    @Column(name = "valor")
    private String valor;
}
