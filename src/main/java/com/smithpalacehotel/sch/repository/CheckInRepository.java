package com.smithpalacehotel.sch.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import com.smithpalacehotel.sch.models.*;

@Repository
public interface CheckInRepository extends JpaRepository<CheckIn, Integer>{
    @Transactional(readOnly = true)
    public Collection<CheckIn> findByReservaEvento(ReservaEvento obj);

    @Transactional(readOnly = true)
    public Collection<CheckIn> findByReservaQuarto(ReservaQuarto obj);
}