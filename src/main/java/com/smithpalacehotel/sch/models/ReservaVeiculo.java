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
public class ReservaVeiculo implements Serializable{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Integer id;

  public LocalDateTime data;

  public Boolean status;

  @ManyToOne
  @JoinColumn(name = "veiculo_id")
  public Veiculo veiculo;

  @ManyToOne
  @JoinColumn(name = "reservaquarto_id")
  public ReservaQuarto reservaQuarto;
}
