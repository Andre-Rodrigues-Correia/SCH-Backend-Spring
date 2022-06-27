package com.smithpalacehotel.sch.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.smithpalacehotel.sch.models.*;

@Repository
public interface CheckOutRepository extends JpaRepository<CheckOut, Integer>{
    @Transactional(readOnly = true)
    @Query(value = "SELECT * FROM checkout INNER JOIN checkin ON checkout.checkin_id = checkin.id WHERE checkout.id = ?1;", nativeQuery = true)
    public Collection<CheckOut> findCheckOutByCheckin(Integer id);
    
    @Transactional(readOnly = true)
    @Query(value = "SELECT COUNT(checkout) > 4 FROM checkout INNER JOIN reservaquarto ON reservaquarto.id = checkout.reservaquarto_id INNER JOIN cliente ON cliente.id = reservaquarto.cliente_id WHERE cliente.id = ?1;", nativeQuery = true)
    public Boolean findQuantiadeCheckOutByCliente(Integer clienteId);
}
