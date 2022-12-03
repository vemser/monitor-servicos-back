package com.vemser.monitorservicosback.dto.kuberneteslog;

import lombok.Data;

@Data
public class Info {
    private String podName;
    private String containerName;
    private String initContainerName;
    private String fromDate;
    private String toDate;
    private boolean truncated;
}
