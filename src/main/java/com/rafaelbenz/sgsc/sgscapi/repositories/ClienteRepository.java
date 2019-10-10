package com.rafaelbenz.sgsc.sgscapi.repositories;

import com.rafaelbenz.sgsc.sgscapi.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Integer> {
}
