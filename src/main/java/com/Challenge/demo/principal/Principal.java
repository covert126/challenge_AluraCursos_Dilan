package com.Challenge.demo.principal;

import com.Challenge.demo.modelos.*;
import com.Challenge.demo.repositorio.LibroRepository;
import com.Challenge.demo.servicio.ConsumoApi;
import com.Challenge.demo.servicio.ConvierteDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class Principal {

    private final Scanner teclado = new Scanner(System.in);
    private final ConsumoApi consumoApi = new ConsumoApi();
    private final ConvierteDatos conversor = new ConvierteDatos();
    private final LibroRepository repository;

    private final String URL_BASE = "https://gutendex.com/books/";

    @Autowired
    public Principal(LibroRepository repository) {
        this.repository = repository;
    }

    public void muestraElMenu() {
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("""
                    1 - Buscar libro por nombre
                    2 - Buscar autor por nombre
                    3 - Buscar libro por idioma
                    4 - Buscar libro por nombre y guardar en BD
                    5 - Mostrar libros registrados
                    6 - Mostrar autores registrados
                    7 - Mostrar libros registrados por idioma
                    8 - Mostrar autores vivos en un año
                    0 - Salir
                    """);

            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1 -> buscarLibroPorNombre();
                case 2 -> buscarAutorPorNombre();
                case 3 -> buscarLibroPorIdioma();
                case 4 -> buscarYGuardarLibro();
                case 5 -> mostrarLibrosRegistrados();
                case 6 -> mostrarAutoresRegistrados();
                case 7 -> mostrarLibrosPorIdioma();
                case 8 -> mostrarAutoresVivosEnAno();
                case 0 -> System.out.println("Cerrando aplicación...");
                default -> System.out.println("Opción inválida");
            }
        }
    }

    private void buscarLibroPorNombre() {
        System.out.println("Escribe el nombre del libro:");
        String nombreLibro = teclado.nextLine().trim();
        String url = URL_BASE + "?search=" + nombreLibro.replace(" ", "%20");
        String json = consumoApi.obtenerDatos(url);
        DatosRespuestaLibros respuesta = conversor.obtenerDatos(json, DatosRespuestaLibros.class);

        respuesta.libros().stream()
                .findFirst()
                .ifPresentOrElse(
                        libro -> {
                            System.out.println("***************************");
                            System.out.println("Título: " + libro.titulo());
                            System.out.println("Idiomas: " + libro.idiomas());
                            System.out.println("Número de descargas: " + libro.numeroDescargas());
                            System.out.println("Autores:");
                            libro.autores().forEach(a -> System.out.println(" - " + a.nombre()));
                        },
                        () -> System.out.println("No se encontró ningún libro.")
                );
        System.out.println("***************************");
    }

    private void buscarAutorPorNombre() {
        System.out.println("Escribe el nombre del autor:");
        String nombreAutor = teclado.nextLine().toLowerCase().trim();

        String url = URL_BASE + "?search=" + nombreAutor.replace(" ", "%20");
        boolean encontrado = false;

        while (url != null && !encontrado) {
            String json = consumoApi.obtenerDatos(url);
            DatosRespuestaLibros respuesta = conversor.obtenerDatos(json, DatosRespuestaLibros.class);

            Optional<DatosAutor> autorEncontrado = respuesta.libros().stream()
                    .flatMap(libro -> libro.autores().stream())
                    .filter(autor -> autor.nombre().toLowerCase().contains(nombreAutor))
                    .findFirst();

            if (autorEncontrado.isPresent()) {
                System.out.println("Autor encontrado:");
                System.out.println(autorEncontrado.get());
                encontrado = true;
            } else {
                url = respuesta.next(); // siguiente página
            }
        }

        if (!encontrado) {
            System.out.println("No se encontró ningún autor.");
        }
    }

    private void buscarLibroPorIdioma() {
        System.out.println("Escribe el código del idioma (ej: en, es, fr, pt):");
        String idioma = teclado.nextLine().trim().toLowerCase();
        String url = URL_BASE + "?languages=" + idioma;

        while (url != null) {
            String json = consumoApi.obtenerDatos(url);
            DatosRespuestaLibros respuesta = conversor.obtenerDatos(json, DatosRespuestaLibros.class);

            if (respuesta.libros().isEmpty()) {
                System.out.println("No se encontraron libros en este idioma.");
                return;
            }

            for (DatosLibro libro : respuesta.libros()) {
                System.out.println("***************************");
                System.out.println("Título: " + libro.titulo());
                System.out.println("Idiomas: " + libro.idiomas());
                System.out.println("Número de descargas: " + libro.numeroDescargas());
                System.out.println("Autores:");
                libro.autores().forEach(a -> System.out.println(" - " + a.nombre()));
                System.out.println("***************************");
            }

            url = respuesta.next(); // siguiente página
        }
    }

    private void buscarYGuardarLibro() {
        System.out.println("Escribe el nombre del libro para buscar y guardar:");
        String nombreLibro = teclado.nextLine().trim();
        String url = URL_BASE + "?search=" + nombreLibro.replace(" ", "%20");
        String json = consumoApi.obtenerDatos(url);
        DatosRespuestaLibros respuesta = conversor.obtenerDatos(json, DatosRespuestaLibros.class);

        respuesta.libros().stream()
                .findFirst()
                .ifPresentOrElse(
                        libro -> {
                            Libro libroBD = new Libro(libro.titulo(), libro.idiomas(), libro.numeroDescargas());

                            libro.autores().forEach(a -> libroBD.getAutores()
                                    .add(new Autor(a.nombre(), a.anioNacimiento(), a.anioFallecimiento()))
                            );

                            repository.save(libroBD);

                            System.out.println("Libro guardado en la base de datos:");
                            System.out.println(libroBD);
                        },
                        () -> System.out.println("No se encontró ningún libro.")
                );
    }

    private void mostrarLibrosRegistrados() {
        List<Libro> libros = repository.findAll();

        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados en la base de datos.");
            return;
        }

        System.out.println("Libros registrados:");
        for (Libro libro : libros) {
            System.out.println("***************************");
            System.out.println("Título: " + libro.getTitulo());
            System.out.println("Idiomas: " + libro.getIdiomas());
            System.out.println("Número de descargas: " + libro.getNumeroDescargas());
            System.out.println("Autores:");
            libro.getAutores().forEach(a -> System.out.println(" - " + a.getNombre()));
            System.out.println("***************************");
        }
    }

    private void mostrarAutoresRegistrados() {
        List<Libro> libros = repository.findAll();

        if (libros.isEmpty()) {
            System.out.println("No hay autores registrados porque no hay libros.");
            return;
        }

        System.out.println("Autores registrados en la base de datos:");
        libros.stream()
                .flatMap(libro -> libro.getAutores().stream())
                .distinct()
                .forEach(a -> {
                    System.out.println("Nombre: " + a.getNombre());
                    System.out.println("Nacimiento: " + a.getAnioNacimiento());
                    System.out.println("Fallecimiento: " + a.getAnioFallecimiento());
                    System.out.println("------------------------");
                });
    }

    private void mostrarLibrosPorIdioma() {
        System.out.println("Escribe el idioma:");
        String idioma = teclado.nextLine().trim().toLowerCase();

        List<Libro> libros = repository.findByIdioma(idioma);

        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros en ese idioma.");
            return;
        }

        System.out.println("Libros en idioma " + idioma + ":");
        libros.forEach(libro -> {
            System.out.println("Título: " + libro.getTitulo());
            libro.getAutores().forEach(a -> System.out.println(" - " + a.getNombre()));
        });
    }

    private void mostrarAutoresVivosEnAno() {
        System.out.println("Escribe el año:");
        int anio = teclado.nextInt();
        teclado.nextLine();

        List<Autor> autores = repository.findAutoresVivosEnAno(anio);

        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores vivos en " + anio);
            return;
        }

        System.out.println("Autores vivos en " + anio + ":");
        autores.forEach(a -> System.out.println(a.getNombre() + " (" + a.getAnioNacimiento() + " - " + a.getAnioFallecimiento() + ")"));
    }

}
