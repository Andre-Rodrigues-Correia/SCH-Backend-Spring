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
public class Veiculo implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @NotBlank(message = "A placa deve ser preenchida.")
    public String placa;

    public Boolean statusVeiculo;

    @NotBlank(message = "O nome do veículo não deve estar em branco.")
    public String nomeVeiculo;
    
    @Min(value = 0L, message = "A quantidade de vezes usado deve ser positiva.")
    public Integer quantidadeVezesUsado;
}
