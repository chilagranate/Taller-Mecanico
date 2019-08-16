package com.chila.tallermecanico.presenter;

import android.net.Uri;

import com.chila.tallermecanico.model.Cliente;
import com.google.firebase.storage.StorageReference;

public interface IVistaContactoPresenter {

    void obtenerCliente();

    void mostrarCliente(Cliente cliente);

    void eliminarCliente();

    void subirFotoCliente(Uri uri, StorageReference imageRef);



    }
