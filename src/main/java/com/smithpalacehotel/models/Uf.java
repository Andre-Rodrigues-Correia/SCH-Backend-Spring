package com.smithpalacehotel.models;

import java.io.Serializable;
import javax.validation.constraints.*;
import javax.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Uf implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @Column(length = 2)
    @NotBlank(message = "Sigla UF deve ser preenchida")
    @Size(min = 2, max = 2, message = "Sigla UF deve ter 2 letras.")
    public String sigla;

    @Column(length = 60)
    @NotBlank(message = "O nome da UF deve ser preenchido.")
    @Size(min = 2, max = 60, message = "O nome da UF deve ter entre 2 e 60 letras.")
    public String nome;
}
