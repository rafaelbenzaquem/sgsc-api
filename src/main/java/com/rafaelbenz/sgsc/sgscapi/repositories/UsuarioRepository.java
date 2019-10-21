package com.rafaelbenz.sgsc.sgscapi.repositories;

import com.rafaelbenz.sgsc.sgscapi.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {

    // https://docs.spring.io/spring-data/jpa/docs/current/reference/html/
//    @Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome% AND cat IN :categorias")
//    public Page<Produto> search(@Param("nome") String nome,@Param("categorias") List<Categoria> categorias, Pageable pageRequest);
//
    public Optional<Usuario> findByLoginAndSenha(String login, String senha);


}
