package com.rafaelbenz.sgsc.sgscapi.model;

import com.rafaelbenz.sgsc.sgscapi.model.enums.TipoUsuario;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    @Column(unique = true)
    private String login;
    private String senha;
    private Integer tipo;

    public Usuario() {
    }

    public Usuario(Integer id, String nome, String login, String senha, TipoUsuario tipo) {
        this.id = id;
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.tipo = (tipo == null) ? null : tipo.getCodigo();
    }

    public Usuario(String nome, String login, String senha, TipoUsuario tipo) {
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.tipo = (tipo == null) ? null : tipo.getCodigo();
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
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
        Usuario usuario = (Usuario) o;
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
                ", senha='" + senha + '\'' +
                '}';
    }
}
