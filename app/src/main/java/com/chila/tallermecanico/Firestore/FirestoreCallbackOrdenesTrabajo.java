package com.chila.tallermecanico.Firestore;

import com.chila.tallermecanico.model.OrdenServicio;

import java.util.List;

public interface FirestoreCallbackOrdenesTrabajo {

    void onCallback(List<OrdenServicio> OrdenesTrabajo);
}
