package com.example.academyspring2corsi.feignClient;

import com.example.academyspring2corsi.data.dto.DocenteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "docenti", url = "http://localhost:8080/docenti")
public interface DocentiClient {

    @RequestMapping(method = RequestMethod.GET, value = "/list")
    List<DocenteDTO> findAll();

    @RequestMapping(method = RequestMethod.GET, value = "/findById")
    DocenteDTO findById(@RequestParam("id") Long id);

    @RequestMapping(method = RequestMethod.GET, value = "/present")
    Boolean existsById(@RequestParam("id") Long id);
}
