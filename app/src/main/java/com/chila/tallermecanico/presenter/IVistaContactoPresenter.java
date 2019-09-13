package com.chila.tallermecanico.presenter;

import android.net.Uri;
import android.view.View;

import com.chila.tallermecanico.model.Auto;
import com.chila.tallermecanico.model.Cliente;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public interface IVistaContactoPresenter {

    void obtenerCliente();

    void mostrarCliente(Cliente cliente);

    void eliminarCliente();

    void subirFotoCliente(byte[] data);

    void obtenerAutosCliente();

    void mostrarAutos(List<Auto> autos);

    void nuevoAuto(View v);

}
