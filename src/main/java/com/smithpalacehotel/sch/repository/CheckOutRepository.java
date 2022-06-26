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
    @Query(value = "SELECT COUNT(checkout) > checkout.id FROM checkout, checkout WHERE checkout.checkout_id = checkout.id AND checkout.id = ?1;", nativeQuery = true)
    public Boolean findQuantiadeByCheckOut(Integer checkoutId);
    
}
