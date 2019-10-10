package com.rafaelbenz.sgsc.sgscapi.dto;

import com.rafaelbenz.sgsc.sgscapi.model.Cliente;
import com.rafaelbenz.sgsc.sgscapi.model.Endereco;
import com.rafaelbenz.sgsc.sgscapi.model.enums.TipoCliente;
import com.rafaelbenz.sgsc.sgscapi.services.validation.TipoNovoCliente;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Arrays;

@TipoNovoCliente
public class ClienteNewDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "preenchimento obrigatório")
    @Length(min = 5, max = 120, message = "O tamanho deve ser entre 5 e 120 caracteres")
    private String nome;
    @NotEmpty(message = "preenchimento obrigatório")
    @Email(message = "email inválido")
    private String email;

    @NotEmpty(message = "preenchimento obrigatório")
    private String cpfOuCnpj;

    private Integer tipo;

    private String logradouro;

    private String numero;

    private String complemento;

    private String bairro;

    private String cidade;


    @NotEmpty(message = "preenchimento obrigatório")
    private String cep;

    @NotEmpty(message = "preenchimento obrigatório")
    private String telefone1;
    private String telefone2;
    private String telefone3;



    public ClienteNewDTO() {
    }



    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpfOuCnpj() {
        return cpfOuCnpj;
    }

    public void setCpfOuCnpj(String cpfOuCnpj) {
        this.cpfOuCnpj = cpfOuCnpj;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getTelefone1() {
        return telefone1;
    }

    public void setTelefone1(String telefone1) {
        this.telefone1 = telefone1;
    }

    public String getTelefone2() {
        return telefone2;
    }

    public void setTelefone2(String telefone2) {
        this.telefone2 = telefone2;
    }

    public String getTelefone3() {
        return telefone3;
    }

    public void setTelefone3(String telefone3) {
        this.telefone3 = telefone3;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public Cliente toCliente() {
        Cliente novo = new Cliente(null, nome, email, cpfOuCnpj, TipoCliente.toEnum(tipo));
        Endereco endereco = new Endereco(null, logradouro, numero, complemento, bairro, cep, novo, cidade);
        novo.getEnderecos().add(endereco);
        novo.getTelefones().addAll(Arrays.asList(telefone1, telefone2, telefone3));
        return novo;
    }
}
