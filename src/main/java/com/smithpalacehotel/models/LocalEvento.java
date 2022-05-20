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
public class LocalEvento implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
  
    @Column(length = 100)
    @NotBlank(message = "O nome do local deve ser preenchido")
    @Size(min = 5, max = 100, message = "O nome do local deve ter entre 5 e 100 letras.")
    public String local;

    @NotBlank(message = "O status do local deve ser informado")
    public Boolean statusEvento;
  
    @Min(value = 10L, message = "A capacidade total do local deve ser maior do que dez")
    @NotBlank(message = "A capacidade total do local deve ser informada")
    public Integer capacidade;
}
