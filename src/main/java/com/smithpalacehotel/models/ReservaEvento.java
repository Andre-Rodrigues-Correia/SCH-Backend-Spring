package com.smithpalacehotel.models;

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

    @NotBlank(message = "A data da reserva de evento deve ser preenchida.")
    public LocalDateTime data;

    @NotBlank(message = "O status da reserva de evento deve ser preenchido.")
    public Boolean status;

    @ManyToOne
    @JoinColumn(name = "localevento_id")
    public LocalEvento localEvento;
}
