package com.example.academyspring2corsi.data.entityCorso;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    @Column(name = "id_docente")
    private Long idDocente;

    


}
