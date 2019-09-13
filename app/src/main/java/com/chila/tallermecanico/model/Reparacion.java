package com.chila.tallermecanico.model;

import java.util.ArrayList;
import java.util.List;

public class Reparacion {

    String nombreReparacion;
    String descripcion;
    List<Repuesto> repuestos;
    List<ManoDeObra> manoDeObra;
    List<String> fotos;

    public Reparacion() {
        repuestos = new ArrayList<>();
        manoDeObra = new ArrayList<>();
        fotos = new ArrayList<>();
    }

    public String getNombreReparacion() {
        return nombreReparacion;
    }

    public void setNombreReparacion(String nombreReparacion) {
        this.nombreReparacion = nombreReparacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Repuesto> getRepuestos() {
        return repuestos;
    }

    public void setRepuestos(List<Repuesto> repuestos) {
        this.repuestos = repuestos;
    }

    public List<ManoDeObra> getManoDeObra() {
        return manoDeObra;
    }

    public void setManoDeObra(List<ManoDeObra> manoDeObra) {
        this.manoDeObra = manoDeObra;
    }

    public List<String> getFotos() {
        return fotos;
    }

    public void setFotos(List<String> fotos) {
        this.fotos = fotos;
    }
}
