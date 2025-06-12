package com.example.academyspring2corsi.mapstruct;


import com.example.academyspring2corsi.data.dto.CorsoDTO;
import com.example.academyspring2corsi.data.entityCorso.Corso;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CorsoMapper {

    CorsoDTO corsoToDto(Corso corso);


    Corso corsoToEntity(CorsoDTO corsoDTO);
}