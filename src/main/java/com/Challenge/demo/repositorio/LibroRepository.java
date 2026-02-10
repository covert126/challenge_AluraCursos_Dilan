package com.Challenge.demo.repositorio;

import com.Challenge.demo.modelos.Autor;
import com.Challenge.demo.modelos.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    Optional<Libro> findByTituloContainsIgnoreCase(String titulo);
    @Query("SELECT l FROM Libro l JOIN l.idiomas i WHERE i = :idioma")
    List<Libro> findByIdioma(String idioma);

    @Query("SELECT DISTINCT a FROM Libro l JOIN l.autores a WHERE a.anioNacimiento <= :anio AND (a.anioFallecimiento IS NULL OR a.anioFallecimiento >= :anio)")
    List<Autor> findAutoresVivosEnAno(Integer anio);
}
