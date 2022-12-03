package com.vemser.monitorservicosback.dto.kuberneteslog;

import lombok.Data;

@Data
public class ReferencePoint {
    private String timestamp;
    private float lineNum;
}
