package com.vemser.monitorservicosback.dto.aplicacao;

import lombok.Data;

@Data
public class AplicacaoDTO {
    private Integer idAplicacao;
    private String gitUrl;
    private String imagemDocker;
    private Integer porta;
    private String caminhoApp;
    private String url;
    private String log;
}
