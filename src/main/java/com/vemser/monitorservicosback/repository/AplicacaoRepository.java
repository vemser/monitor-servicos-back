package com.vemser.monitorservicosback.repository;

import com.vemser.monitorservicosback.entity.AplicacaoEntity;
import com.vemser.monitorservicosback.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AplicacaoRepository extends JpaRepository<AplicacaoEntity, Integer> {

    @Query("select max(a.porta) + 1 from APLICACAO a")
    Integer encontrarProximaPorta();

    Optional<AplicacaoEntity> findByGitUrl(String gitUrl);
}
