package com.rafaelbenz.sgsc.sgscapi.repositories;

import com.rafaelbenz.sgsc.sgscapi.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria,Integer> {
}
