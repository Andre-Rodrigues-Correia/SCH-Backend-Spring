package com.smithpalacehotel.sch.repository;

import org.springframework.stereotype.Repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import com.smithpalacehotel.sch.models.*;

@Repository
public interface CheckOutRepository extends JpaRepository<CheckOut, Integer>{
    
}