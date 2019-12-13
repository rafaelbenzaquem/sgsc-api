package com.rafaelbenz.sgsc.sgscapi.repositories;

import com.rafaelbenz.sgsc.sgscapi.model.Categoria;
import com.rafaelbenz.sgsc.sgscapi.model.Servico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria,Integer> {

    public List<Categoria> findDistinctByServicosIn(List<Servico> servicos);

}
