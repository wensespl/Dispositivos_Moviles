package com.example.pc3app;

public class Jugador {
    private String nombre = "";
    private int puntaje;
    private int idjugador = 0;
    private  String key = "";
    private String codigo = "";

    public Jugador(){}

    public Jugador(String n, int p, int id) {
        nombre = n;
        puntaje = p;
        idjugador = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    public int getIdjugador() {
        return idjugador;
    }

    public void setIdjugador(int idjugador) {
        this.idjugador = idjugador;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
