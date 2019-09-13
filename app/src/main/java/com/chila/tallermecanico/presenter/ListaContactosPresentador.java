package com.chila.tallermecanico.presenter;


import com.chila.tallermecanico.Firestore.Database;
import com.chila.tallermecanico.Firestore.FirestoreCallbackClientes;
import com.chila.tallermecanico.model.Cliente;
import com.chila.tallermecanico.view.IListaContactos;


import java.util.List;

public class    ListaContactosPresentador implements IListaContactosPresentador{

    private IListaContactos iListacontactos;

    public ListaContactosPresentador(IListaContactos iListacontactos){
        this.iListacontactos=iListacontactos;
        obtenerClientes();
    }

    public void obtenerClientes() {
        Database db = Database.getInstance();
        db.getClientes(this::mostrarClientes);
    }

    public void mostrarClientes(List<Cliente> listaClientes) {
        iListacontactos.inicializarAdaptador(iListacontactos.crearAdaptador(listaClientes));
        iListacontactos.generarLayout();
    }
}
