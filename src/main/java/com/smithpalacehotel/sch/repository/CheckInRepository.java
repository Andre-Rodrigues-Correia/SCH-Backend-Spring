package com.smithpalacehotel.sch.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.smithpalacehotel.sch.models.*;

@Repository
public interface CheckInRepository extends JpaRepository<CheckIn, Integer>{
    @Transactional(readOnly = true)
    @Query(value = "SELECT check_in.* FROM check_in INNER JOIN reserva_quarto ON check_in.reservaquarto_id = reserva_quarto.id WHERE check_in.id = ?1;", nativeQuery = true)
    public Collection<CheckIn> findCheckInByReservaQuarto(Integer id);

    @Transactional(readOnly = true)
    @Query(value = "SELECT check_in.* FROM check_in INNER JOIN reserva_evento ON check_in.reservaevento_id = reserva_evento.id WHERE check_in.id = ?1;", nativeQuery = true)
    public Collection<CheckIn> findCheckInByReservaEvento(Integer id);

    @Transactional(readOnly = true)
    @Query(value = "SELECT quarto.status FROM quarto INNER JOIN reserva_quarto ON reserva_quarto.quarto_id = quarto.id WHERE reserva_quarto.id = ?1;", nativeQuery = true)
    public Boolean findQuartoStatusByReservaQuarto(Integer reservaQuarto);

    @Transactional(readOnly = true)
    @Query(value = "SELECT local_evento.status FROM local_evento INNER JOIN reserva_evento ON reserva_evento.localevento_id = local_evento.id WHERE reserva_evento.id = ?1;", nativeQuery = true)
    public Boolean findEventoStatusByReservaEvento(Integer reservaEvento);

    @Transactional(readOnly = true)
    @Query(value = "SELECT check_in.* FROM check_in WHERE check_in.data_checkin >= ?1 AND check_in.data_checkin <= ?2 ORDER BY check_in.data_checkin DESC;", nativeQuery = true)
    public Collection<CheckIn> listCheckIn(LocalDateTime inicio, LocalDateTime fim);
}
