package com.vemser.monitorservicosback.controller;

import com.vemser.monitorservicosback.dto.aplicacao.AplicacaoCreateDTO;
import com.vemser.monitorservicosback.dto.aplicacao.AplicacaoDTO;
import com.vemser.monitorservicosback.service.AplicacaoService;
import com.vemser.monitorservicosback.service.DeployService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/aplicacao")
@Validated
@RequiredArgsConstructor
public class AplicacaoController {

    private final AplicacaoService aplicacaoService;
    private final DeployService deployService;

    @PostMapping
    public AplicacaoDTO create(@RequestBody AplicacaoCreateDTO aplicacaoCreateDTO) {
        return aplicacaoService.create(aplicacaoCreateDTO);
    }

    @PostMapping("/deploy")
    public AplicacaoDTO executarDeployKub(@RequestBody AplicacaoCreateDTO aplicacaoCreateDTO) throws IOException, InterruptedException {
        return deployService.executarDeploy(aplicacaoCreateDTO);
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
