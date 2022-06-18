package com.smithpalacehotel.sch.services;

import java.util.Collection;

import org.hibernate.annotations.Check;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.smithpalacehotel.sch.models.CheckIn;
import com.smithpalacehotel.sch.models.LocalEvento;
import com.smithpalacehotel.sch.models.Quarto;
import com.smithpalacehotel.sch.repository.CheckInRepository;
import com.smithpalacehotel.sch.repository.LocalEventoRepository;
import com.smithpalacehotel.sch.repository.QuartoRepository;
import com.smithpalacehotel.sch.services.exceptions.BusinessRuleException;
import com.smithpalacehotel.sch.services.exceptions.DataIntegrityException;
import com.smithpalacehotel.sch.services.exceptions.ObjectNotFoundException;

@Service
public class CheckInService {
    @Autowired
    private CheckInRepository checkInRepository;
    @Autowired
    private QuartoRepository quartoRepository;
    @Autowired
    private LocalEventoRepository localEventoRepository;

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

    public boolean verificarRegrasDeNegocio(CheckIn obj){
        // Regra de Negocio 1: Não deixar mais de um checkin para uma mesma reserva
        boolean checkInUnico = true;
        if (obj.getReservaEvento() == null){
            Collection<CheckIn> checkIns = checkInRepository.findByReservaQuarto(obj.getReservaQuarto());

            for (CheckIn checkIn : checkIns)
                if (checkIn != null) checkInUnico = false;
        }
        else if (obj.getReservaQuarto() == null){
            Collection<CheckIn> checkIns = checkInRepository.findByReservaEvento(obj.getReservaEvento());

            for (CheckIn checkIn : checkIns)
                if (checkIn != null) checkInUnico = false;
        }
        if (!checkInUnico){
            throw new BusinessRuleException("Não é possível atribuir mais de um CheckIn para uma mesma reserva!");
        }

        // Regra de Negocio 2: Checar o status do quarto
        boolean reservaPronta = false;
        if (obj.getReservaEvento() == null){
            Quarto quarto = quartoRepository.findById(obj.getReservaQuarto().getQuarto().getId()).get();
            reservaPronta = quarto.getStatus();
        }
        else if (obj.getReservaQuarto() == null){
            LocalEvento evento = localEventoRepository.findById(obj.getReservaEvento().getLocalEvento().getId()).get();
            reservaPronta = evento.getStatus();
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
