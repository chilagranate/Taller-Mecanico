package com.chila.tallermecanico.presenter;

import com.chila.tallermecanico.Firestore.Database;
import com.chila.tallermecanico.Firestore.FirestoreCallbackCliente;
import com.chila.tallermecanico.model.Cliente;
import com.chila.tallermecanico.view.IVistaContacto;

public class VistaContactoPresenter implements IVistaContactoPresenter  {

    private IVistaContacto iVistaContacto;
    private String userId;

    public VistaContactoPresenter(IVistaContacto iVistaContacto, String userId){
        this.iVistaContacto = iVistaContacto;
        this.userId = userId;
        obtenerCliente();
    }

    public void obtenerCliente(){
        Database db = Database.getInstance();
        db.obtenerCliente(userId, new FirestoreCallbackCliente() {
            @Override
            public void onCallBack(Cliente cliente) {
                mostrarCliente(cliente);
            }
        });

    }

    public void mostrarCliente(Cliente cliente){
        iVistaContacto.cargarCliente(cliente);
    }





}
