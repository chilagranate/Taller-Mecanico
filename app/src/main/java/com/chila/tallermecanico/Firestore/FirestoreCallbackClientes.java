package com.chila.tallermecanico.Firestore;

import com.chila.tallermecanico.model.Cliente;

import java.util.List;

public interface FirestoreCallbackClientes {

    void onCallBack(List<Cliente> clientes);
}
