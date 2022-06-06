package com.smithpalacehotel.sch.services;

import java.util.Collection;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.smithpalacehotel.sch.models.Veiculo;
import com.smithpalacehotel.sch.repository.VeiculoRepository;
import com.smithpalacehotel.sch.services.exceptions.DataIntegrityException;
import com.smithpalacehotel.sch.services.exceptions.ObjectNotFoundException;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository repository;

    public Veiculo findById(Integer id) {
    	try {
        	Veiculo obj = repository.findById(id).get();
        	return obj;
        } catch (NoSuchElementException e) {
        	throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Veiculo.class.getName());
        }
    }

    public Veiculo insert(Veiculo obj) {
        obj.setId(null);
        try {
        	return repository.save(obj);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Campo(s) obrigatório(s) do Veículo não foi(foram) preenchido(s): Placa");
        }
    }

    public Veiculo update(Veiculo obj) {
    	findById(obj.getId());
        try {
        	return repository.save(obj);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Campo(s) obrigatório(s) do Veiculo não foi(foram) preenchido(s): placa");
        }
    }

    public void delete(Integer id) {
        findById(id);
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir um Veiculo associado a reservas!");
        }
    }

    public Collection<Veiculo> findAll() {
        return repository.findAll();
    }

}
