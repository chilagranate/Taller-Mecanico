package com.chila.tallermecanico.model;

import java.util.Date;
import java.util.List;

public class OrdenServicio {
    private String id;
    private Auto auto;


    private String userMauth;
    private List<Pago> pagos;



    private Date fechaEntrada, fechaSalida, fechaEstimada;
    private String naftaRestante;
    private boolean aceptado;
    private boolean finalizado;
    private boolean pagado;


    private String fallaCliente;
    private String observacionesGenerales;

    public OrdenServicio() {
    }


    public Auto getAuto() {
        return auto;
    }

    public void setAuto(Auto auto) {
        this.auto = auto;
    }

    public String getUserMauth() {
        return userMauth;
    }

    public void setUserMauth(String userMauth) {
        this.userMauth = userMauth;
    }

    public String getUser() {
        return userMauth;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUser(String user) {
        this.userMauth = user;
    }

    public String getFallaCliente() {
        return fallaCliente;
    }

    public void setFallaCliente(String fallaCliente) {
        this.fallaCliente = fallaCliente;
    }

    public String getObservacionesGenerales() {
        return observacionesGenerales;
    }

    public void setObservacionesGenerales(String observacionesGenerales) {
        this.observacionesGenerales = observacionesGenerales;
    }
    public List<Pago> getPagos() {
        return pagos;
    }

    public void setPagos(List<Pago> pagos) {
        this.pagos = pagos;
    }


    public Date getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(Date fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public Date getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public Date getFechaEstimada() {
        return fechaEstimada;
    }

    public void setFechaEstimada(Date fechaEstimada) {
        this.fechaEstimada = fechaEstimada;
    }

    public String getNaftaRestante() {
        return naftaRestante;
    }

    public void setNaftaRestante(String naftaRestante) {
        this.naftaRestante = naftaRestante;
    }

    public boolean isAceptado() {
        return aceptado;
    }

    public void setAceptado(boolean aceptado) {
        this.aceptado = aceptado;
    }

    public boolean isFinalizado() {
        return finalizado;
    }

    public void setFinalizado(boolean finalizado) {
        this.finalizado = finalizado;
    }

    public boolean isPagado() {
        return pagado;
    }

    public void setPagado(boolean pagado) {
        this.pagado = pagado;
    }



}
