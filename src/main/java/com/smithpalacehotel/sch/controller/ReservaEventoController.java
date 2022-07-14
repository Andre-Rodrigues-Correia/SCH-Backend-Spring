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

import com.smithpalacehotel.sch.models.ReservaEvento;
import com.smithpalacehotel.sch.services.ReservaEventoService;
import com.smithpalacehotel.sch.services.exceptions.ConstraintException;

@RestController
@RequestMapping(value = "/reservaevento")
public class ReservaEventoController {

    @Autowired
    private ReservaEventoService service;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Collection<ReservaEvento>> findAll() {
        Collection<ReservaEvento> collection = service.findAll();
        return ResponseEntity.ok().body(collection);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ReservaEvento> find(@PathVariable Integer id) {
        ReservaEvento obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ReservaEvento> insert(@Valid @RequestBody ReservaEvento obj, BindingResult br) {
        if (br.hasErrors())
        	throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
        obj = service.insert(obj);
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ReservaEvento> update(@PathVariable Integer id, @Valid @RequestBody ReservaEvento obj, BindingResult br) {
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

    @RequestMapping(value = "/reservaeventoporlocal/{localevento}/{inicial}/{fim}", method = RequestMethod.GET)
    public ResponseEntity<Collection<ReservaEvento>> relatorio(@Valid @PathVariable Integer localevento, @Valid @PathVariable String inicial, @Valid @PathVariable String fim) {
        Collection<ReservaEvento> collection = service.relatorio(localevento, LocalDateTime.parse(inicial), LocalDateTime.parse(fim));
        return ResponseEntity.ok().body(collection);
    }
}
