package com.smithpalacehotel.models;

import javax.persistence.*;
import lombok.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Gerente extends Pessoa{
    @Builder
    public Gerente(Integer id, String nome, String cpf, String login, String senha) {
        super(id, nome, cpf, login, senha);
        this.login = login;
        this.senha = senha;
    }
}
