package com.example.academyspring2corsi.mapstruct;


import com.example.academyspring2corsi.data.dto.CorsoDTO;
import com.example.academyspring2corsi.data.entity.Corso;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CorsoMapper {

    CorsoDTO corsoToDto(Corso corso);

    Corso corsoToEntity(CorsoDTO corsoDTO);
}