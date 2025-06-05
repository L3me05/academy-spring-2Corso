package com.example.academyspring2corsi.controller;

import com.example.academyspring2corsi.data.dto.CorsoDTO;
import com.example.academyspring2corsi.data.dto.DocenteDTO;
import com.example.academyspring2corsi.service.CorsoService;
import jakarta.servlet.ServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/corsi")
@RequiredArgsConstructor
public class CorsoController {


    private final CorsoService corsoService;

    //lista
    @GetMapping("/list")
    public List<CorsoDTO> list() {
        return corsoService.findAll();

    }

    @PostMapping
    public CorsoDTO create(@RequestBody CorsoDTO corsoDTO) {
        return corsoService.save(corsoDTO);
    }

    @PutMapping("/{id}")
    public CorsoDTO update(@PathVariable Long id, @RequestBody CorsoDTO corsoDTO) {
        return corsoService.update(id, corsoDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        corsoService.delete(id);
    }



}
