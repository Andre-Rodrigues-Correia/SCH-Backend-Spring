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
public class Cidade implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @Column(length = 60)
    @NotBlank(message = "O nome da Cidade deve ser preenchido.")
    @Size(min = 2, max = 60, message = "O nome da Cidade deve ter entre 2 e 60 letras.")
    public String nome;
    
    @ManyToOne
    @JoinColumn(name = "uf_id")
    private Uf uf;
}
