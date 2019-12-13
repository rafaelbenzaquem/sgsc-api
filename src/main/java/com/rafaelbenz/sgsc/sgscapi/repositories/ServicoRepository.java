package com.rafaelbenz.sgsc.sgscapi.repositories;

import com.rafaelbenz.sgsc.sgscapi.model.Categoria;
import com.rafaelbenz.sgsc.sgscapi.model.Servico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Integer> {

    // https://docs.spring.io/spring-data/jpa/docs/current/reference/html/
    @Query("SELECT DISTINCT obj FROM Servico obj INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome% AND cat IN :categorias")
    public Page<Servico> search(@Param("nome") String nome, @Param("categorias") List<Categoria> categorias, Pageable pageRequest);

    public Page<Servico> findDistinctByNomeContainingAndCategoriasIn(String nome, List<Categoria> categorias, Pageable pageRequest);

    public Page<Servico> findDistinctByNomeContaining(String nome, Pageable pageRequest);

}
