package com.rafaelbenz.sgsc.sgscapi.services;

import com.rafaelbenz.sgsc.sgscapi.model.Categoria;
import com.rafaelbenz.sgsc.sgscapi.repositories.CategoriaRepository;
import com.rafaelbenz.sgsc.sgscapi.services.exceptions.DataIntegretyServiceException;
import com.rafaelbenz.sgsc.sgscapi.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    CategoriaRepository repository;

    public Categoria find(Integer id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getSimpleName()));
    }

    public List<Categoria> findAll() {
        return repository.findAll();
    }

    public Page<Categoria> findPage(Integer page, Integer linesPerPage, String direction, String... properties) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), properties);
        return repository.findAll(pageRequest);
    }

    public Categoria insert(Categoria categoria) {
        return repository.save(categoria);
    }

    public Categoria update(Categoria categoria) {
        Categoria categoriaAtualizada = find(categoria.getId());
        updateData(categoriaAtualizada, categoria);
        return repository.save(categoriaAtualizada);
    }

    private void updateData(Categoria categoriaAtualizada, Categoria categoria) {
        categoriaAtualizada.setNome(categoria.getNome());
    }

    public Categoria delete(Integer id) {
        Categoria categoria = find(id);
        try {
            repository.delete(categoria);
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegretyServiceException("Não é possível remover uma categoria que possui serviços.");
        }
        return categoria;
    }
}
