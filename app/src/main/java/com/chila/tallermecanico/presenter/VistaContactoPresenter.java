package com.chila.tallermecanico.presenter;

import com.chila.tallermecanico.Firestore.Database;
import com.chila.tallermecanico.Firestore.FirestoreCallbackCliente;
import com.chila.tallermecanico.model.Cliente;
import com.chila.tallermecanico.view.IVistaContacto;

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
        db.obtenerCliente(userId, new FirestoreCallbackCliente() {
            @Override
            public void onCallBack(Cliente cliente) {

                mostrarCliente(cliente);
            }
        });
    }

    public void mostrarCliente(Cliente cliente){
        this.cliente = cliente;
        iVistaContacto.cargarCliente(cliente);
    }

    public void eliminarCliente(){
        db.borrarCliente(cliente);





    }





}
