package com.vemser.monitorservicosback.service;


import com.vemser.monitorservicosback.exceptions.RegraDeNegocioException;
import com.vemser.monitorservicosback.repository.PropriedadesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PropriedadesService {
    private final PropriedadesRepository propriedadesRepository;

    public String recuperarValorPorChave(String chave) throws RegraDeNegocioException {
        return propriedadesRepository.findByChave(chave)
                .orElseThrow(() -> new RegraDeNegocioException("Propriedade não encontrada!"))
                .getValor();
    }

    public String recuperarURLDeploy() {
        try {
            return recuperarValorPorChave(PropriedadesRepository.CHAVE_URL_DEPLOY);
        } catch (RegraDeNegocioException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String recuperarURLLog() {
        try {
            return recuperarValorPorChave(PropriedadesRepository.CHAVE_URL_LOG);
        } catch (RegraDeNegocioException e) {
            e.printStackTrace();
        }
        return null;
    }


}
