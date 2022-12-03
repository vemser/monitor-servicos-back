package com.vemser.monitorservicosback.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "APLICACAO")
public class AplicacaoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APP_SEQUENCIA")
    @SequenceGenerator(name = "APP_SEQUENCIA", sequenceName = "seq_aplicacao", allocationSize = 1)
    @Column(name = "id_aplicacao")
    private Integer idAplicacao;

    @Column(name = "GIT_URL")
    private String gitUrl;

    @Column(name = "IMAGEM_DOCKER")
    private String imagemDocker;

    @Column(name = "CAMINHO_APP")
    private String caminhoApp;

    @Column(name = "PORTA")
    private Integer porta;
}
