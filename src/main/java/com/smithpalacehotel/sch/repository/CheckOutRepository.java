package com.smithpalacehotel.sch.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.smithpalacehotel.sch.models.*;

@Repository
public interface CheckOutRepository extends JpaRepository<CheckOut, Integer>{
    @Transactional(readOnly = true)
    @Query(value = "SELECT data_checkin >= data_inicial AND ?2 <= data_final FROM check_in INNER JOIN reserva_quarto ON reserva_quarto.id = check_in.reservaquarto_id WHERE check_in.id = ?1;", nativeQuery = true)
    public Boolean findReservaQuartoHorarioByCheckIn(Integer checkInId, LocalDateTime dataCheckOut); // Retorna true se houver conflito de horario

    @Transactional(readOnly = true)
    @Query(value = "SELECT data_checkin >= data_inicial AND ?2 <= data_final FROM check_in INNER JOIN reserva_evento ON reserva_evento.id = check_in.reservaevento_id WHERE check_in.id = ?1;", nativeQuery = true)
    public Boolean findReservaEventoHorarioByCheckIn(Integer checkInId, LocalDateTime dataCheckOut); // Retorna true se houver conflito de horario
    
    @Transactional(readOnly = true)
    @Query(value = "SELECT COUNT(*) > 4 FROM check_out INNER JOIN reserva_quarto ON reserva_quarto.id = check_out.reservaquarto_id INNER JOIN pessoa ON pessoa.id = reserva_quarto.cliente_id WHERE pessoa.id = ?1;", nativeQuery = true)
    public Boolean findQuantiadeCheckOutByCliente(Integer clienteId);
    
    @Transactional(readOnly = true)
    @Query(value = "SELECT check_out.* FROM check_out,reserva_quarto WHERE check_out.reservaquarto_id=reserva_quarto.id AND check_out.data_checkout<>reserva_quarto.data_final;", nativeQuery = true)
    public Collection<CheckOut> listCheckOutByReservaQuarto(DateTime data_checkout, DateTime data_fim);
}
