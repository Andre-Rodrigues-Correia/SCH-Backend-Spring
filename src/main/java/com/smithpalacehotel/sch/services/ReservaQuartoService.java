package com.smithpalacehotel.sch.services;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.smithpalacehotel.sch.models.ReservaQuarto;
import com.smithpalacehotel.sch.repository.ReservaQuartoRepository;
import com.smithpalacehotel.sch.services.exceptions.DataIntegrityException;
import com.smithpalacehotel.sch.services.exceptions.ObjectNotFoundException;

@Service
public class ReservaQuartoService {

    @Autowired
    private ReservaQuartoRepository reservaQuartoRepository;

    public Collection<ReservaQuarto> findAll() {
        return reservaQuartoRepository.findAll();
    }

    public ReservaQuarto findById(Integer id){
        ReservaQuarto obj = reservaQuartoRepository.findById(id).get();
        if (obj == null) {
            throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + ReservaQuarto.class.getName());
        }
        return obj;
    }

    public ReservaQuarto insert(ReservaQuarto obj){
        try {
            if (verificarRegrasDeNegocio(obj)){
                obj.setId(null);
                return reservaQuartoRepository.save(obj);
            }
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Campo(s) obrigatório(s) do ReservaQuarto não foi(foram) preenchido(s): Reserva de quarto ou Reserva de evento");
        }
        return null;
    }

    public ReservaQuarto update(ReservaQuarto obj){
        findById(obj.getId());
        try{
            return reservaQuartoRepository.save(obj);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Campo(s) obrigatório(s) do ReservaQuarto não foi(foram) preenchido(s): Reserva de quarto ou Reserva de evento");
        }
    }

    public void delete(Integer id) {
        findById(id);
        try {
            reservaQuartoRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir um ReservaQuarto!");
        }
    }

    public boolean verificarRegrasDeNegocio(ReservaQuarto obj){
        return true;
    }
}
