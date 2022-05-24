package com.smithpalacehotel.sch.models;

import javax.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Cliente extends Pessoa{
    public Float valorTotal;

    @Builder
    public Cliente(Integer id, String nome, String cpf, String login, String senha, Cidade cidade, Float valorTotal) {
        super(id, nome, cpf, login, senha, cidade);
        this.valorTotal = valorTotal;
    }
}
