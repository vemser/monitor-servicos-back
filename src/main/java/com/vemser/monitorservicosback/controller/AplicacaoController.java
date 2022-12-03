package com.vemser.monitorservicosback.controller;

import com.vemser.monitorservicosback.dto.aplicacao.AplicacaoCreateDTO;
import com.vemser.monitorservicosback.dto.aplicacao.AplicacaoDTO;
import com.vemser.monitorservicosback.service.AplicacaoService;
import com.vemser.monitorservicosback.service.DeployService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/aplicacao")
@Validated
@RequiredArgsConstructor
@Slf4j
public class AplicacaoController {

    private final AplicacaoService aplicacaoService;
    private final DeployService deployService;

    @PostMapping
    public AplicacaoDTO create(@RequestBody AplicacaoCreateDTO aplicacaoCreateDTO) {
        return aplicacaoService.create(aplicacaoCreateDTO);
    }

    @PostMapping(value = "/deploy", produces = MediaType.TEXT_PLAIN_VALUE)
    public String executarDeployKub(@RequestBody AplicacaoCreateDTO aplicacaoCreateDTO) throws IOException, InterruptedException {
        log.info("{}", aplicacaoCreateDTO);
        String deployed = deployService.executarDeploy(aplicacaoCreateDTO);
        return IOUtils.toString(new URL(deployed), StandardCharsets.UTF_8);
    }

    @GetMapping
    public List<AplicacaoDTO> getAll() {
        return aplicacaoService.getAll();
    }

    @DeleteMapping
    public void delete(Integer id) {
        aplicacaoService.delete(id);
    }
}
