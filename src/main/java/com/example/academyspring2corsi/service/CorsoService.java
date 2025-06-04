package com.example.academyspring2corsi.service;


import com.example.academyspring2corsi.data.dto.CorsoDTO;
import com.example.academyspring2corsi.data.dto.DiscenteDTO;
import com.example.academyspring2corsi.data.dto.DocenteDTO;
import com.example.academyspring2corsi.data.entity.Corso;
import com.example.academyspring2corsi.mapstruct.CorsoMapper;
import com.example.academyspring2corsi.repository.CorsoDiscenteRepository;
import com.example.academyspring2corsi.repository.CorsoRepository;
import com.example.academyspring2corsi.webClient.DiscenteClient;
import com.example.academyspring2corsi.webClient.DocenteClient;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Transient;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

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
    @Autowired
    DocenteClient docenteClient;
    @Autowired
    CorsoDiscenteRepository corsoDiscenteRepository;
    @Autowired
    DiscenteClient discenteClient;





    //metodi per crud
    public List<CorsoDTO> findAll() {
        return corsoRepository.findAll().stream()
                .map(corsoMapper::corsoToDto)
                .peek(corsoDTO -> corsoDTO.setDocente(docenteClient.docenteById(corsoDTO.getIdDocente())))
                .peek(corsoDTO -> {
                    Long idCorso = corsoRepository.findIdByNomeAndAnnoAccademico(corsoDTO.getNome(),corsoDTO.getAnnoAccademico());
                    List<Long> idsDiscenti = corsoDiscenteRepository.findIdsDiscenteByIdCorso((idCorso));
                    List<DiscenteDTO> discenti = new ArrayList<>();
                    idsDiscenti.stream()
                            .map(id -> discenti.add(discenteClient.discenteById(id)))
                            .toList();
                    corsoDTO.setDiscenti(discenti);
                })
                .collect(Collectors.toList());
    }


    public CorsoDTO get(Long id) {
        Corso corso =corsoRepository.findById(id).orElseThrow();
        return corsoMapper.corsoToDto(corso);
    }


    public CorsoDTO save(CorsoDTO c){
        if(c.getIdDocente()==null || docenteClient.existsById(c.getIdDocente())) {
            Corso corso = corsoMapper.corsoToEntity(c);
            Corso savedCorso = corsoRepository.save(corso);
            return corsoMapper.corsoToDto(savedCorso);
        }
        else throw new EntityNotFoundException("Docente non trovato");
    }

    public CorsoDTO update(Long id, CorsoDTO corsoDTO) {
        Corso corso=corsoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Corso non trovato"));
        if(corsoDTO.getIdDocente()==null || docenteClient.existsById(corsoDTO.getIdDocente())) {
            if(corsoDTO.getNome()!=null) corso.setNome(corsoDTO.getNome());
            if (corsoDTO.getAnnoAccademico()!=null) corso.setAnnoAccademico(corsoDTO.getAnnoAccademico());
            if(corsoDTO.getIdDocente()!=null) corso.setIdDocente(corsoDTO.getIdDocente());
            Corso savedCorso = corsoRepository.save(corso);
            return corsoMapper.corsoToDto(savedCorso);
        }
        else throw new EntityNotFoundException("Docente non trovato");
    }


    public void delete(Long id) {
        corsoRepository.deleteById(id);
    }

}