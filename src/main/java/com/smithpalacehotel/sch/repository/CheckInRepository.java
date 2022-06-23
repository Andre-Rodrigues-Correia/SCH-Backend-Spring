package com.smithpalacehotel.sch.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.smithpalacehotel.sch.models.*;

@Repository
public interface CheckInRepository extends JpaRepository<CheckIn, Integer>{
    @Transactional(readOnly = true)
    @Query(value = "SELECT * FROM checkin INNER JOIN reservaquarto ON checkin.reservaquarto_id = reservaquarto.id WHERE checkin.id = ?1;", nativeQuery = true)
    public Collection<CheckIn> findCheckInByReservaQuarto(Integer id);

    @Transactional(readOnly = true)
    @Query(value = "SELECT * FROM checkin INNER JOIN reservaevento ON checkin.reservaevento_id = reservaevento.id WHERE checkin.id = ?1;", nativeQuery = true)
    public Collection<CheckIn> findCheckInByReservaEvento(Integer id);

    @Transactional(readOnly = true)
    @Query(value = "SELECT quarto.status FROM quarto INNER JOIN reservaquarto ON reservaquarto.quarto_id = quarto.id WHERE reservaquarto.id = ?1;", nativeQuery = true)
    public Boolean findQuartoStatusByReservaQuarto(Integer reservaQuarto);

    @Transactional(readOnly = true)
    @Query(value = "SELECT localevento.status FROM localevento INNER JOIN reservaevento ON reservaevento.localevento_id = localevento.id WHERE reservaevento.id = ?1;", nativeQuery = true)
    public Boolean findEventoStatusByReservaEvento(Integer reservaEvento);
}