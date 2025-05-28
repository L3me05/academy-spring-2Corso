package com.example.academyspring2corsi.data.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CorsoDTO {

    private String nome;
    private Integer annoAccademico;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getAnnoAccademico() {
        return annoAccademico;
    }

    public void setAnnoAccademico(Integer annoAccademico) {
        this.annoAccademico = annoAccademico;
    }
}
