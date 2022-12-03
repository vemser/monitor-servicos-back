package com.vemser.monitorservicosback.controller;


import com.vemser.monitorservicosback.dto.login.LoginCreateDTO;
import com.vemser.monitorservicosback.dto.login.LoginDTO;
import com.vemser.monitorservicosback.entity.UsuarioEntity;
import com.vemser.monitorservicosback.exceptions.RegraDeNegocioException;
import com.vemser.monitorservicosback.security.TokenService;
import com.vemser.monitorservicosback.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
@Validated
@RequiredArgsConstructor
public class AuthController {
    private final TokenService tokenService;
    private final UsuarioService usuarioService;
    public final AuthenticationManager authenticationManager;

    @PostMapping
    public String auth(LoginCreateDTO loginDTO) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getEmail(),
                        loginDTO.getSenha()
                );

        Authentication authentication =
                authenticationManager.authenticate(
                        usernamePasswordAuthenticationToken);

        UsuarioEntity usuarioValidado = (UsuarioEntity) authentication.getPrincipal();

        return tokenService.generateToken(usuarioValidado);
    }

    @PostMapping("/create")
    public LoginDTO create(LoginCreateDTO newUsuario) throws RegraDeNegocioException {
        return usuarioService.create(newUsuario);
    }
}
