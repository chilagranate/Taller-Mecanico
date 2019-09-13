package com.chila.tallermecanico.viewmodel;

import android.provider.ContactsContract;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.chila.tallermecanico.Firestore.Database;
import com.chila.tallermecanico.Firestore.FirestoreCallbackAuto;
import com.chila.tallermecanico.Firestore.FirestoreCallbackAutoPatente;
import com.chila.tallermecanico.model.Auto;

import java.util.List;

public class VistaAutoViewModel extends ViewModel {

    private MutableLiveData<Auto> auto = new MutableLiveData<>();

    public void init(String patente){
        Database db = Database.getInstance();
        db.obtenerAutoPatente(patente, this::setAuto);
    }

    private void setAuto(Auto auto){
        this.auto.postValue(auto);

    }

    public MutableLiveData<Auto> getAuto(){
        return auto;
    }
}
