package com.example.academyspring2corsi.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscenteDTO {


    private String nome;
    private String cognome;
    private Integer matricola;
    private Integer eta;
    private String cittaResidenza;



}
