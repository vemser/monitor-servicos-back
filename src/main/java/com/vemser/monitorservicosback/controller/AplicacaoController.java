package com.vemser.monitorservicosback.controller;

import com.vemser.monitorservicosback.dto.aplicacao.AplicacaoCreateDTO;
import com.vemser.monitorservicosback.dto.aplicacao.AplicacaoDTO;
import com.vemser.monitorservicosback.service.AplicacaoService;
import com.vemser.monitorservicosback.service.DeployService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public String executarDeployKub(@RequestBody AplicacaoCreateDTO aplicacaoCreateDTO) {
        log.info("{}", aplicacaoCreateDTO);
        String deployed = deployService.executarDeploy(aplicacaoCreateDTO);
        return deployed;
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
