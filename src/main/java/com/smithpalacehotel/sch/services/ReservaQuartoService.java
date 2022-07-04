package com.smithpalacehotel.sch.services;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.smithpalacehotel.sch.models.*;
import com.smithpalacehotel.sch.repository.ReservaQuartoRepository;
import com.smithpalacehotel.sch.services.exceptions.BusinessRuleException;
import com.smithpalacehotel.sch.services.exceptions.DataIntegrityException;
import com.smithpalacehotel.sch.services.exceptions.ObjectNotFoundException;

import ch.qos.logback.core.net.server.Client;

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
        // Regra de Negocio 1: Verificar se não existe conflito
        boolean conflito = false;
        Collection<ReservaQuarto> reservaQuartos = reservaQuartoRepository.findReservaQuartoByData(obj.getDataInicial(), obj.getDataFinal());

        for (ReservaQuarto reservaQuarto : reservaQuartos)
            if (reservaQuarto != null) conflito = true;
        
        if (!conflito){
            throw new BusinessRuleException("A reserva possui conflito de horário!");
        }

        // Regra de Negocio 2: Verificar se possui dividas ativas
        boolean dividas = false;
        Collection<Cliente> clientes = reservaQuartoRepository.findDevedoresByReservaQuarto(obj.getId());

        for (Cliente cliente : clientes)
            if (cliente != null) dividas = true;

        if (!dividas){
            throw new BusinessRuleException("O cliente possui dívidas com o Hotel!");
        }

        if (!conflito && !dividas){
            return false;
        }
        else{
            return true;
        }
    }
}
