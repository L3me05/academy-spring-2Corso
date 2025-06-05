package com.example.academyspring2corsi.repository;

import com.example.academyspring2corsi.data.entity.CorsoDiscente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CorsoDiscenteRepository extends JpaRepository <CorsoDiscente, Long> {

    @Query("SELECT cd.idDiscente FROM CorsoDiscente cd WHERE cd.idCorso = :idCorso")
    List<Long> findIdsDiscenteByIdCorso(Long idCorso);

    void deleteByIdCorso(Long idCorso);
}
