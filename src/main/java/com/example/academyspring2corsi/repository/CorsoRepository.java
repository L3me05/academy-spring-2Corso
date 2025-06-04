package com.example.academyspring2corsi.repository;


import com.example.academyspring2corsi.data.entity.Corso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CorsoRepository extends JpaRepository<Corso, Long>{

    @Query("SELECT c.id FROM Corso c WHERE c.nome = :nome AND c.annoAccademico = :annoAccademico")
    Long findIdByNomeAndAnnoAccademico(@Param("nome") String nome, @Param("annoAccademico") Integer annoAccademico);

}

