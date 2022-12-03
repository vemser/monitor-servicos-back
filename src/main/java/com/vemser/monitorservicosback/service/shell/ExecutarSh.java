package com.vemser.monitorservicosback.service.shell;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.*;

@Service
@Slf4j
public class ExecutarSh {
    public void executarDeployKub(String image, String workspace) throws IOException, InterruptedException, ExecutionException, TimeoutException {
        ProcessBuilder builder = new ProcessBuilder();
        builder.command("sudo", "sh", "/usr/local/bin/upkub/pull-minikube.sh", image, workspace);
        Process process = builder.start();
        StreamGobbler streamGobbler =
                new StreamGobbler(process.getInputStream(), process.getErrorStream(), System.out::println);
        Future<?> future = Executors.newSingleThreadExecutor().submit(streamGobbler);
        int exitCode = process.waitFor();
        future.get(3, TimeUnit.MINUTES);
        log.info("{} {}", image, exitCode == 0 ? "" : "não " + "publicado");
    }
}
