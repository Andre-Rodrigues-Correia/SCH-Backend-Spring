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

import com.smithpalacehotel.sch.models.CheckOut;
import com.smithpalacehotel.sch.services.CheckOutService;
import com.smithpalacehotel.sch.services.exceptions.ConstraintException;

@RestController
@RequestMapping(value = "/checkout")
public class CheckOutController {

    @Autowired
    private CheckOutService service;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Collection<CheckOut>> findAll() {
        Collection<CheckOut> collection = service.findAll();
        return ResponseEntity.ok().body(collection);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<CheckOut> find(@PathVariable Integer id) {
        CheckOut obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<CheckOut> insert(@Valid @RequestBody CheckOut obj, BindingResult br) {
        if (br.hasErrors())
        	throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
        obj = service.insert(obj);
        return ResponseEntity.ok().body(obj);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<CheckOut> update(@PathVariable Integer id, @Valid @RequestBody CheckOut obj, BindingResult br) {
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
    
    @RequestMapping(value = "/checkoutporreservaquarto", method = RequestMethod.GET)
    public ResponseEntity<Void> relatorio(@Valid @RequestBody LocalDateTime data_checkout, @Valid @RequestBody LocalDateTime data_fim) {
        service.relatorio(data_checkout, data_fim);
        return ResponseEntity.ok().build();
    }

}
