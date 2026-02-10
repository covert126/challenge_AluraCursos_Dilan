package com.Challenge.demo.modelos;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosRespuestaLibros(
        @JsonAlias("results") List<DatosLibro> libros,
        @JsonAlias("next") String next,
        @JsonAlias("previous") String previous
) {
}