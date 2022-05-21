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
public class Quarto implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @NotBlank(message = "A numero deve ser preenchida.")
    public String numeroQuarto;

    @NotBlank(message = "O status deve ser preenchido.")
    public Boolean statusQuarto;
    
    @NotBlank(message = "A quantidade de pessoas deve ser preenchida.")
    public Integer capacidadePessoas;
}
