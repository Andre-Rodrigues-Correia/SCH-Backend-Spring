package com.smithpalacehotel.sch.controller;

import java.time.LocalDateTime;
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

import com.smithpalacehotel.sch.models.ReservaQuarto;
import com.smithpalacehotel.sch.services.ReservaQuartoService;
import com.smithpalacehotel.sch.services.ReservaVeiculoService;
import com.smithpalacehotel.sch.services.exceptions.ConstraintException;

@RestController
@RequestMapping(value = "/reservaquarto")
public class ReservaQuartoController {

    @Autowired
    private ReservaQuartoService service;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Collection<ReservaQuarto>> findAll() {
        Collection<ReservaQuarto> collection = service.findAll();
        return ResponseEntity.ok().body(collection);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ReservaQuarto> find(@PathVariable Integer id) {
        ReservaQuarto obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ReservaQuarto> insert(@Valid @RequestBody ReservaQuarto obj, BindingResult br) {
        if (br.hasErrors())
        	throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
        obj = service.insert(obj);
        return ResponseEntity.ok().body(obj);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ReservaQuarto> update(@PathVariable Integer id, @Valid @RequestBody ReservaQuarto obj, BindingResult br) {
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

    @RequestMapping(value = "/listarquartoporstatus/{inicio}/{fim}", method = RequestMethod.GET)
    public ResponseEntity<Collection<ReservaQuarto>> relatorio(@PathVariable String inicio, @PathVariable String fim) {
        Collection<ReservaQuarto> collection = service.listarQuartoByStatus(LocalDateTime.parse(inicio), LocalDateTime.parse(fim));
        return ResponseEntity.ok().body(collection);
    }

}
