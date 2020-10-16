package com.example.casanovappv2.models;

public class Galeria {
    private String Nombre;
    private String Foto;

    public Galeria() {

    }

    public Galeria(String nombre, String foto) {
        Nombre = nombre;
        Foto = foto;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getFoto() {
        return Foto;
    }

    public void setFoto(String foto) {
        Foto = foto;
    }
}
