package com.smithpalacehotel.sch.models;

import java.io.Serializable;
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

  public LocalDateTime dataInicial;

  public LocalDateTime dataFinal;

  public Boolean status;

  @ManyToOne
  @JoinColumn(name = "quarto_id")
  public Quarto quarto;

  @ManyToOne
  @JoinColumn(name = "cliente_id")
  public Cliente cliente;
}
