package com.smithpalacehotel.sch.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.smithpalacehotel.sch.models.*;

@Repository
public interface ReservaEventoRepository extends JpaRepository<ReservaEvento, Integer>{
    @Transactional(readOnly = true)
    @Query(value = "SELECT * FROM reservaevento INNER JOIN cliente ON cliente.id = reservaevento.cliente_id WHERE client.id = ?1 ORDER BY id DESC LIMIT 2;", nativeQuery = true)
    public Collection<ReservaEvento> findLastReservaEventoByCliente(Integer clienteId);

    @Transactional(readOnly = true)
    @Query(value = "SELECT COUNT(checkin) > reservaevento.capacidade FROM reservaevento, checkin WHERE checkin.reservaevento_id = reservaevento.id AND reservaevento.id = ?1;", nativeQuery = true)
    public Boolean findCapacidadeByReservaEvento(Integer reservaEventoId);
}
