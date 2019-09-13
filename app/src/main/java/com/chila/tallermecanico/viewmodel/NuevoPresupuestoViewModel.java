package com.chila.tallermecanico.viewmodel;

import com.chila.tallermecanico.Firestore.Database;
import com.chila.tallermecanico.Firestore.FiresotreCallbackOrdenTrabajo;
import com.chila.tallermecanico.model.OrdenServicio;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Created by Facundo A. Paredes on 13/9/2019.
 */
public class NuevoPresupuestoViewModel extends ViewModel {

    private MutableLiveData<OrdenServicio> ordenServicioMutableLiveData = new MutableLiveData<>();

    public void init(String idOrdenServicio){
        Database db = Database.getInstance();
        db.obtenerOrdenServicio(idOrdenServicio, new FiresotreCallbackOrdenTrabajo() {
            @Override
            public void onCallback(OrdenServicio ordenServicio) {
                setOrdenServicio(ordenServicio);
            }
        });
    }

    private void setOrdenServicio(OrdenServicio ordenServicio){
        ordenServicioMutableLiveData.postValue(ordenServicio);
    }

    public LiveData<OrdenServicio> getOrdenServicio(){
        return ordenServicioMutableLiveData;
    }
}
