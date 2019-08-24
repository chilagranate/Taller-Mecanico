package com.chila.tallermecanico.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrdenTrabajo extends Comprobante

{
    private String id;
    private String user;
    private List<Pago> pagos;
    private List<ItemComprobante> itemsOrdenTrabajo;
    private List<NotaComprobante> notas;



    private Date fechaEntrada, fechaSalida, fechaEstimada;
    private int naftaRestante;
    private boolean aceptado;
    private boolean finalizado;
    private boolean pagado;


    private String fallaCliente;
    private String observacionesGenerales;

    public OrdenTrabajo() {
    }

    public String getUser() {
        return user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUser(String user) {
        this.user = user;
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

    public List<ItemComprobante> getItemsOrdenTrabajo() {
        return itemsOrdenTrabajo;
    }

    public void setItemsOrdenTrabajo(List<ItemComprobante> itemsOrdenTrabajo) {
        this.itemsOrdenTrabajo = itemsOrdenTrabajo;
    }

    public List<NotaComprobante> getNotas() {
        return notas;
    }

    public void setNotas(List<NotaComprobante> notas) {
        this.notas = notas;
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

    public int getNaftaRestante() {
        return naftaRestante;
    }

    public void setNaftaRestante(int naftaRestante) {
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
