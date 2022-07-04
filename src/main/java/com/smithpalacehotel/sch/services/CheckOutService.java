package com.smithpalacehotel.sch.services;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.smithpalacehotel.sch.models.CheckOut;
import com.smithpalacehotel.sch.repository.CheckOutRepository;
import com.smithpalacehotel.sch.services.exceptions.BusinessRuleException;
import com.smithpalacehotel.sch.services.exceptions.DataIntegrityException;
import com.smithpalacehotel.sch.services.exceptions.ObjectNotFoundException;

@Service
public class CheckOutService {
    @Autowired
    private CheckOutRepository checkOutRepository;

    public Collection<CheckOut> findAll() {
        return checkOutRepository.findAll();
    }

    public CheckOut findById(Integer id){
        CheckOut obj = checkOutRepository.findById(id).get();
        if (obj == null) {
            throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + CheckOut.class.getName());
        }
        return obj;
    }

    public CheckOut insert(CheckOut obj){
        try {
            if (verificarRegrasDeNegocio(obj)){
                obj.setId(null);
                return checkOutRepository.save(obj);
            }
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Campo(s) obrigatório(s) do CheckOut não foi(foram) preenchido(s): Reserva de quarto ou Reserva de evento");
        }
        return null;
    }

    public CheckOut update(CheckOut obj){
        findById(obj.getId());
        try{
            return checkOutRepository.save(obj);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Campo(s) obrigatório(s) do CheckOut não foi(foram) preenchido(s): Reserva de quarto ou Reserva de evento");
        }
    }

    public void delete(Integer id) {
        findById(id);
        try {
            checkOutRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir um CheckOut!");
        }
    }

    public boolean verificarRegrasDeNegocio(CheckOut obj){
        // Regra de Negocio 1: Verificar se esta dentro do período de reserva
        boolean conflito = checkOutRepository.findReservaQuartoHorarioByCheckIn(obj.getCheckin().getId(), obj.getDataCheckout());

        if (conflito){
            throw new BusinessRuleException("Conflito de horário na reserva!");
        }

        // Regra de Negocio 2: Verificar se tem mais de 5 checkouts
        boolean maisDeCinco = checkOutRepository.findQuantiadeCheckOutByCliente(obj.getReservaQuarto().getCliente().getId());
        
        if (!conflito){
            return true;
        }
        else{
            return false;
        }
    }
}
