package com.smithpalacehotel.sch.services;

import java.time.LocalDateTime;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.smithpalacehotel.sch.models.CheckIn;
import com.smithpalacehotel.sch.repository.CheckInRepository;
import com.smithpalacehotel.sch.services.exceptions.BusinessRuleException;
import com.smithpalacehotel.sch.services.exceptions.DataIntegrityException;
import com.smithpalacehotel.sch.services.exceptions.ObjectNotFoundException;

// Feito por Elian
@Service
public class CheckInService {
    @Autowired
    private CheckInRepository checkInRepository;

    public Collection<CheckIn> findAll() {
        return checkInRepository.findAll();
    }

    public CheckIn findById(Integer id){
        CheckIn obj = checkInRepository.findById(id).get();
        if (obj == null) {
            throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + CheckIn.class.getName());
        }
        return obj;
    }

    public CheckIn insert(CheckIn obj){
        try {
            if (verificarRegrasDeNegocio(obj)){
                obj.setId(null);
                return checkInRepository.save(obj);
            }
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Campo(s) obrigatório(s) do CheckIn não foi(foram) preenchido(s): Reserva de quarto ou Reserva de evento");
        }
        return null;
    }

    public CheckIn update(CheckIn obj){
        findById(obj.getId());
        try{
            return checkInRepository.save(obj);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Campo(s) obrigatório(s) do CheckIn não foi(foram) preenchido(s): Reserva de quarto ou Reserva de evento");
        }
    }

    public void delete(Integer id) {
        findById(id);
        try {
            checkInRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir um CheckIn!");
        }
    }

    public Collection<CheckIn> relatorio(LocalDateTime inicio, LocalDateTime fim) {
        return checkInRepository.listCheckIn(inicio, fim);
    }

    public boolean verificarRegrasDeNegocio(CheckIn obj){
        // Retorna erro caso não receba informações sobre as reservas
        if (obj.getReservaQuarto() == null && obj.getReservaEvento() == null)
            throw new ObjectNotFoundException("É necessário informar pelo menos uma reserva!");

        // Regra de Negocio 1: Não deixar mais de um checkin para uma mesma reserva
        boolean checkInUnico = true;
        if (obj.getReservaEvento() == null){
            Collection<CheckIn> checkIns = checkInRepository.findCheckInByReservaQuarto(obj.getReservaQuarto().getId());

            for (CheckIn checkIn : checkIns)
                if (checkIn != null) checkInUnico = false;
        }
        else if (obj.getReservaQuarto() == null){
            Collection<CheckIn> checkIns = checkInRepository.findCheckInByReservaEvento(obj.getReservaEvento().getId());

            for (CheckIn checkIn : checkIns)
                if (checkIn != null) checkInUnico = false;
        }
        if (!checkInUnico){
            throw new BusinessRuleException("Não é possível atribuir mais de um CheckIn para uma mesma reserva!");
        }

        // Regra de Negocio 2: Checar o status do quarto ou local de evento
        boolean reservaPronta = false;
        if (obj.getReservaEvento() == null && checkInRepository.findQuartoStatusByReservaQuarto(obj.getReservaQuarto().getId())){
            reservaPronta = true;
        }
        else if (obj.getReservaQuarto() == null && checkInRepository.findEventoStatusByReservaEvento(obj.getReservaEvento().getId())){
            reservaPronta = true;
        }

        if (!reservaPronta){
            throw new BusinessRuleException("O local de evento está em manutenção!");
        }

        if (reservaPronta && checkInUnico){
            return true;
        }
        else {
            return false;
        }
    }
}
