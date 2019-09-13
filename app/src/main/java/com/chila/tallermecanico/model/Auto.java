package com.chila.tallermecanico.model;

import com.google.firebase.firestore.DocumentId;

import java.util.List;

public class Auto {
    private String id;
    private Cliente cliente;
    private String fotoPortada;
    private String patente;
    private String marca;
    private String modelo;
    private String VIN;
    private String usermAuth;
    private String ano;
    private String km;
    private List<String> fotosAuto;

    public Auto() {
    }
    public String toString(){
        return patente + ": " + marca + " " + modelo;
    }

    public String getModeloCompleto(){
        return marca + " " + modelo;
    }
    public String getId() {
        return id;
    }
    public String getUsermAuth() {
        return usermAuth;
    }

    public void setUsermAuth(String usermAuth) {
        this.usermAuth = usermAuth;
    }
    public void setId(String id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getFotoPortada() {
        return fotoPortada;
    }

    public void setFotoPortada(String fotoPortada) {
        this.fotoPortada = fotoPortada;
    }

    public String getPatente() {

        return patente.toUpperCase();
    }

    public void setPatente(String patente) {
        this.patente = patente.toUpperCase();
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getVIN() {
        return VIN;
    }

    public void setVIN(String VIN) {
        this.VIN = VIN;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getKm() {
        return km;
    }

    public void setKm(String km) {
        this.km = km;
    }

    public List<String> getFotosAuto() {
        return fotosAuto;
    }

    public void setFotosAuto(List<String> fotosAuto) {
        this.fotosAuto = fotosAuto;
    }
}
