package com.smithpalacehotel.sch.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.smithpalacehotel.sch.models.*;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Integer>{ 
}