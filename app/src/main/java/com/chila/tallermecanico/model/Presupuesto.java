package com.chila.tallermecanico.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Presupuesto {
    private String id;
    private List<Reparacion> reparaciones;
    private double precioPresupuesto;
    private boolean aceptado;
    private int vigencia;
    private double anticipoRequerido;
    private Date fechaAceptado;
    private String firmaCliente;
    private List<String> fotos;

    public Presupuesto() {
        reparaciones = new ArrayList<>();
        fotos = new ArrayList<>();
    }
    public void addReparacion(Reparacion reparacion){
        reparaciones.add(reparacion);
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Reparacion> getReparaciones() {
        return reparaciones;
    }

    public void setReparaciones(List<Reparacion> reparaciones) {
        this.reparaciones = reparaciones;
    }

    public double getPrecioPresupuesto() {
        return precioPresupuesto;
    }

    public void setPrecioPresupuesto(double precioPresupuesto) {
        this.precioPresupuesto = precioPresupuesto;
    }

    public boolean isAceptado() {
        return aceptado;
    }

    public void setAceptado(boolean aceptado) {
        this.aceptado = aceptado;
    }

    public int getVigencia() {
        return vigencia;
    }

    public void setVigencia(int vigencia) {
        this.vigencia = vigencia;
    }

    public double getAnticipoRequerido() {
        return anticipoRequerido;
    }

    public void setAnticipoRequerido(double anticipoRequerido) {
        this.anticipoRequerido = anticipoRequerido;
    }

    public Date getFechaAceptado() {
        return fechaAceptado;
    }

    public void setFechaAceptado(Date fechaAceptado) {
        this.fechaAceptado = fechaAceptado;
    }

    public String getFirmaCliente() {
        return firmaCliente;
    }

    public void setFirmaCliente(String firmaCliente) {
        this.firmaCliente = firmaCliente;
    }

    public List<String> getFotos() {
        return fotos;
    }

    public void setFotos(List<String> fotos) {
        this.fotos = fotos;
    }
}

