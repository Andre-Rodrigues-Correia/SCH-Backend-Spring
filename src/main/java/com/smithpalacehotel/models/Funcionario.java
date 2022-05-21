package com.smithpalacehotel.models;

import javax.persistence.*;
import lombok.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Funcionario extends Pessoa{
    @Builder
    public Funcionario(Integer id, String nome, String cpf, String login, String senha, Cidade cidade) {
        super(id, nome, cpf, login, senha, cidade);
        this.login = login;
        this.senha = senha;
    }
}
