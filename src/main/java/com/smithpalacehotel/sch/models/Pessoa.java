package com.smithpalacehotel.sch.models;

import java.io.Serializable;
import javax.validation.constraints.*;
import javax.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public abstract class Pessoa implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @Column(length = 100)
    @NotBlank(message = "Nome deve ser preenchido")
    @Size(min = 5, max = 100, message = "O nome deve ter entre 5 e 100 letras.")
    public String nome;

    @Column(length = 11)
    @NotBlank(message = "Cpf deve ser preenchido")
    @Size(min = 11, max = 11, message = "O Cpf deve ter 11 letras.")
    public String cpf;

    @Column(length = 50)
    @NotBlank(message = "Nome deve ser preenchido")
    @Size(min = 5, max = 50, message = "O nome deve ter entre 5 e 100 letras.")
    public String login;

    @Column(length = 30)
    @NotBlank(message = "A senha deve ser preenchida")
    @Size(min = 8, max = 30, message = "A senha deve ter entre 8 e 30 caracteres.")
    public String senha;

    @ManyToOne
    @JoinColumn(name = "cidade_id")
    public Cidade cidade;
}
