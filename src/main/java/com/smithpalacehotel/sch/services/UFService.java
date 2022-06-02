package com.smithpalacehotel.sch.services;

import java.util.Collection;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.smithpalacehotel.sch.models.Uf;
import com.smithpalacehotel.sch.repository.UfRepository;
import com.smithpalacehotel.sch.services.exceptions.DataIntegrityException;
import com.smithpalacehotel.sch.services.exceptions.ObjectNotFoundException;

@Service
public class UFService {

    @Autowired
    private UfRepository repository;

    public Uf findById(Integer id) {
    	try {
        	Uf obj = repository.findById(id).get();
        	return obj;
        } catch (NoSuchElementException e) {
        	throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Uf.class.getName());
        }
    }

    public Collection<Uf> findAll() {
        return repository.findAll();
    }

    public Uf insert(Uf obj) {
    	obj.setId(null);
    	try {
        	return repository.save(obj);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Campo(s) obrigatório(s) da Uf não foi(foram) preenchido(s)");
        }
    }

    public Uf update(Uf obj) {
    	findById(obj.getId());
    	try {
        	return repository.save(obj);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Campo(s) obrigatório(s) da Uf não foi(foram) preenchido(s)");
        }
    }

    public void delete(Integer id) {
        findById(id);
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir uma Uf que possui cidades!");
        }
    }

}