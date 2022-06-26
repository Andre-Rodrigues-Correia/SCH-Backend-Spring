package com.smithpalacehotel.sch.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.smithpalacehotel.sch.models.*;

@Repository
public interface ReservaVeiculoRepository extends JpaRepository<ReservaVeiculo, Integer>{ 
    @Transactional(readOnly = true)
    @Query(value = "SELECT * FROM reservaveiculo INNER JOIN veiculo ON reservaveiculo.veiculo_id = veiculo.id WHERE reservaveiculo.data = ?1;", nativeQuery = true)
    public Collection<ReservaVeiculo> findReservaVeiculoByData(LocalDateTime data);

    @Transactional(readOnly = true)
    @Query(value = "SELECT * FROM reservaveiculo INNER JOIN reservaquarto ON reservaveiculo.reservaquarto_id = reservaquarto.id INNER JOIN veiculo ON reservaveiculo.veiculo_id = veiculo.id WHERE veiculo.id = ?1;", nativeQuery = true)
    public Collection<ReservaVeiculo> findReservaVeiculoByVeiculo(Integer veiculoId);

}
