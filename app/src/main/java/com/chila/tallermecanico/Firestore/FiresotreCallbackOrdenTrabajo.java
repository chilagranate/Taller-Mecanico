package com.chila.tallermecanico.Firestore;

import androidx.lifecycle.MutableLiveData;

import com.chila.tallermecanico.model.OrdenServicio;

public interface FiresotreCallbackOrdenTrabajo {
    void onCallback(OrdenServicio ordenServicio);
}
