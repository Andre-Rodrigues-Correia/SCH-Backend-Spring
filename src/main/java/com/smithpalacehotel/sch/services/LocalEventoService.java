package com.smithpalacehotel.sch.services;

import java.util.Collection;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.smithpalacehotel.sch.models.LocalEvento;
import com.smithpalacehotel.sch.repository.LocalEventoRepository;
import com.smithpalacehotel.sch.services.exceptions.DataIntegrityException;
import com.smithpalacehotel.sch.services.exceptions.ObjectNotFoundException;

@Service
public class LocalEventoService {

    @Autowired
    private LocalEventoRepository repository;

    public LocalEvento findById(Integer id) {
    	try {
        	LocalEvento obj = repository.findById(id).get();
        	return obj;
        } catch (NoSuchElementException e) {
        	throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + LocalEvento.class.getName());
        }
    }

    public LocalEvento insert(LocalEvento obj) {
        obj.setId(null);
        try {
        	return repository.save(obj);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Campo(s) obrigatório(s) do Local de Evento não foi(foram) preenchido(s): Nome, Capacidade");
        }
    }

    public LocalEvento update(LocalEvento obj) {
    	findById(obj.getId());
        try {
        	return repository.save(obj);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Campo(s) obrigatório(s) da Local de Evento não foi(foram) preenchido(s): Nome, Capacidade");
        }
    }

    public void delete(Integer id) {
        findById(id);
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir um Local de Evento associado a reservas!");
        }
    }

    public Collection<LocalEvento> findAll() {
        return repository.findAll();
    }

}