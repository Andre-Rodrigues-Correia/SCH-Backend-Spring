package com.smithpalacehotel.sch.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.validation.constraints.*;
import javax.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class CheckOut implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    public LocalDateTime dataCheckout;

    @ManyToOne
    @JoinColumn(name="checkin_id")
    public CheckIn checkin;

    @ManyToOne
    @JoinColumn(name = "reservaquarto_id")
    public ReservaQuarto reservaQuarto;
}
