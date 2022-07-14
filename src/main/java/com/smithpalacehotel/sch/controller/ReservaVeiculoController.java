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

import com.smithpalacehotel.sch.models.ReservaVeiculo;
import com.smithpalacehotel.sch.services.ReservaVeiculoService;
import com.smithpalacehotel.sch.services.exceptions.ConstraintException;

@RestController
@RequestMapping(value = "/reservaveiculo")
public class ReservaVeiculoController {

    @Autowired
    private ReservaVeiculoService service;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Collection<ReservaVeiculo>> findAll() {
        Collection<ReservaVeiculo> collection = service.findAll();
        return ResponseEntity.ok().body(collection);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ReservaVeiculo> find(@PathVariable Integer id) {
        ReservaVeiculo obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ReservaVeiculo> insert(@Valid @RequestBody ReservaVeiculo obj, BindingResult br) {
        if (br.hasErrors())
        	throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
        obj = service.insert(obj);
        return ResponseEntity.ok().body(obj);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ReservaVeiculo> update(@PathVariable Integer id, @Valid @RequestBody ReservaVeiculo obj, BindingResult br) {
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
    
    @RequestMapping(value = "/reservaveiculoquantidadevezesusado/{placa}/{quantidade}", method = RequestMethod.GET)
    public ResponseEntity<Collection<ReservaVeiculo>> relatorio(@PathVariable String placa, @PathVariable Integer quantidade) {
        Collection<ReservaVeiculo> collection = service.relatorio(placa, quantidade);
        return ResponseEntity.ok().body(collection);
    }

}
