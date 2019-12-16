package com.rafaelbenz.sgsc.sgscapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class ItemContrato implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    @EmbeddedId
    private ItemContratoPk id = new ItemContratoPk();

    private Double desconto;
    private Integer quantidade;
    private Double preco;

    public ItemContrato() {
    }

    public ItemContrato(Contrato contrato, Servico servico, Double desconto, Integer quantidade, Double preco) {
        super();
        this.id.setContrato(contrato);
        this.id.setServico(servico);
        this.desconto = desconto;
        this.quantidade = quantidade;
        this.preco = preco;
    }

//    //melhor deixar para o cliente da api calcular e poupar recursos do servidor
    public BigDecimal getSubTotal(){
       return (new BigDecimal(preco).subtract(new BigDecimal(desconto))).multiply(new BigDecimal(quantidade));
    }

    @JsonIgnore
    public Contrato getContrato() {
        return id.getContrato();
    }

    public void setContrato(Contrato contrato) {
        id.setContrato(contrato);
    }

    public Servico getServico() {
        return id.getServico();
    }

    public void setServico(Servico servico) {
        id.setServico(servico);
    }

    public ItemContratoPk getId() {
        return id;
    }

    public void setId(ItemContratoPk id) {
        this.id = id;
    }

    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemContrato)) return false;
        ItemContrato that = (ItemContrato) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
