# Desafío Alura Cursos – Gestión de Libros

Este proyecto corresponde al desafío propuesto en los cursos de **Java y Spring Boot** de Alura, cuyo objetivo es consumir una API pública de libros, persistir información en una base de datos y realizar consultas utilizando **Spring Data JPA**.

La aplicación se ejecuta por consola y permite interactuar con libros y autores obtenidos desde la API **Gutendex**.

---

## Tecnologías utilizadas

- Java 17  
- Spring Boot  
- Spring Data JPA  
- Hibernate  
- Maven  
- PostgreSQL  
- API Gutendex (https://gutendex.com)

---

## Descripción general del proyecto

La aplicación consume datos de libros desde la API de Gutendex, procesa la información recibida y permite:

- Buscar libros y autores desde la API
- Guardar libros y sus autores en una base de datos
- Consultar libros y autores almacenados
- Realizar filtros utilizando consultas JPA

El proyecto sigue una arquitectura organizada en capas:
- **modelos**: entidades JPA y DTOs
- **repositorio**: acceso a datos con Spring Data JPA
- **servicio**: consumo de API y conversión de datos
- **principal**: lógica principal y menú por consola

---

## Funcionalidades disponibles

La aplicación presenta un menú interactivo por consola con las siguientes opciones:

1. **Buscar libro por nombre**  
   Consulta la API de Gutendex y muestra la información del primer libro encontrado.

2. **Buscar autor por nombre**  
   Busca un autor recorriendo los resultados de la API, incluso a través de múltiples páginas.

3. **Buscar libro por idioma**  
   Muestra todos los libros disponibles en un idioma específico (por ejemplo: `en`, `es`, `fr`).

4. **Buscar libro por nombre y guardar en la base de datos**  
   Obtiene un libro desde la API y lo guarda en la base de datos junto con sus autores.

5. **Mostrar libros registrados**  
   Lista todos los libros almacenados en la base de datos, mostrando:
   - Título
   - Idiomas
   - Número de descargas
   - Autores asociados

6. **Mostrar autores registrados**  
   Muestra todos los autores almacenados en la base de datos sin duplicados.

7. **Listar libros por idioma (base de datos)**  
   Permite consultar libros almacenados filtrando por idioma usando consultas JPA.

8. **Mostrar autores vivos en un año determinado**  
   Muestra los autores que estaban vivos en un año ingresado por el usuario, utilizando una consulta JPA personalizada.

---

## Modelo de datos

### Entidad Libro

- id  
- titulo  
- numeroDescargas  
- idiomas (colección de elementos)  
- autores (relación OneToMany)

Un libro puede tener varios autores y varios idiomas.

---

### Entidad Autor

- id  
- nombre  
- anioNacimiento  
- anioFallecimiento  

Se implementaron los métodos `equals()` y `hashCode()` para evitar duplicados al consultar autores.

---

## Persistencia y JPA

Se utiliza **Spring Data JPA** con un único repositorio:
`java
 public interface LibroRepository extends JpaRepository<Libro, Long> {
}`

Este repositorio es suficiente para el proyecto, ya que Spring Data JPA proporciona automáticamente los métodos CRUD básicos y permite la creación de consultas personalizadas mediante anotaciones, sin necesidad de implementar la lógica manualmente.

---

## Aprendizajes obtenidos

Durante el desarrollo de este proyecto se reforzaron conceptos fundamentales como:

- Consumo de APIs REST externas
- Manejo de datos paginados
- Uso de DTOs para separar la lógica de negocio
- Modelado de entidades y relaciones con JPA
- Consultas personalizadas con Spring Data JPA
- Persistencia de datos con PostgreSQL
- Organización de un proyecto siguiendo buenas prácticas

---

## Estado del proyecto

El proyecto se encuentra **finalizado** y cumple con todos los requerimientos del desafío propuesto por Alura Cursos.

