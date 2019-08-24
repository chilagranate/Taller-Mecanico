package com.chila.tallermecanico.presenter;

import com.chila.tallermecanico.Firestore.Database;
import com.chila.tallermecanico.Firestore.FirestoreCallbackAuto;
import com.chila.tallermecanico.Firestore.FirestoreCallbackCliente;
import com.chila.tallermecanico.Firestore.FirestoreCallbackFoto;
import com.chila.tallermecanico.model.Auto;
import com.chila.tallermecanico.model.Cliente;
import com.chila.tallermecanico.view.IVistaContacto;

import java.util.List;

public class VistaContactoPresenter implements IVistaContactoPresenter  {

    private IVistaContacto iVistaContacto;
    private String userId;
    private Cliente cliente;
    private Database db = Database.getInstance();
    private List<Auto> listaAutos;


    public VistaContactoPresenter(IVistaContacto iVistaContacto, String userId){
        this.iVistaContacto = iVistaContacto;
        this.userId = userId;
        obtenerCliente();
    }

    public void obtenerCliente(){
        db.obtenerCliente(userId, new FirestoreCallbackCliente() {
            @Override
            public void onCallBack(Cliente cliente) {

                iVistaContacto.mostrarProgressBar();

                mostrarCliente(cliente);
            }
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
        db.subirFotoCliente(cliente, data, new FirestoreCallbackFoto() {
                    @Override
                    public void onCallBack() {
                        obtenerCliente();
                    }
                });
    }


    public void obtenerAutosCliente(){
        Database db = Database.getInstance();
        db.obtenerAutosCliente(cliente, new FirestoreCallbackAuto() {
            @Override
            public void onCallBack(List<Auto> autos) {

                mostrarAutos(autos);
            }
        });
    }

    public void mostrarAutos(List<Auto>autos){
        this.listaAutos = autos;
        iVistaContacto.ocultarProgressBar();
        iVistaContacto.cargarCliente(cliente);
        iVistaContacto.inicializarAdaptador(iVistaContacto.crearAdaptador(listaAutos));
        iVistaContacto.generarLayout();

    }




}
