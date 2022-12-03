package com.vemser.monitorservicosback.dto.kuberneteslog;

import lombok.Data;

@Data
public class Log {
    private String content;
    private String timestamp;
}
