package com.vemser.monitorservicosback.security;

import com.vemser.monitorservicosback.entity.UsuarioEntity;
import com.vemser.monitorservicosback.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {
    private final UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UsuarioEntity> usuarioEntityOptional = usuarioService.findByEmail(username);
        return usuarioEntityOptional
                .orElseThrow(() -> new UsernameNotFoundException("Usuario inválido"));
    }
}
