package com.Challenge.demo.modelos;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private Integer anioNacimiento;

    private Integer anioFallecimiento;

    public Autor() {}

    public Autor(String nombre, Integer anioNacimiento, Integer anioFallecimiento) {
        this.nombre = nombre;
        this.anioNacimiento = anioNacimiento;
        this.anioFallecimiento = anioFallecimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getAnioNacimiento() {
        return anioNacimiento;
    }

    public void setAnioNacimiento(Integer anioNacimiento) {
        this.anioNacimiento = anioNacimiento;
    }

    public Integer getAnioFallecimiento() {
        return anioFallecimiento;
    }

    public void setAnioFallecimiento(Integer anioFallecimiento) {
        this.anioFallecimiento = anioFallecimiento;
    }

    @Override
    public String toString() {
        return
                "nombre='" + nombre + '\'' +
                        ", anioNacimiento=" + anioNacimiento +
                        ", anioFallecimiento=" + anioFallecimiento;
    }

    // =======================
    // Para que distinct() funcione
    // =======================
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Autor autor)) return false;
        return Objects.equals(nombre, autor.nombre) &&
                Objects.equals(anioNacimiento, autor.anioNacimiento) &&
                Objects.equals(anioFallecimiento, autor.anioFallecimiento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, anioNacimiento, anioFallecimiento);
    }
}
