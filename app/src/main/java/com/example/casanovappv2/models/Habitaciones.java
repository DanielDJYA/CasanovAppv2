package com.example.casanovappv2.models;

public class Habitaciones {
    private String Nombre;
    private String Descripcion;
    private String Precio;
    private String Estado;
    private String Foto;

    public Habitaciones(String nombre, String descripcion, String precio) {
        Nombre = nombre;
        Descripcion = descripcion;
        Precio = precio;
    }


    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getPrecio() {
        return Precio;
    }

    public void setPrecio(String precio) {
        Precio = precio;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public String getFoto() {
        return Foto;
    }

    public void setFoto(String foto) {
        Foto = foto;
    }
}
