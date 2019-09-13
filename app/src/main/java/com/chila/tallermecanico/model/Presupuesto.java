package com.chila.tallermecanico.model;

import java.util.Date;
import java.util.List;

public class Presupuesto {
    private String id;
    private List<Reparacion> repuestos;
    private double precioPresupuesto;
    private boolean aceptado;
    private int vigencia;
    private double anticipoRequerido;
    private Date fechaAceptado;
    private String firmaCliente;
    private List<String> fotos;


}

