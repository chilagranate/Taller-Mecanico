package com.chila.tallermecanico.model;

import org.parceler.Parcel;

@Parcel
public class Repuesto{

    private String descripcion;
    private double precio;
    private int cantidad;

    public Repuesto() {
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
