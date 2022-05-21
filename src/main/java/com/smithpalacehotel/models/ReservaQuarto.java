package com.smithpalacehotel.models;

import java.io.Serializable;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class ReservaQuarto implements Serializable{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Integer id;

  @NotBlank(message = "A data deve ser preenchida.")
  public LocalDateTime dataInicial;

  @NotBlank(message = "A data deve ser preenchida.")
  public LocalDateTime dataFinal;

  @NotBlank(message = "O status deve ser preenchido.")
  public Boolean status;

  @ManyToOne
  @JoinColumn(name = "quarto_id")
  public Quarto quarto;

  @ManyToOne
  @JoinColumn(name = "cliente_id")
  public Cliente cliente;
}
