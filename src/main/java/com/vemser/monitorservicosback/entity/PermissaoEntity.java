package com.vemser.monitorservicosback.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "PERMISSAO")
public class PermissaoEntity implements GrantedAuthority {

    @Id
    @Column(name = "ID_PERMISSAO")
    private int idPermissao;

    @Column(name = "DESCRICAO")
    private String descricao;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "USUARIO_PERMISSAO",
            joinColumns = @JoinColumn(name = "ID_PERMISSAO"),
            inverseJoinColumns = @JoinColumn(name = "ID_USUARIO")
    )
    private Set<UsuarioEntity> usuarios;

    @Override
    public String getAuthority() {
        return descricao;
    }
}
