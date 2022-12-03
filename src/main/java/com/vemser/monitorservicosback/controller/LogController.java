package com.vemser.monitorservicosback.controller;

import com.google.gson.Gson;
import com.vemser.monitorservicosback.dto.kubernetespod.Root;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@RestController
@RequestMapping("/log")
@Validated
@RequiredArgsConstructor
public class LogController {

    @GetMapping(value = "/{nomeProjeto}", produces = MediaType.TEXT_PLAIN_VALUE)
    public String getLog(@PathVariable("nomeProjeto") String nomeProjeto) {
        try {
            String idPod = procurarIdPod(nomeProjeto);
            return getLog(nomeProjeto, idPod);
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    private String getLog(String nomeProjeto, String idPod) throws IOException {
        String url = "http://localhost:8001/api/v1/namespaces/kubernetes-dashboard/services/http:kubernetes-dashboard:/proxy/api/v1/log/file/default/" + idPod + "/" + nomeProjeto + "?previous=false&timestamps=false";
        String s = IOUtils.toString(new URL(url), StandardCharsets.UTF_8);
        return s;
    }

    private String procurarIdPod(String nomeProjeto) throws IOException {
        String url = "http://localhost:8001/api/v1/namespaces/default/pods?labelSelector=app=" + nomeProjeto;

        String retorno = IOUtils.toString(new URL(url), StandardCharsets.UTF_8);
        Root root = new Gson().fromJson(retorno, Root.class);
        if (Objects.isNull(root.getItems()) || root.getItems().isEmpty()) {
            throw new IllegalArgumentException("Projeto não encontrado!");
        } else {
            return root.getItems().get(root.getItems().size() - 1).getMetadata().getName();
        }
    }
}
