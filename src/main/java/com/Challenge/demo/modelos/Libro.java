package com.Challenge.demo.modelos;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private Integer numeroDescargas;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> idiomas = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "libro_id")
    private List<Autor> autores = new ArrayList<>();

    public Libro() {}

    public Libro(String titulo, List<String> idiomas, Integer numeroDescargas) {
        this.titulo = titulo;
        this.idiomas = idiomas;
        this.numeroDescargas = numeroDescargas;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    public Integer getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(Integer numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "titulo='" + titulo + '\'' +
                ", numeroDescargas=" + numeroDescargas +
                ", idiomas=" + idiomas +
                ", autores=" + autores +
                '}';
    }
}
