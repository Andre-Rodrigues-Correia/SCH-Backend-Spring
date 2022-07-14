package com.smithpalacehotel.sch.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.smithpalacehotel.sch.models.*;

@Repository
public interface ReservaQuartoRepository extends JpaRepository<ReservaQuarto, Integer>{
  @Transactional(readOnly = true)
  @Query(value = "SELECT reserva_quarto.* FROM reserva_quarto INNER JOIN quarto ON reserva_quarto.quarto_id = quarto.id WHERE ?1 >= reserva_quarto.data_inicial AND ?2 <= reserva_quarto.data_final;", nativeQuery = true)
    public Collection<ReservaQuarto> findReservaQuartoByData(LocalDateTime dataInicial, LocalDateTime dataFinal);
  
    @Transactional(readOnly = true)
    @Query(value = "SELECT pessoa.* FROM reserva_quarto INNER JOIN check_in ON check_in.reservaquarto_id = reserva_quarto.id INNER JOIN check_out ON check_out.checkin_id != check_in.id INNER JOIN pessoa ON pessoa.id = reserva_quarto.cliente_id WHERE reserva_quarto.id = ?1;", nativeQuery = true)
    public Collection<Cliente> findDevedoresByReservaQuarto(Integer reservaQuartoId);

    @Transactional(readOnly = true)
    @Query(value = "SELECT reserva_quarto.* FROM reserva_quarto INNER JOIN quarto ON quarto.id = reserva_quarto.id = quarto.id WHERE reserva_quarto.data_inicial <= ?1 AND reserva_quarto.data_final >= ?2 ORDER BY reserva_quarto.data_inicIal DESC;", nativeQuery = true)
    public Collection<ReservaQuarto> listQuartoDisponivel(LocalDateTime inicio, LocalDateTime fim);
}
