package com.example.academyspring2corsi.webClient;

import com.example.academyspring2corsi.data.dto.DocenteDTO;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;


@Component
public class DocenteClient {
    private final WebClient webClient;

    public DocenteClient() {
        this.webClient = WebClient.create("http://localhost:8080/docenti");
    }


    public DocenteDTO docenteById(Long id) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/findById")
                        .queryParam("id", id)
                        .build())
                .retrieve()
                .bodyToMono(DocenteDTO.class)
                .block();
    }


//    public boolean existsById(Long id) {
//        return Boolean.TRUE.equals(webClient.method(HttpMethod.GET)
//                .uri(uriBuilder -> uriBuilder
//                        .path("/present")
//                        .queryParam("id", id)
//                        .build())
//                .retrieve()
//                .bodyToMono(Boolean.class)
//                .block());
//    }

    public Long findIdByNomeAndCognome(String nome, String cognome) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/findIdByNomeAndCognome")
                        .queryParam("nome", nome)
                        .queryParam("cognome", cognome)
                        .build())
                .retrieve()
                .bodyToMono(Long.class)
                .block();
    }

    public Long createDocenteAndReturnId(DocenteDTO docenteDTO) {
        return webClient.post()
                .uri("/createAndReturnId")
                .bodyValue(docenteDTO)
                .header("Content-Type", "application/json")
                .retrieve()
                .bodyToMono(Long.class)
                .block();
    }
}
