package com.example.academyspring2corsi.data.entityCorso;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class CorsoDiscente {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_corso")
    private Long idCorso;

    @Column(name = "id_discente")
    private Long idDiscente;
}
