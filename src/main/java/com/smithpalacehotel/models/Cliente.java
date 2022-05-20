package com.smithpalacehotel.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Cliente extends Pessoa{
    @NotBlank(message = "O valor total gasto do cliente deve ser informado")
    public Float valorTotal;

    @Builder
    public Cliente(Integer id, String nome, String cpf, String login, String senha) {
        super(id, nome, cpf, login, senha);
        this.login = login;
        this.senha = senha;
    }
}
