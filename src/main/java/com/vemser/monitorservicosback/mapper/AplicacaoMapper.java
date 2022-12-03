package com.vemser.monitorservicosback.mapper;

import com.vemser.monitorservicosback.dto.aplicacao.AplicacaoCreateDTO;
import com.vemser.monitorservicosback.dto.aplicacao.AplicacaoDTO;
import com.vemser.monitorservicosback.entity.AplicacaoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AplicacaoMapper {
    AplicacaoDTO toDto(AplicacaoEntity aplicacaoEntity);

    AplicacaoEntity createToEnity(AplicacaoCreateDTO aplicacaoCreateDTO);

    AplicacaoEntity toEnity(AplicacaoDTO aplicacaoDTO);
}
