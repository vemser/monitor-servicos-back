package com.vemser.monitorservicosback.repository;

import com.vemser.monitorservicosback.entity.PropriedadesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PropriedadesRepository extends JpaRepository<PropriedadesEntity, String> {
    String CHAVE_URL_DEPLOY = "URL_DEPLOY";
    String CHAVE_URL_LOG = "URL_LOG";
    Optional<PropriedadesEntity> findByChave(String chave);
}
