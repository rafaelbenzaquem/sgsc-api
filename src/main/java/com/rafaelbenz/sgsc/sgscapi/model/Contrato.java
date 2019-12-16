package com.rafaelbenz.sgsc.sgscapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

@Entity
public class Contrato implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Date instante;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "contrato")
    private Pagamento pagamento;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @OneToMany(mappedBy = "id.contrato")
    private Set<ItemContrato> itens = new HashSet<>();

    public Contrato() {
    }

    public Contrato(Integer id, Date instante, Pagamento pagamento, Cliente cliente) {
        this.id = id;
        this.instante = instante;
        this.pagamento = pagamento;
        this.cliente = cliente;
    }

    @JsonIgnore
    public List<Servico> getServicos() {
        List<Servico> servicos = new ArrayList<>();
        for (ItemContrato item : itens)
            servicos.add(item.getServico());
        return servicos;
    }

    //melhor deixar para o cliente da api calcular e poupar recursos do servidor
//    public BigDecimal getValorTotal() {
//        BigDecimal soma = new BigDecimal(0.0);
//        for (ItemContrato itemPedido : itens) {
//            soma = soma.add(itemPedido.getSubTotal());
//        }
//        return soma;
//    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getInstante() {
        return instante;
    }

    public void setInstante(Date instante) {
        this.instante = instante;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Set<ItemContrato> getItens() {
        return itens;
    }

    public void setItens(Set<ItemContrato> itens) {
        this.itens = itens;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contrato)) return false;
        Contrato pedido = (Contrato) o;
        return Objects.equals(getId(), pedido.getId());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId());
    }
}
