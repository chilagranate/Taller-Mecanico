package com.chila.tallermecanico.Firestore;

import com.chila.tallermecanico.model.OrdenTrabajo;

import java.util.List;

public interface FirestoreCallbackOrdenesTrabajo {

    void onCallback(List<OrdenTrabajo> OrdenesTrabajo);
}
