package com.example.academyspring2corsi.service;


import com.example.academyspring2corsi.data.dto.CorsoDTO;
import com.example.academyspring2corsi.data.dto.DiscenteDTO;
import com.example.academyspring2corsi.data.dto.DocenteDTO;
import com.example.academyspring2corsi.data.entity.Corso;
import com.example.academyspring2corsi.data.entity.CorsoDiscente;
import com.example.academyspring2corsi.mapstruct.CorsoMapper;
import com.example.academyspring2corsi.repository.CorsoDiscenteRepository;
import com.example.academyspring2corsi.repository.CorsoRepository;
import com.example.academyspring2corsi.webClient.DiscenteClient;
import com.example.academyspring2corsi.webClient.DocenteClient;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CorsoService {
    private final CorsoRepository corsoRepository;
    private final CorsoMapper corsoMapper;
    private final DocenteClient docenteClient;
    private final CorsoDiscenteRepository corsoDiscenteRepository;
    private final DiscenteClient discenteClient;


    public List<CorsoDTO> findAll() {
        return corsoRepository.findAll().stream()
                .map(corso -> {
                    CorsoDTO dto = corsoMapper.corsoToDto(corso);
                    loadDocenteOnCorso(dto, corso.getIdDocente());
                    loadDiscentiOnCorso(dto);
                    return dto;
                })
                .collect(Collectors.toList());
    }


    @Transactional
    public CorsoDTO save(CorsoDTO corsoDTO) {
        Long idDocente = validateOrCreateDocente(corsoDTO.getDocente());
        Corso corso = corsoMapper.corsoToEntity(corsoDTO);
        corso.setIdDocente(idDocente);
        Corso savedCorso = corsoRepository.save(corso);

        saveDiscenti(corsoDTO, savedCorso.getId());
        
        return corsoMapper.corsoToDto(savedCorso);
    }




    @Transactional
    public CorsoDTO update(Long id, CorsoDTO corsoDTO) {
        Corso corso = corsoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Corso non trovato con id: " + id));

        Long idDocente=null;
        if (!Objects.isNull(corsoDTO.getDocente())) {
            idDocente =validateOrCreateDocente(corsoDTO.getDocente());
        }
        
        updateCorso(corso, corsoDTO, idDocente );
        Corso savedCorso = corsoRepository.save(corso);

        if (!Objects.isNull(corsoDTO.getDiscenti())) {
            removeExistingDiscenti(id);
            saveDiscenti(corsoDTO, id);
        }

        return corsoMapper.corsoToDto(savedCorso);
    }


    @Transactional
    public void delete(Long id) {
        if (!corsoRepository.existsById(id)) {
            throw new EntityNotFoundException("Corso non trovato con id: " + id);
        }
        corsoRepository.deleteById(id);
        removeExistingDiscenti(id);
    }




    private void loadDocenteOnCorso(CorsoDTO corsoDTO, Long idDocente ) {
        corsoDTO.setDocente(docenteClient.docenteById(idDocente));
    }

    private void loadDiscentiOnCorso(CorsoDTO corsoDTO) {
        Long idCorso = corsoRepository.findIdByNomeAndAnnoAccademico(
            corsoDTO.getNome(),
            corsoDTO.getAnnoAccademico()
        );
        List<Long> idsDiscenti = corsoDiscenteRepository.findIdsDiscenteByIdCorso(idCorso);
        List<DiscenteDTO> discenti = idsDiscenti.stream()
                .map(discenteClient::discenteById)
                .collect(Collectors.toList());
        corsoDTO.setDiscenti(discenti);
    }

    private Long validateOrCreateDocente(DocenteDTO docenteDTO) {
        if (Objects.isNull(docenteDTO)) return null;
        else {
            Long idDocente = docenteClient.findIdByNomeAndCognome(docenteDTO.getNome(), docenteDTO.getCognome());
            if (idDocente == null) idDocente= docenteClient.createDocenteAndReturnId(docenteDTO);
            return idDocente;
        }
    }

//    private void validateDocente(Long idDocente) {
//        if (idDocente != null && !docenteClient.existsById(idDocente)) {
//            throw new EntityNotFoundException("Docente non trovato con id: " + idDocente);
//        }
//    }

    private void saveDiscenti(CorsoDTO corsoDTO, Long idCorso) {
        if (corsoDTO.getDiscenti() == null) {
            return;
        }

        corsoDTO.getDiscenti().stream()
                .map(discenteDTO -> {
                    Long idDiscente = discenteClient.idDiscenteByNomeAndCognome(
                        discenteDTO.getNome(), 
                        discenteDTO.getCognome()
                    );
                    if (idDiscente == null) {
                        discenteClient.createDiscente(discenteDTO);
                        idDiscente = discenteClient.idDiscenteByNomeAndCognome(
                            discenteDTO.getNome(),
                            discenteDTO.getCognome()
                        );
                    }
                    CorsoDiscente corsoDiscente = new CorsoDiscente();
                    corsoDiscente.setIdCorso(idCorso);
                    corsoDiscente.setIdDiscente(idDiscente);
                    return corsoDiscente;

                })
                .forEach(corsoDiscenteRepository::save);
    }


    private void removeExistingDiscenti(Long idCorso) {
        corsoDiscenteRepository.deleteByIdCorso(idCorso);
    }

    private void updateCorso(Corso corso, CorsoDTO corsoDTO, Long idDocente ) {
        Optional.ofNullable(corsoDTO.getNome()).ifPresent(corso::setNome);
        Optional.ofNullable(corsoDTO.getAnnoAccademico()).ifPresent(corso::setAnnoAccademico);
        Optional.ofNullable(idDocente).ifPresent(corso::setIdDocente);
    }
}