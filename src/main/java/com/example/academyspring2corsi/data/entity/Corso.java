package com.example.academyspring2corsi.data.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Getter
@Setter
public class Corso {

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nome;

    @Column(name = "anno_accademico")
    private Integer annoAccademico;

    


}
