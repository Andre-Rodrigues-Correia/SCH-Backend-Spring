package com.smithpalacehotel.sch.controller;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.smithpalacehotel.sch.models.Cidade;
import com.smithpalacehotel.sch.models.Uf;
import com.smithpalacehotel.sch.services.CidadeService;
import com.smithpalacehotel.sch.services.exceptions.ConstraintException;

@RestController
@RequestMapping(value = "/cidades")
public class CidadeController {

    @Autowired
    private CidadeService service;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Collection<Cidade>> findAll() {
        Collection<Cidade> collection = service.findAll();
        return ResponseEntity.ok().body(collection);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Cidade> find(@PathVariable Integer id) {
        Cidade obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Cidade> insert(@Valid @RequestBody Cidade obj, BindingResult br) {
        if (br.hasErrors()) {
        	throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
		}
        obj = service.insert(obj);
        return ResponseEntity.ok().body(obj);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Cidade> update(@PathVariable Integer id, @Valid @RequestBody Cidade obj, BindingResult br) {
        if (br.hasErrors())
        	throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
        obj.setId(id);
        obj = service.update(obj);
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    // @RequestMapping(value="/findByUf/{idUf}", method=RequestMethod.GET)
	// public ResponseEntity<Collection<Cidade>> findByUf(@PathVariable Integer idUf) {
	// 	Uf obj = new Uf();
	// 	obj.setId(idUf);
	// 	Collection<Cidade> collection = service.findByUf(obj);
	// 	return ResponseEntity.ok().body(collection);
	// }

}