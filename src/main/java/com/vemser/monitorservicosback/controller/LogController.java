package com.vemser.monitorservicosback.controller;

import com.google.gson.Gson;
import com.vemser.monitorservicosback.dto.kuberneteslog.LogRoot;
import com.vemser.monitorservicosback.dto.kubernetespod.Root;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@RestController
@RequestMapping("/log")
@Validated
@RequiredArgsConstructor
public class LogController {
    //    private static final String URL = "http://vemser-dbc.dbccompany.com.br:38001";
    private static final String URL = "http://localhost:8001/";

    @GetMapping(value = "/{nomeProjeto}", produces = MediaType.TEXT_PLAIN_VALUE)
    public String getLog(@PathVariable("nomeProjeto") String nomeProjeto,
                         @RequestParam(required = false, defaultValue = "200") Long linhas) {
        try {
            String idPod = procurarIdPod(nomeProjeto);
            return getLog(nomeProjeto, idPod, linhas);
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    private String getLog(String nomeProjeto, String idPod, Long linhas) throws IOException {
        linhas += 2000000000;
        String url = URL + "/api/v1/namespaces/kubernetes-dashboard/services/http:kubernetes-dashboard:/proxy/api/v1/log/default/" + idPod + "/" + nomeProjeto + "?logFilePosition=end&referenceTimestamp=newest&referenceLineNum=0&offsetFrom=2000000000&offsetTo=" + linhas + "&previous=false";
        String s = IOUtils.toString(new URL(url), StandardCharsets.UTF_8);
        LogRoot root = new Gson().fromJson(s, LogRoot.class);
        StringBuilder builder = new StringBuilder();
        root.getLogs().stream().forEach(log -> builder.append(log.getContent() + "\n"));

        return IOUtils.toString(new URL(builder.toString()), StandardCharsets.UTF_8);
    }

    private String procurarIdPod(String nomeProjeto) throws IOException {
        String url = URL + "/api/v1/namespaces/default/pods?labelSelector=app=" + nomeProjeto;

        String retorno = IOUtils.toString(new URL(url), StandardCharsets.UTF_8);
        Root root = new Gson().fromJson(retorno, Root.class);
        if (Objects.isNull(root.getItems()) || root.getItems().isEmpty()) {
            throw new IllegalArgumentException("Projeto não encontrado!");
        } else {
            return root.getItems().get(root.getItems().size() - 1).getMetadata().getName();
        }
    }
}
