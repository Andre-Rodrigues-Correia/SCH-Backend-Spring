package com.smithpalacehotel.sch.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.smithpalacehotel.sch.models.*;

@Repository
public interface ReservaQuartoRepository extends JpaRepository<ReservaQuarto, Integer>{
  @Transactional(readOnly = true)
  @Query(value = "SELECT * FROM reservaquarto INNER JOIN quarto ON reservaquarto.quarto_id = quarto.id WHERE reservaquarto.data = ?1;", nativeQuery = true)
    public Collection<ReservaQuarto> findReservaQuartoByData(LocalDateTime data);
}
