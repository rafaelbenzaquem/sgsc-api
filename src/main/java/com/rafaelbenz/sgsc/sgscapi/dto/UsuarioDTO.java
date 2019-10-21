package com.rafaelbenz.sgsc.sgscapi.dto;

import com.rafaelbenz.sgsc.sgscapi.model.Usuario;
import com.rafaelbenz.sgsc.sgscapi.model.enums.TipoUsuario;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

public class UsuarioDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String nome;
    private String login;
    private Integer tipo;

    public UsuarioDTO() {
    }

    public UsuarioDTO(Integer id, String nome, String login, TipoUsuario tipo) {
        this.id = id;
        this.nome = nome;
        this.login = login;
        this.tipo = (tipo == null) ? null : tipo.getCodigo();
    }

    public UsuarioDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.login = usuario.getLogin();
        this.tipo = usuario.getTipo();
    }

    public Usuario toUsuario() {
        return new Usuario(id, nome, login, null, TipoUsuario.toEnum(tipo));
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(TipoUsuario tipo) {
        this.tipo = (tipo == null) ? null : tipo.getCodigo();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioDTO usuario = (UsuarioDTO) o;
        return Objects.equals(id, usuario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", login='" + login + '\'' +
                '}';
    }
}
