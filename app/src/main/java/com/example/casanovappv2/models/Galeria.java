package com.example.casanovappv2.models;

public class Galeria {
    private String titulo;
    private String foto;

    public Galeria() {

    }

    public Galeria(String titulo, String foto) {
        this.titulo = titulo;
        this.foto = foto;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
