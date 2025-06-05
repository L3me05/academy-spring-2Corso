package com.example.academyspring2corsi.webClient;

import com.example.academyspring2corsi.data.dto.DiscenteDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class DiscenteClient {

    private final WebClient webClient;

    public DiscenteClient() {
        this.webClient = WebClient.create("http://localhost:8080/discenti");
    }

    public DiscenteDTO discenteById(Long id) {
        return webClient.get()
                .uri("/findById?id={id}", id)
                .retrieve()
                .bodyToMono(DiscenteDTO.class)
                .block();
    }

    public Long idDiscenteByNomeAndCognome(String nome, String cognome) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/findByNomeAndCognome")
                        .queryParam("nome", nome)
                        .queryParam("cognome", cognome)
                        .build())
                .retrieve()
                .bodyToMono(Long.class)
                .block();
    }

    public DiscenteDTO createDiscente(DiscenteDTO discenteDTO) {
        return webClient.post()
                .bodyValue(discenteDTO)
                .header("Content-Type", "application/json")
                .retrieve()
                .bodyToMono(DiscenteDTO.class)
                .block();
    }




}
