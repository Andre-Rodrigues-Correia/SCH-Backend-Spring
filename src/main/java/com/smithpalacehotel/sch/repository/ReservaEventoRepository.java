package com.smithpalacehotel.sch.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.smithpalacehotel.sch.models.*;

@Repository
public interface ReservaEventoRepository extends JpaRepository<ReservaEvento, Integer>{
    @Transactional(readOnly = true)
    @Query(value = "SELECT reserva_evento.* FROM reserva_evento INNER JOIN pessoa ON pessoa.id = reserva_evento.cliente_id WHERE pessoa.id = ?1 ORDER BY id DESC LIMIT 2;", nativeQuery = true)
    public Collection<ReservaEvento> findLastReservaEventoByCliente(Integer clienteId);

    @Transactional(readOnly = true)
    @Query(value = "SELECT COUNT(*) > local_evento.capacidade FROM reserva_evento, check_in, local_evento WHERE check_in.reservaevento_id = reserva_evento.id AND local_evento.id = reserva_evento.localevento_id AND reserva_evento.id = ?1;", nativeQuery = true)
    public Boolean findCapacidadeByReservaEvento(Integer reservaEventoId);
    
    @Transactional(readOnly = true)
    @Query(value = "SELECT reserva_evento.* FROM reserva_evento INNER JOIN local_evento ON reserva_evento.localevento_id = ?1 WHERE reserva_evento.data >= ?2 AND reserva_evento.data <= ?3 ORDER BY reserva_evento.data;", nativeQuery = true)
    public Collection<ReservaEvento> listReservaEventoByLocalEvento(Integer localEventoId, LocalDateTime inicial, LocalDateTime fim);
}
