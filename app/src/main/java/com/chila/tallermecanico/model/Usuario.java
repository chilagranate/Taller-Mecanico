package com.chila.tallermecanico.model;

public class Usuario {
    private static final Usuario instance = new Usuario();

    private String id;
    private String nombre;
    private String apellido;
    private String telefono;
    private String eMail;



    public Usuario() {

    }

    public static Usuario getInstance(){
        return instance;
    }

    public Usuario(String uid, String eMail){
        this.id = uid;
        this.eMail = eMail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }
}
