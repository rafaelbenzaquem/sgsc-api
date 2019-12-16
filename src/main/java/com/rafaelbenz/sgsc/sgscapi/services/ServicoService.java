package com.rafaelbenz.sgsc.sgscapi.services;

import com.rafaelbenz.sgsc.sgscapi.model.Categoria;
import com.rafaelbenz.sgsc.sgscapi.model.Cliente;
import com.rafaelbenz.sgsc.sgscapi.model.Endereco;
import com.rafaelbenz.sgsc.sgscapi.model.Servico;
import com.rafaelbenz.sgsc.sgscapi.repositories.CategoriaRepository;
import com.rafaelbenz.sgsc.sgscapi.repositories.ServicoRepository;
import com.rafaelbenz.sgsc.sgscapi.services.exceptions.DataIntegretyServiceException;
import com.rafaelbenz.sgsc.sgscapi.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServicoService {

    @Autowired
    ServicoRepository servicoRepository;
    @Autowired
    CategoriaRepository categoriaRepository;

    @Transactional
    public Servico insert(Servico servico) {
        servico.setId(null);
        List<Categoria> categorias = servico.getCategorias();
        servico = servicoRepository.save(servico);


        for (Categoria c : categorias) {
            Categoria cc = categoriaRepository.findById(c.getId()).get();
            cc.getServicos().add(servico);
            categoriaRepository.save(cc);
        }


        return servico;
    }

    public Servico find(Integer id) {
        return servicoRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Servico.class.getSimpleName()));
    }

    public Page<Servico> search(String nome, List<Categoria> categorias, Integer page, Integer linesPerPage, String direction, String... properties) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), properties);
        if (categorias.isEmpty()) {
            System.out.println("Sem categoria");
            return servicoRepository.findDistinctByNomeContaining(nome, pageRequest);
        } else {
            System.out.println("Com categoria");
            return servicoRepository.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
        }
    }

    public List<Servico> findAll() {
        return servicoRepository.findAll();
    }


    public Servico update(Servico servico) {
        servico = servicoRepository.save(servico);
        categoriaRepository.saveAll(servico.getCategorias());
        return servico;
    }

    private void updateData(Servico servicoAtualizado, Servico servico) {
        servicoAtualizado.setNome(servico.getNome());
        servicoAtualizado.setPreco(servico.getPreco());
    }

    public Servico delete(Integer id) {
        Servico categoria = find(id);
        servicoRepository.delete(categoria);
        return categoria;
    }

}
