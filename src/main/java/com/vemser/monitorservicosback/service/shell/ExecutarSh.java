package com.vemser.monitorservicosback.service.shell;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@Service
@Slf4j
public class ExecutarSh {
    public void executarDeployKub(String image, String workspace) throws IOException, InterruptedException, ExecutionException, TimeoutException {
        Runtime.getRuntime().exec(new String[]{"sudo", "/bin/sh", "-c", "/usr/local/bin/upkub/pull-minikube.sh", image, workspace}, null);

//        ProcessBuilder builder = new ProcessBuilder();
//
//
//
//        builder.command("sudo", "sh", "/usr/local/bin/upkub/pull-minikube.sh", image, workspace);
//        Process process = builder.start();
//        StreamGobbler streamGobbler =
//                new StreamGobbler(process.getInputStream(), process.getErrorStream(), System.out::println);
//        Future<?> future = Executors.newSingleThreadExecutor().submit(streamGobbler);
//        int exitCode = process.waitFor();
//        future.get(1, TimeUnit.MINUTES);
//        log.info("{} {}", image, exitCode == 0 ? "" : "não " + "publicado");
    }
}
