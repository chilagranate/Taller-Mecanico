package com.chila.tallermecanico.presenter;

import com.chila.tallermecanico.Firestore.Database;
import com.chila.tallermecanico.model.OrdenServicio;
import com.chila.tallermecanico.view.IMainActivity;

import java.util.List;

public class MainActivityPresenter implements IMainActivityPresenter {

    private IMainActivity iMainActivity;

    public MainActivityPresenter(IMainActivity iMainActivity) {
        this.iMainActivity = iMainActivity;
        obtenerOrdenesTrabajo();
    }

    public void obtenerOrdenesTrabajo() {
        Database db = Database.getInstance();
        db.obtenerOrdenesServicio(this::mostrarOrdenesTrabajo);
    }

    private void mostrarOrdenesTrabajo(List<OrdenServicio> ordenesTrabajo) {
        iMainActivity.inicializarAdaptador(iMainActivity.crearAdaptador(ordenesTrabajo));
        iMainActivity.generarLayout();
    }
}
