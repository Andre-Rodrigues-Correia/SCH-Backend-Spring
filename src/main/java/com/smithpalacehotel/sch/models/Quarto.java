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
public class Quarto implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @NotBlank(message = "O número deve ser preenchido.")
    public String numeroQuarto;

    public Boolean status;
    
    public Integer capacidadePessoas;
}
