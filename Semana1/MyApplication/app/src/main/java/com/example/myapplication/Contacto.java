package com.example.myapplication;

public class Contacto {
    private  int idcontacto = 0;
    private String nombre = "";
    private int edad  = 0;
    public Contacto(){}
    public Contacto(int idcontacto, String nombre, int edad) {
        this.idcontacto = idcontacto;
        this.nombre = nombre;
        this.edad = edad;
    }

    public int getIdcontacto() {
        return idcontacto;
    }

    public void setIdcontacto(int idcontacto) {
        this.idcontacto = idcontacto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
}
