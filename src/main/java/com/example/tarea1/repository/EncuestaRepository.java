package com.example.tarea1.repository;

import com.example.tarea1.model.Encuesta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface EncuestaRepository extends JpaRepository<Encuesta, Long> {

    public List<Encuesta> findByNivelSatisfaccion(String satisfaccion);


}
