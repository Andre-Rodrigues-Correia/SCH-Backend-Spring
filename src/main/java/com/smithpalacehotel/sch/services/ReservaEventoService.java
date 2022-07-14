package com.smithpalacehotel.sch.services;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.time.Month;
import java.time.Year;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.smithpalacehotel.sch.models.ReservaEvento;
import com.smithpalacehotel.sch.repository.ReservaEventoRepository;
import com.smithpalacehotel.sch.services.exceptions.BusinessRuleException;
import com.smithpalacehotel.sch.services.exceptions.DataIntegrityException;
import com.smithpalacehotel.sch.services.exceptions.ObjectNotFoundException;

// Feito por William
@Service
public class ReservaEventoService {

    @Autowired
    private ReservaEventoRepository repository;

    public ReservaEvento findById(Integer id) {
    	try {
        	ReservaEvento obj = repository.findById(id).get();
        	return obj;
        } catch (NoSuchElementException e) {
        	throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + ReservaEvento.class.getName());
        }
    }

    public ReservaEvento insert(ReservaEvento obj) {
        obj.setId(null);
        try {
        	return repository.save(obj);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Campo(s) obrigatório(s) do Local de Evento não foi(foram) preenchido(s): Nome, Capacidade");
        }
    }

    public ReservaEvento update(ReservaEvento obj) {
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

    public Collection<ReservaEvento> findAll() {
        return repository.findAll();
    }
    
    public Collection<ReservaEvento> relatorio(Integer localEventoId, LocalDateTime inicial, LocalDateTime fim) {
        return repository.listReservaEventoByLocalEvento(localEventoId, inicial, fim);
    }

    public boolean verificarRegrasDeNegocio(ReservaEvento obj){
        // Regra de Negócio 1: Impedir o aluguel de um local de evento mais de 3 vezes seguidas em um mesmo mês por uma mesma pessoa.
        boolean aluguelInvalido = false;
        
        Collection<ReservaEvento> reservas = repository.findLastReservaEventoByCliente(obj.getCliente().getId());

        int ano = obj.getData().getYear();
        int mes = obj.getData().getMonthValue();
        int num = 0;

        for (ReservaEvento reserva : reservas)
            if (reserva.getData().getMonthValue() == mes && reserva.getData().getYear() == ano) num++;

        if (num >= 2) aluguelInvalido = true;

        // Regra de Negocio 2: Verificar se a quantidade máxima de pessoas foi atingida no local de evento.
        boolean capacidadeMaxima = false;

        if (repository.findCapacidadeByReservaEvento(obj.getId())){
            capacidadeMaxima = true;
        }

        if (!capacidadeMaxima){
            throw new BusinessRuleException("Capacidade máxima do local excedida!");
        }

        if (capacidadeMaxima && aluguelInvalido){
            return true;
        }
        else {
            return false;
        }
    }

}
