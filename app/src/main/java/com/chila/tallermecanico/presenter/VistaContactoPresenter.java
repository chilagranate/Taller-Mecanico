package com.chila.tallermecanico.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chila.tallermecanico.Firestore.Database;
import com.chila.tallermecanico.Firestore.FirestoreCallbackAuto;
import com.chila.tallermecanico.Firestore.FirestoreCallbackCliente;
import com.chila.tallermecanico.Firestore.FirestoreCallbackFoto;
import com.chila.tallermecanico.model.Auto;
import com.chila.tallermecanico.model.Cliente;
import com.chila.tallermecanico.view.IVistaContacto;
import com.chila.tallermecanico.view.NuevoAutoActivity;

import java.util.List;

public class VistaContactoPresenter implements IVistaContactoPresenter  {

    private IVistaContacto iVistaContacto;
    private String userId;
    private Cliente cliente;
    private Database db = Database.getInstance();



    public VistaContactoPresenter(IVistaContacto iVistaContacto, String userId){
        this.iVistaContacto = iVistaContacto;
        this.userId = userId;
        obtenerCliente();
    }

    public void obtenerCliente(){
        db.obtenerCliente(userId, cliente -> {

            iVistaContacto.mostrarProgressBar();

            mostrarCliente(cliente);
        });
    }

    public void mostrarCliente(Cliente cliente){
        this.cliente = cliente;
        obtenerAutosCliente();
    }


    public void eliminarCliente(){
        db.borrarCliente(cliente);
    }

    public void subirFotoCliente(byte[] data){
        iVistaContacto.mostrarProgressBar();
        db.subirFotoCliente(cliente, data, this::obtenerCliente);
    }


    public void obtenerAutosCliente(){
        db.obtenerAutosCliente(cliente, this::mostrarAutos);
    }

    public void mostrarAutos(List<Auto>autos){
        if(autos!=null) {

            iVistaContacto.ocultarProgressBar();
            iVistaContacto.cargarCliente(cliente);
            iVistaContacto.inicializarAdaptador(iVistaContacto.crearAdaptador(autos));
            iVistaContacto.generarLayout();
        }

    }

    public void nuevoAuto(View v){

        Intent intent = new Intent(v.getContext(), NuevoAutoActivity.class);

        v.getContext().startActivity(intent);

    }






}
