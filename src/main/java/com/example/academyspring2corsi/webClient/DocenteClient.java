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

//    public List<DocenteDTO> findAllDocenti() {
//        return webClient.method(HttpMethod.GET)
//                .uri("/list")
//                .retrieve()
//                .bodyToMono(List.class)
//                .block();
//    }

    public DocenteDTO docenteById(Long id) {
        return webClient.method(HttpMethod.GET)
                .uri("/findById?id={id}" ,id)
                .retrieve()
                .bodyToMono(DocenteDTO.class)
                .block();
    }

    public boolean existsById(Long id) {
        return Boolean.TRUE.equals(webClient.method(HttpMethod.GET)
                .uri(uriBuilder -> uriBuilder
                        .path("/present")
                        .queryParam("id", id)
                        .build())
                .retrieve()
                .bodyToMono(Boolean.class)
                .block());
    }
}
