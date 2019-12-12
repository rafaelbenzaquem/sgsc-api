package com.rafaelbenz.sgsc.sgscapi.resources;

import com.rafaelbenz.sgsc.sgscapi.dto.LoginDTO;
import com.rafaelbenz.sgsc.sgscapi.dto.UsuarioDTO;
import com.rafaelbenz.sgsc.sgscapi.model.Usuario;
import com.rafaelbenz.sgsc.sgscapi.services.UsuarioService;
import com.rafaelbenz.sgsc.sgscapi.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioResource {

    @Autowired
    UsuarioService service;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@Valid @RequestBody Usuario usuario) {

        Usuario novoUsuario = service.insert(usuario);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}").buildAndExpand(novoUsuario.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Usuario> find(@PathVariable Integer id) {
        Usuario usuario = service.find(id);
        return ResponseEntity.ok().body(usuario);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<UsuarioDTO> login(@Valid @RequestBody LoginDTO dto) {
        try {
            Usuario usuario = service.findByLoginAndSenha(dto.getLogin(), dto.getSenha());
            return ResponseEntity.ok().body(new UsuarioDTO(usuario));
        } catch (ObjectNotFoundException ex) {
            ex.printStackTrace();
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<UsuarioDTO>> findAll() {
        List<UsuarioDTO> usuarios = new ArrayList<>();
        service.findAll().forEach(u -> usuarios.add(new UsuarioDTO(u)));
        return ResponseEntity.ok().body(usuarios);
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ResponseEntity<Page<UsuarioDTO>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy) {

        Page<Usuario> pages = service.findPage(page, linesPerPage, direction, orderBy);
        Page<UsuarioDTO> usuarioDTOS = pages.map(p -> new UsuarioDTO(p));
        return ResponseEntity.ok().body(usuarioDTOS);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@Valid @RequestBody Usuario usuario, @PathVariable Integer id) {
        usuario.setId(id);
        service.update(usuario);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
