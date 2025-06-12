package com.example.academyspring2corsi.repository.corso;


import com.example.academyspring2corsi.data.entityCorso.Corso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CorsoRepository extends JpaRepository<Corso, Long>{

    @Query("SELECT c.id FROM Corso c WHERE c.nome = :nome AND c.annoAccademico = :annoAccademico")
    Long findIdByNomeAndAnnoAccademico(@Param("nome") String nome, @Param("annoAccademico") Integer annoAccademico);

}

