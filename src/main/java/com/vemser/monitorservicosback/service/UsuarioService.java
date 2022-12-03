package com.vemser.monitorservicosback.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.monitorservicosback.dto.login.LoginCreateDTO;
import com.vemser.monitorservicosback.dto.login.LoginDTO;
import com.vemser.monitorservicosback.entity.UsuarioEntity;
import com.vemser.monitorservicosback.exceptions.RegraDeNegocioException;
import com.vemser.monitorservicosback.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;

    public Optional<UsuarioEntity> findById(Integer idUsuario) {
        return usuarioRepository.findById(idUsuario);
    }

    public LoginDTO create(LoginCreateDTO loginDTO) throws RegraDeNegocioException {
        UsuarioEntity usuarioEntity = objectMapper.convertValue(loginDTO, UsuarioEntity.class);
        try {
            usuarioEntity.setSenha(new BCryptPasswordEncoder().encode(loginDTO.getSenha()));
            UsuarioEntity save = usuarioRepository.save(usuarioEntity);
            return objectMapper.convertValue(save, LoginDTO.class);
        } catch (Exception ex) {
            throw new RegraDeNegocioException("Login já existente");
        }
    }

    public Optional<UsuarioEntity> findByEmail(String login) {
        return usuarioRepository.findByEmail(login);
    }
}
