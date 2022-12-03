package com.vemser.monitorservicosback.dto.kuberneteslog;

import lombok.Data;

import java.util.List;

@Data
public class LogRoot {
    private Info info;
    private Selection selection;
    private List<Log> logs;
}
