package com.smithpalacehotel.sch.services;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.smithpalacehotel.sch.models.CheckOut;
import com.smithpalacehotel.sch.repository.CheckOutRepository;
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
        // Retorna erro caso não receba informações sobre as reservas
        if (obj.getReservaQuarto() == null && obj.getReservaEvento() == null)
            throw new ObjectNotFoundException("É necessário informar pelo menos uma reserva!");
        
         // Regra de Negocio 1: Verificar se o CheckOut foi realizado dentro do prazo da reserva
        
        boolean prazoReservaEvento = true;
        boolean prazoReservaQuarto = true;
        
       if(checkOutRepository.findReservaEventoHorarioByCheckIn(obj.getId())){
            prazoReservaEvento = false;
       }else;
        
       if(checkOutRepository.findReservaQuartoHorarioByCheckIn(obj.getId())){
            prazoReservaQuarto = false;
       }else;
         
        
        if (!prazoReservaQuarto){
            throw new BusinessRuleException("Reserva do quarto passou do prazo!");
        }else;
        if (!prazoReservaEvento){
           throw new BusinessRuleException("Reserva do Local do evento passou do prazo!");
        }else;
        
        // Regra de Negocio 2: Verificar se houveram mais de quatro CheckOut's para fornecer desconto
        
         boolean desconto = false;
        
         Collection<CheckOut> checkOuts = checkOutRepository.findQuantiadeCheckOutByCliente(obj.getId());

        for (CheckOut checkOut : checkOuts)
            if (checkOut >= 4) desconto = true;
        
          if (!desconto){
            throw new BusinessRuleException("Desconto disponível para o Cliente!");
        }    
        
        if (prazoReservaEvento && prazoReservaQuarto && desconto){
            return true;
        }
        else {
            return false;
        }
    }
}
