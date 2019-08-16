package com.chila.tallermecanico.presenter;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.chila.tallermecanico.Firestore.Database;
import com.chila.tallermecanico.Firestore.FirestoreCallbackCliente;
import com.chila.tallermecanico.model.Cliente;
import com.chila.tallermecanico.view.IVistaContacto;
import com.chila.tallermecanico.view.VistaContacto;
import com.google.firebase.storage.StorageReference;

public class VistaContactoPresenter implements IVistaContactoPresenter  {

    private IVistaContacto iVistaContacto;
    private String userId;
    private Cliente cliente;
    private Database db = Database.getInstance();
    private Context context;


    public VistaContactoPresenter(IVistaContacto iVistaContacto, String userId, Context context){
        this.iVistaContacto = iVistaContacto;
        this.userId = userId;
        this.context = context;
        obtenerCliente();
    }

    public void obtenerCliente(){
        db.obtenerCliente(userId, new FirestoreCallbackCliente() {
            @Override
            public void onCallBack(Cliente cliente) {

                mostrarCliente(cliente);
            }
        });
    }

    public void mostrarCliente(Cliente cliente){
        this.cliente = cliente;
        iVistaContacto.cargarCliente(cliente);
    }



    public void eliminarCliente(){
        db.borrarCliente(cliente);
    }

    public void subirFotoCliente(Uri uri, StorageReference imageRef){
        db.subirFotoCliente(cliente, uri, imageRef);

    }






}
