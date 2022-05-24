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
public class CheckIn implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    public LocalDateTime dataCheckin;

    @ManyToOne
    @JoinColumn(name = "reservaevento_id")
    public ReservaEvento reservaEvento;

    @ManyToOne
    @JoinColumn(name = "reservaquarto_id")
    public ReservaQuarto reservaQuarto;
}
