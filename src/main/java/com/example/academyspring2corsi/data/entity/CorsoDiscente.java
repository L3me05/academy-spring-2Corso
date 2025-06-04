package com.example.academyspring2corsi.data.entity;

import jakarta.persistence.*;

@Entity
public class CorsoDiscente {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_corso")
    private Long idCorso;

    @Column(name = "id_discente")
    private Long idDiscente;
}
