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
public class ReservaEvento implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    public LocalDateTime data;

    public Boolean status;

    @ManyToOne
    @JoinColumn(name = "localevento_id")
    public LocalEvento localEvento;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    public Cliente cliente;
}
