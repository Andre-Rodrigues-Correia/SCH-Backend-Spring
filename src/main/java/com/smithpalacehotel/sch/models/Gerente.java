package com.smithpalacehotel.sch.models;

import javax.persistence.*;
import lombok.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Gerente extends Pessoa{
    @Builder
    public Gerente(Integer id, String nome, String cpf, String login, String senha, Cidade cidade) {
        super(id, nome, cpf, login, senha, cidade);
    }
}
