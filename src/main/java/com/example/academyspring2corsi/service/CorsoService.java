package com.example.academyspring2corsi.service;


import com.example.academyspring2corsi.data.dto.CorsoDTO;
import com.example.academyspring2corsi.data.dto.DocenteDTO;
import com.example.academyspring2corsi.data.entity.Corso;
import com.example.academyspring2corsi.mapstruct.CorsoMapper;
import com.example.academyspring2corsi.repository.CorsoRepository;
import com.example.academyspring2corsi.restTemplate.DocentiClient;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

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
    DocentiClient docentiClient;


    private final String URL_DOCENTE = "http://localhost:8080/docenti/";



    //metodi per crud
    public List<CorsoDTO> findAll() {
        return corsoRepository.findAll().stream()
                .map(corsoMapper::corsoToDto)
                .peek(corsoDTO -> corsoDTO.setDocente(docentiClient.getDocente(corsoDTO.getIdDocente())))
                .collect(Collectors.toList());
    }



    public CorsoDTO get(Long id) {
        Corso corso =corsoRepository.findById(id).orElseThrow();
        return corsoMapper.corsoToDto(corso);
    }



    public CorsoDTO save(CorsoDTO c){
        if(docentiClient.exists(c.getIdDocente())) {
            Corso corso = corsoMapper.corsoToEntity(c);
            Corso savedCorso = corsoRepository.save(corso);
            return corsoMapper.corsoToDto(savedCorso);
        }
        else throw new ServiceException("Docente non trovato");
    }



    public CorsoDTO update(Long id, CorsoDTO corsoDTO) {
        Corso corso=corsoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Corso non trovato"));
        if(corsoDTO.getIdDocente()==null || docentiClient.exists(corsoDTO.getIdDocente())) {
            if(corsoDTO.getNome()!=null) corso.setNome(corsoDTO.getNome());
            if (corsoDTO.getAnnoAccademico()!=null) corso.setAnnoAccademico(corsoDTO.getAnnoAccademico());
            if (corsoDTO.getIdDocente()!=null) corso.setIdDocente(corsoDTO.getIdDocente());
            Corso savedCorso = corsoRepository.save(corso);
            return corsoMapper.corsoToDto(savedCorso);
        }
        else throw new ServiceException("Docente non trovato");

    }


    public void delete(Long id) {
        corsoRepository.deleteById(id);
    }






//    public List<DocenteDTO> listaProva() {
//        try{
//            ResponseEntity<List<DocenteDTO>> docenti = restTemplate.exchange(
//                    URL_DOCENTE+"list",
//                    HttpMethod.GET,
//                    null,
//                    new ParameterizedTypeReference<List<DocenteDTO>>(){}
//            );
//            if(docenti.getStatusCode() == HttpStatus.OK && docenti.getBody() != null){
//                return docenti.getBody();
//            }else{
//                return Collections.emptyList();
//            }
//        } catch (RestClientException e) {
//            throw new ServiceException("Errore durante il recupero della lista docenti", e);
//
//        }
//
//    }
}