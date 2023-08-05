package com.example.pc4clientesapi.Models;

public class TipoCliente {
    private String nombre = "";
    private String detalle = "";
    private String idTipoCliente = "";

    public TipoCliente(){}

    public TipoCliente(String nombre, String detalle, String idTipoCliente){
        this.nombre = nombre;
        this.detalle = detalle;
        this.idTipoCliente = idTipoCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getIdTipoCliente() {
        return idTipoCliente;
    }

    public void setIdTipoCliente(String idTipoCliente) {
        this.idTipoCliente = idTipoCliente;
    }
}
