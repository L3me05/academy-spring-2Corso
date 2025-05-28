package com.example.academyspring2corsi.repository;


import com.example.academyspring2corsi.data.entity.Corso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CorsoRepository extends JpaRepository<Corso, Long>{



}

