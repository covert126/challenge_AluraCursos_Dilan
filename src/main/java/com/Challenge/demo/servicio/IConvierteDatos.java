package com.Challenge.demo.servicio;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
