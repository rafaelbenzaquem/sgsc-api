package com.rafaelbenz.sgsc.sgscapi.services;

import com.rafaelbenz.sgsc.sgscapi.model.Categoria;
import com.rafaelbenz.sgsc.sgscapi.model.Servico;
import com.rafaelbenz.sgsc.sgscapi.repositories.ServicoRepository;
import com.rafaelbenz.sgsc.sgscapi.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicoService {

    @Autowired
    ServicoRepository servicoRepository;

    public Servico find(Integer id){
        return servicoRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Servico.class.getSimpleName()));
    }

    public Page<Servico> search(String nome, List<Categoria> categorias, Integer page, Integer linesPerPage, String direction, String... properties) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), properties);
        return servicoRepository.findDistinctByNomeContainingAndCategoriasIn(nome,categorias,pageRequest);
    }

    public List<Servico> findAll(){
        return servicoRepository.findAll();
    }

}
