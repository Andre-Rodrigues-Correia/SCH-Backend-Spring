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
public class Veiculo implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @NotBlank(message = "A placa deve ser preenchida.")
    public String placa;

    @NotBlank(message = "O status deve ser preenchido.")
    public Boolean statusVeiculo;
    
    @NotBlank(message = "A quantidade de vezes usado deve ser preenchida.")
    public Integer quntidadeVezesUsado;
}
