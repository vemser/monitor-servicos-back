package com.vemser.monitorservicosback.dto.kuberneteslog;

import lombok.Data;

@Data
public class Selection {
    private ReferencePoint referencePoint;
    private float offsetFrom;
    private float offsetTo;
    private String logFilePosition;
}
