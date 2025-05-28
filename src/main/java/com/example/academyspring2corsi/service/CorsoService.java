package com.example.academyspring2corsi.service;


import com.example.academyspring2corsi.data.dto.CorsoDTO;
import com.example.academyspring2corsi.data.entity.Corso;
import com.example.academyspring2corsi.mapstruct.CorsoMapper;
import com.example.academyspring2corsi.repository.CorsoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CorsoService {

    @Autowired
    CorsoRepository corsoRepository;
    @Autowired
    CorsoMapper corsoMapper;






    //metodi per crud
    public List<CorsoDTO> findAll() {
        return corsoRepository.findAll().stream()
                .map(corsoMapper::corsoToDto)
                .collect(Collectors.toList());
    }


    public CorsoDTO get(Long id) {
        Corso corso =corsoRepository.findById(id).orElseThrow();
        return corsoMapper.corsoToDto(corso);
    }


    @EntityGraph(attributePaths = {"docente", "discente"})
    public CorsoDTO save(CorsoDTO c){
        Corso corso = corsoMapper.corsoToEntity(c);
        Corso savedCorso = corsoRepository.save(corso);
        return corsoMapper.corsoToDto(savedCorso);
    }

    public CorsoDTO update(Long id, CorsoDTO corsoDTO) {
        Corso corso=corsoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Corso non trovato"));

        if(corsoDTO.getNome()!=null) corso.setNome(corsoDTO.getNome());
        if (corsoDTO.getAnnoAccademico()!=null) corso.setAnnoAccademico(corsoDTO.getAnnoAccademico());
        Corso savedCorso = corsoRepository.save(corso);
        return corsoMapper.corsoToDto(savedCorso);
    }


    public void delete(Long id) {
        corsoRepository.deleteById(id);
    }


}