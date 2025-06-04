package com.example.academyspring2corsi.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor          //genera costruttore senza argomenti
@AllArgsConstructor         //genera costruttore  con tutti i campi degli argomenti
@Data                       //genera getters and setters ha bisogno di un argument constructor
public class DocenteDTO {


    private String nome;
    private String cognome;
    private String dataNascita;


}
