package com.vemser.monitorservicosback.service;


import com.vemser.monitorservicosback.dto.aplicacao.AplicacaoCreateDTO;
import com.vemser.monitorservicosback.dto.aplicacao.AplicacaoDTO;
import com.vemser.monitorservicosback.entity.AplicacaoEntity;
import com.vemser.monitorservicosback.mapper.AplicacaoMapper;
import com.vemser.monitorservicosback.repository.AplicacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AplicacaoService {
    private final AplicacaoRepository aplicacaoRepository;
    private final PropriedadesService propriedadesService;
    private final AplicacaoMapper aplicacaoMapper;

    public Optional<AplicacaoEntity> findById(Integer idAplicacao) {
        return aplicacaoRepository.findById(idAplicacao);
    }

    public AplicacaoDTO create(AplicacaoCreateDTO createDTO) {
        String urlLog = propriedadesService.recuperarURLLog();
        String urlDeploy = propriedadesService.recuperarURLDeploy();
        Optional<AplicacaoEntity> byGitUrl = aplicacaoRepository.findByGitUrl(createDTO.getGitUrl());
        if (byGitUrl.isPresent()) {
            return getDto(byGitUrl.get(), urlLog, urlDeploy);
        }

        AplicacaoEntity appEntity = aplicacaoMapper.createToEnity(createDTO);
        String[] partes = createDTO.getGitUrl().split("/");
        String usuario = partes[partes.length - 2].toLowerCase();
        String repositorio = partes[partes.length - 1].split("\\.", 2)[0].toLowerCase();
        Integer port = aplicacaoRepository.encontrarProximaPorta();
        appEntity.setCaminhoApp(usuario + "/" + repositorio);
        appEntity.setImagemDocker((usuario + "-" + repositorio).replace("_", "-"));
        appEntity.setPorta(port);
        AplicacaoEntity save = aplicacaoRepository.save(appEntity);

        return getDto(save, urlLog, urlDeploy);
    }

    public AplicacaoDTO update(AplicacaoDTO aplicacaoDTO) {
        AplicacaoEntity usuarioEntity = aplicacaoMapper.toEnity(aplicacaoDTO);
        AplicacaoEntity save = aplicacaoRepository.save(usuarioEntity);
        return aplicacaoMapper.toDto(save);
    }

    public void delete(Integer id) {
        aplicacaoRepository.deleteById(id);
    }

    public List<AplicacaoDTO> getAll() {
        String urlLog = propriedadesService.recuperarURLLog();
        String urlDeploy = propriedadesService.recuperarURLDeploy();
        return aplicacaoRepository.findAll()
                .stream().map(aplicacaoEntity -> getDto(aplicacaoEntity, urlLog, urlDeploy))
                .toList();
    }

    private AplicacaoDTO getDto(AplicacaoEntity save, String urlLog, String urlDeploy) {
        AplicacaoDTO aplicacaoDTO = aplicacaoMapper.toDto(save);
        aplicacaoDTO.setLog(urlLog + aplicacaoDTO.getCaminhoApp());
        aplicacaoDTO.setUrl(urlDeploy + aplicacaoDTO.getCaminhoApp());
        return aplicacaoDTO;
    }
}
