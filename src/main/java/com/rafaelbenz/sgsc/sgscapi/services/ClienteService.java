package com.rafaelbenz.sgsc.sgscapi.services;

import com.rafaelbenz.sgsc.sgscapi.model.Cliente;
import com.rafaelbenz.sgsc.sgscapi.repositories.ClienteRepository;
import com.rafaelbenz.sgsc.sgscapi.repositories.EnderecoRepository;
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
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    EnderecoRepository enderecoRepository;

    @Transactional
    public Cliente insert(Cliente cliente) {
        cliente.setId(null);
        cliente =  clienteRepository.save(cliente);
        enderecoRepository.saveAll(cliente.getEnderecos());
        return cliente;
    }

    public Cliente find(Integer id) {
        return clienteRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getSimpleName()));
    }

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String direction, String... properties) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), properties);
        return clienteRepository.findAll(pageRequest);
    }

    public Cliente update(Cliente cliente) {
        Cliente clienteAtualizado = find(cliente.getId());
        updateData(clienteAtualizado, cliente);
        return clienteRepository.save(clienteAtualizado);
    }

    private void updateData(Cliente clienteAtualizado, Cliente cliente) {
        clienteAtualizado.setNome(cliente.getNome());
        clienteAtualizado.setEmail(cliente.getEmail());
    }

    public Cliente delete(Integer id) {
        Cliente cliente = find(id);
        try {
            clienteRepository.delete(cliente);
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegretyServiceException("Não é possível remover um Cliente que possui vinculos.");
        }
        return cliente;
    }

}
