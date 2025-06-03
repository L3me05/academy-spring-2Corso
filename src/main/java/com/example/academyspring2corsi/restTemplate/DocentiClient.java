package com.example.academyspring2corsi.restTemplate;


import com.example.academyspring2corsi.data.dto.DocenteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class DocentiClient {
    @Autowired
    RestTemplate restTemplate;

    private final String URL_DOCENTI = "http://localhost:8080/docenti/";

    public DocenteDTO getDocente(Long id) {
        try {
            // Il formato corretto è "findById?id={id}" e non "findById?={id}"
            DocenteDTO docente = restTemplate.getForObject(URL_DOCENTI + "findById?id={id}", DocenteDTO.class, id);
            return docente;
        } catch (RestClientException e) {
            throw new RuntimeException("Errore durante ricerca docente: " + e.getMessage(), e);
        }
    }

    public boolean exists(Long id) {

    }

}