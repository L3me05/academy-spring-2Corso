package com.example.academyspring2corsi.data.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CorsoDTO {

    private String nome;
    private Integer annoAccademico;
    private DocenteDTO docente;
    private List<DiscenteDTO> discenti;

}
