package com.rafaelbenz.sgsc.sgscapi.services;

import com.rafaelbenz.sgsc.sgscapi.model.Cliente;
import com.rafaelbenz.sgsc.sgscapi.model.Usuario;
import com.rafaelbenz.sgsc.sgscapi.repositories.UsuarioRepository;
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
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;


    @Transactional
    public Usuario insert(Usuario usuario) {
        usuario.setId(null);
        usuarioRepository.save(usuario);
        return usuarioRepository.save(usuario);
    }

    public Usuario find(Integer id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getSimpleName()));
    }

    public Usuario findByLoginAndSenha(String login, String senha) throws ObjectNotFoundException {
        return usuarioRepository.findByLoginAndSenha(login, senha).orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Login: " + login + ", Tipo: " + Cliente.class.getSimpleName()));
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Page<Usuario> findPage(Integer page, Integer linesPerPage, String direction, String... properties) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), properties);
        return usuarioRepository.findAll(pageRequest);
    }

    public Usuario update(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }


    public Usuario delete(Integer id) {
        Usuario usuario = find(id);
        try {
            usuarioRepository.delete(usuario);
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegretyServiceException("Não é possível remover um Cliente que possui vinculos.");
        }
        return usuario;
    }

}
