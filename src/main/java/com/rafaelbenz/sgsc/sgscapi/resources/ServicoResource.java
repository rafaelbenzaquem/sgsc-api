package com.rafaelbenz.sgsc.sgscapi.resources;

import com.rafaelbenz.sgsc.sgscapi.dto.ServicoDTO;
import com.rafaelbenz.sgsc.sgscapi.model.Categoria;
import com.rafaelbenz.sgsc.sgscapi.model.Cliente;
import com.rafaelbenz.sgsc.sgscapi.model.Servico;
import com.rafaelbenz.sgsc.sgscapi.services.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/servicos")
public class ServicoResource {

    @Autowired
    ServicoService service;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@Valid @RequestBody Servico servico) {

        servico= service.insert(servico);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}").buildAndExpand(servico.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Servico>> findAll() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Servico> find(@PathVariable Integer id) {
        Servico servico = service.find(id);
        return ResponseEntity.ok().body(servico);
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<Page<ServicoDTO>> findPage(
            @RequestParam(value = "nome", defaultValue = "") String nome,
            @RequestParam(value = "categorias", defaultValue = "") String categorias,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy
    ) {

        try {
            nome = URLDecoder.decode(nome, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Page<Servico> pages = service.search(nome, urlToCategorias(categorias), page, linesPerPage, direction, orderBy);
        Page<ServicoDTO> servicos = pages.map(p -> new ServicoDTO(p));
        return ResponseEntity.ok().body(servicos);
    }

    private List<Categoria> urlToCategorias(String urlResquestParameter) {
        List<Categoria> categorias = new ArrayList<>();
        if (!urlResquestParameter.isEmpty())
            Arrays.asList(urlResquestParameter.split(",")).forEach(id -> categorias.add(new Categoria(Integer.parseInt(id), null)));
        return categorias;
//        return Arrays.asList(urlResquestParameter.split(",")).stream().map(id -> new Categoria(Integer.parseInt(id),null)).collect(Collectors.toList());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@Valid @RequestBody Servico servico, @PathVariable Integer id) {
        servico.setId(id);
        service.update(servico);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
