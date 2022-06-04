package com.smithpalacehotel.sch.services;

import java.util.Collection;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.smithpalacehotel.sch.models.Quarto;
import com.smithpalacehotel.sch.repository.QuartoRepository;
import com.smithpalacehotel.sch.services.exceptions.DataIntegrityException;
import com.smithpalacehotel.sch.services.exceptions.ObjectNotFoundException;

@Service
public class QuartoService {

    @Autowired
    private QuartoRepository repository;

    public Quarto findById(Integer id) {
    	try {
        	Quarto obj = repository.findById(id).get();
        	return obj;
        } catch (NoSuchElementException e) {
        	throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Quarto.class.getName());
        }
    }

    public Quarto insert(Quarto obj) {
        obj.setId(null);
        try {
        	return repository.save(obj);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Campo(s) obrigatório(s) do Quarto não foi(foram) preenchido(s): Número, Capacidade");
        }
    }

    public Quarto update(Quarto obj) {
    	findById(obj.getId());
        try {
        	return repository.save(obj);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Campo(s) obrigatório(s) da Quarto não foi(foram) preenchido(s): Número, Capacidade");
        }
    }

    public void delete(Integer id) {
        findById(id);
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir um Quarto associado a reservas!");
        }
    }

    public Collection<Quarto> findAll() {
        return repository.findAll();
    }
}
