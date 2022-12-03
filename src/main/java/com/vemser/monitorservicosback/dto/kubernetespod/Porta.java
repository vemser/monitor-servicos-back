package com.vemser.monitorservicosback.dto.kubernetespod;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Porta {
    private Integer porta;
    private String repo;
}
