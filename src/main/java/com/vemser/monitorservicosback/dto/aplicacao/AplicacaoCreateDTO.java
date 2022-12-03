package com.vemser.monitorservicosback.dto.aplicacao;

import com.vemser.monitorservicosback.enums.TipoDeploy;
import lombok.Data;

@Data
public class AplicacaoCreateDTO {
    private String gitUrl;
    private String workspace;
    private String javaOpts;
    private TipoDeploy tipoDeploy;
}
