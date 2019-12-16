package com.rafaelbenz.sgsc.sgscapi.services;

import com.rafaelbenz.sgsc.sgscapi.model.ItemContrato;
import com.rafaelbenz.sgsc.sgscapi.model.PagamentoComBoleto;
import com.rafaelbenz.sgsc.sgscapi.model.Contrato;
import com.rafaelbenz.sgsc.sgscapi.model.enums.EstadoPagamento;
import com.rafaelbenz.sgsc.sgscapi.repositories.ItemContratoRepository;
import com.rafaelbenz.sgsc.sgscapi.repositories.PagamentoRepository;
import com.rafaelbenz.sgsc.sgscapi.repositories.ContratoRepository;
import com.rafaelbenz.sgsc.sgscapi.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ContratoService {

    @Autowired
    ContratoRepository contratoRepository;

    @Autowired
    PagamentoRepository pagamentoRepository;

    @Autowired
    ItemContratoRepository itemContratoRepository;

    @Autowired
    BoletoService boletoService;

    @Autowired
    ServicoService servicoService;

    public Contrato find(Integer id){
        return contratoRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Contrato.class.getSimpleName()));
    }

    public List<Contrato> findAll(){
        return contratoRepository.findAll();
    }

    public Contrato insert(Contrato pedido) {
        pedido.setId(null);
        pedido.setInstante(new Date());
        pedido.getPagamento().setEstado(EstadoPagamento.PENDENTE);
        pedido.getPagamento().setContrato(pedido);


        if(pedido.getPagamento() instanceof PagamentoComBoleto){
            PagamentoComBoleto pagamento = (PagamentoComBoleto) pedido.getPagamento();
            boletoService.preencherPagamentoComBoleto(pagamento,pedido.getInstante());
        }
        pedido = contratoRepository.save(pedido);
        pedido.setPagamento(pagamentoRepository.save(pedido.getPagamento()));
        for(ItemContrato item : pedido.getItens()){
            item.setDesconto(0.0);
            item.setPreco(item.getServico().getPreco());
            item.setContrato(pedido);
        }
        itemContratoRepository.saveAll(pedido.getItens());
        return pedido;
    }
}
