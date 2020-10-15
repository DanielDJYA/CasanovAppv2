package com.example.casanovappv2.models;

public class Usuarios {
    private String Nombres;
    private String Apellidos;
    private String NDni;
    private String Edad;
    private String Telefono;
    private String Correo;
    private String Contraseña;

    public Usuarios(String nombres, String apellidos) {
        Nombres = nombres;
        Apellidos = apellidos;
    }

    public String getNombres() {
        return Nombres;
    }

    public void setNombres(String nombres) {
        Nombres = nombres;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String apellidos) {
        Apellidos = apellidos;
    }

    public String getNDni() {
        return NDni;
    }

    public void setNDni(String NDni) {
        this.NDni = NDni;
    }

    public String getEdad() {
        return Edad;
    }

    public void setEdad(String edad) {
        Edad = edad;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public String getContraseña() {
        return Contraseña;
    }

    public void setContraseña(String contraseña) {
        Contraseña = contraseña;
    }
}

