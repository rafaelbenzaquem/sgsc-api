package com.rafaelbenz.sgsc.sgscapi.resources;

import com.rafaelbenz.sgsc.sgscapi.dto.ClienteDTO;
import com.rafaelbenz.sgsc.sgscapi.dto.ClienteNewDTO;
import com.rafaelbenz.sgsc.sgscapi.model.Cliente;
import com.rafaelbenz.sgsc.sgscapi.services.ClienteService;
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
@RequestMapping(value = "/clientes")
public class ClienteResource {

    @Autowired
    ClienteService service;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@Valid @RequestBody Cliente cliente) {
        boolean teste = false;
        if (teste)
            throw new RuntimeException();
        Cliente Cliente = service.insert(cliente);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}").buildAndExpand(Cliente.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Cliente> find(@PathVariable Integer id) {
        Cliente cliente = service.find(id);
        return ResponseEntity.ok().body(cliente);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ClienteDTO>> findAll() {
        List<ClienteDTO> Clientes = new ArrayList<>();
        service.findAll().forEach(c -> Clientes.add(new ClienteDTO(c)));
        return ResponseEntity.ok().body(Clientes);
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ResponseEntity<Page<ClienteDTO>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy) {

        Page<Cliente> pages = service.findPage(page, linesPerPage, direction, orderBy);
        Page<ClienteDTO> Clientes = pages.map(p -> new ClienteDTO(p));
        return ResponseEntity.ok().body(Clientes);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@Valid @RequestBody Cliente cliente, @PathVariable Integer id) {
        System.out.println("está vindo pra ca --------------");
        cliente.setId(id);
        service.update(cliente);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
