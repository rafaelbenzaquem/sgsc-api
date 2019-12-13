package com.rafaelbenz.sgsc.sgscapi.resources;

import com.rafaelbenz.sgsc.sgscapi.dto.CategoriaDTO;
import com.rafaelbenz.sgsc.sgscapi.model.Categoria;
import com.rafaelbenz.sgsc.sgscapi.model.Servico;
import com.rafaelbenz.sgsc.sgscapi.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

    @Autowired
    CategoriaService categoriaService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO objDTO) {
        Categoria categoria = categoriaService.insert(objDTO.toCategoria());
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}").buildAndExpand(categoria.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Categoria> find(@PathVariable Integer id) {
        Categoria categoria = categoriaService.find(id);
        return ResponseEntity.ok().body(categoria);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<CategoriaDTO>> findAll() {
        List<CategoriaDTO> categorias = new ArrayList<>();
        categoriaService.findAll().forEach(c -> categorias.add(new CategoriaDTO(c)));
        return ResponseEntity.ok().body(categorias);
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ResponseEntity<Page<CategoriaDTO>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy
    ) {
        Page<Categoria> pages = categoriaService.findPage(page, linesPerPage, direction, orderBy);
        Page<CategoriaDTO> categorias = pages.map(p -> new CategoriaDTO(p));
        return ResponseEntity.ok().body(categorias);
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<List<CategoriaDTO>> findByServices(
            @RequestParam(value = "services", defaultValue = "") String services
    ) {
        List<Categoria> categorias = categoriaService.findByServicos(urlToCategorias(services));
        List<CategoriaDTO> categoriasDTO = categorias.stream().map(p -> new CategoriaDTO(p)).collect(Collectors.toList());
        return ResponseEntity.ok().body(categoriasDTO);
    }

    private List<Servico> urlToCategorias(String urlResquestParameter) {
        List<Servico> servicos = new ArrayList<>();
        if (!urlResquestParameter.isEmpty())
            Arrays.asList(urlResquestParameter.split(",")).forEach(id -> servicos.add(new Servico(Integer.parseInt(id))));
        return servicos;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@Valid @RequestBody CategoriaDTO objDto, @PathVariable Integer id) {
        objDto.setId(id);
        categoriaService.update(objDto.toCategoria());
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
