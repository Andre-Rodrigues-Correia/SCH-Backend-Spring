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
public class ReservaVeiculo implements Serializable{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Integer id;

  @NotBlank(message = "A data deve ser preenchida.")
  public LocalDateTime data;

  @NotBlank(message = "O status deve ser preenchido.")
  public Boolean status;

  @ManyToOne
  @JoinColumn(name = "veiculo_id")
  public Veiculo veiculo;
}
