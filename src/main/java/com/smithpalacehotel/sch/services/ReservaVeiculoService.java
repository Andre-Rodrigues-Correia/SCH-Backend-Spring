package com.smithpalacehotel.sch.services;

import java.util.Collection;

import org.hibernate.annotations.Check;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.smithpalacehotel.sch.models.ReservaVeiculo;
import com.smithpalacehotel.sch.models.LocalEvento;
import com.smithpalacehotel.sch.models.Quarto;
import com.smithpalacehotel.sch.repository.ReservaVeiculoRepository;
import com.smithpalacehotel.sch.repository.LocalEventoRepository;
import com.smithpalacehotel.sch.repository.QuartoRepository;
import com.smithpalacehotel.sch.services.exceptions.BusinessRuleException;
import com.smithpalacehotel.sch.services.exceptions.DataIntegrityException;
import com.smithpalacehotel.sch.services.exceptions.ObjectNotFoundException;

// Feito por André
@Service
public class ReservaVeiculoService {

    @Autowired
    private ReservaVeiculoRepository reservaVeiculoRepository;

    public Collection<ReservaVeiculo> findAll() {
        return reservaVeiculoRepository.findAll();
    }

    public ReservaVeiculo findById(Integer id){
        ReservaVeiculo obj = reservaVeiculoRepository.findById(id).get();
        if (obj == null) {
            throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + ReservaVeiculo.class.getName());
        }
        return obj;
    }

    public ReservaVeiculo insert(ReservaVeiculo obj){
        try {
            if (verificarRegrasDeNegocio(obj)){
                obj.setId(null);
                return reservaVeiculoRepository.save(obj);
            }
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Campo(s) obrigatório(s) do ReservaVeiculo não foi(foram) preenchido(s): Reserva de quarto ou Reserva de evento");
        }
        return null;
    }

    public ReservaVeiculo update(ReservaVeiculo obj){
        findById(obj.getId());
        try{
            return reservaVeiculoRepository.save(obj);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Campo(s) obrigatório(s) do ReservaVeiculo não foi(foram) preenchido(s): Reserva de quarto ou Reserva de evento");
        }
    }

    public void delete(Integer id) {
        findById(id);
        try {
            reservaVeiculoRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir um ReservaVeiculo!");
        }
    }

    public boolean verificarRegrasDeNegocio(ReservaVeiculo obj){
      
        // Verificar se não existe o conflito de data e reserva de veículo (Listar todas as Reservas no FrontEnd)
        boolean conflitoDataReserva = false;

        Collection<ReservaVeiculo> reservas = reservaVeiculoRepository.findReservaVeiculoByData(obj.getData());

        for(ReservaVeiculo reserva : reservas)
            if(reserva != null) conflitoDataReserva = true;
        
        if (conflitoDataReserva){
            throw new BusinessRuleException("Esse veícula já foi reservado para essa data");
        }

        //  Verificar se foram atingidas a quantidade máxima de locações por mês.
        boolean capacidadeMaxima = false;
        int num = 0;

        reservas = reservaVeiculoRepository.findReservaVeiculoByVeiculo(obj.getVeiculo().getId());
        for(ReservaVeiculo reserva : reservas){
            if(obj.getData().getMonthValue() == reserva.getData().getMonthValue() && obj.getData().getYear() == reserva.getData().getYear()) num++;
        }

        if(num >= 14) capacidadeMaxima = true;

        if (capacidadeMaxima){
            throw new BusinessRuleException("A capacidade máxima de locação do veículo foi atingida nesse mês");
        }

        if (!capacidadeMaxima && !conflitoDataReserva){
            return true;
        }
        else {
            return false;
        }
    }
}
