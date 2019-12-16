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
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public Contrato insert(Contrato contrato) {
        contrato.setId(null);
        contrato.setInstante(new Date());
        contrato.getPagamento().setEstado(EstadoPagamento.PENDENTE);
        contrato.getPagamento().setContrato(contrato);


        if(contrato.getPagamento() instanceof PagamentoComBoleto){
            PagamentoComBoleto pagamento = (PagamentoComBoleto) contrato.getPagamento();
            boletoService.preencherPagamentoComBoleto(pagamento,contrato.getInstante());
        }
        contrato = contratoRepository.save(contrato);
        contrato.setPagamento(pagamentoRepository.save(contrato.getPagamento()));
        for(ItemContrato item : contrato.getItens()){
            item.setDesconto(0.0);
            item.setPreco(item.getServico().getPreco());
            item.setContrato(contrato);
        }
        itemContratoRepository.saveAll(contrato.getItens());
        return contrato;
    }
}
