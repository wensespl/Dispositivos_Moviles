package com.example.myfirebasepenadillo;

public class Contacto {
    private String nombre = "";
    private String alias = "";
    private int idcontacto = 0;
    private  String key = "";
    private String codigo = "";

    public Contacto(){}
    public Contacto(String n, String a, int id) {
        nombre = n;
        alias = a;
        idcontacto = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int getIdcontacto() {
        return idcontacto;
    }

    public void setIdcontacto(int idcontacto) {
        this.idcontacto = idcontacto;
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
