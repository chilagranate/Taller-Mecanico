package com.chila.tallermecanico.presenter;

import com.chila.tallermecanico.Firestore.Database;

import com.chila.tallermecanico.model.OrdenServicio;
import com.chila.tallermecanico.view.IVistaOrdenTrabajoActivity;

public class VistaOrdenServicioPresentador implements IVistaOrdenServicioPresentador {

    private IVistaOrdenTrabajoActivity iVistaOrdenTrabajoActivity;
    private OrdenServicio ordenServicio;


    public VistaOrdenServicioPresentador(IVistaOrdenTrabajoActivity iVistaOrdenTrabajoActivity, String idOT) {
        this.iVistaOrdenTrabajoActivity = iVistaOrdenTrabajoActivity;
        obtenerOT(idOT);
    }

    private void obtenerOT(String idOt){
        //Database db = Database.getInstance();
        //db.obtenerOrdenServicio(idOt, this::mostrarOT);
    }

    private void mostrarOT(OrdenServicio ordenServicio){
        iVistaOrdenTrabajoActivity.mostrarOT(ordenServicio);
    }
}
