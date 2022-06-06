package com.smithpalacehotel.sch.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.smithpalacehotel.sch.models.*;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer>{
    Funcionario findByLoginAndSenha(String login, String senha); 
}