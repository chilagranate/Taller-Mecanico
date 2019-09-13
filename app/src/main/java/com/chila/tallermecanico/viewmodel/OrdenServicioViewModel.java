package com.chila.tallermecanico.viewmodel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.chila.tallermecanico.Firestore.Database;
import com.chila.tallermecanico.Firestore.FiresotreCallbackOrdenTrabajo;
import com.chila.tallermecanico.model.OrdenServicio;

public class OrdenServicioViewModel extends ViewModel {
    //private OrdenServicio ordenServicio;
    private MutableLiveData<OrdenServicio> ordenServicio = new MutableLiveData<>();

    public void init(String id) {

        Database db = Database.getInstance();
        db.obtenerOrdenServicio(id, this::setOrdenServicio);


    }

    private void setOrdenServicio(OrdenServicio ordenServicio) {

        this.ordenServicio.postValue(ordenServicio);
    }

    public MutableLiveData<OrdenServicio> getOrdenServicio() {
        return ordenServicio;
    }




}
