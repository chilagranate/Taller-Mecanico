package com.chila.tallermecanico.Firestore;


import android.util.Log;

import androidx.annotation.NonNull;

import com.chila.tallermecanico.R;
import com.chila.tallermecanico.model.Cliente;

import com.google.android.gms.tasks.OnCompleteListener;


import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


public class Database {
    private static final Database instance = new Database();
    private Cliente cliente = new Cliente();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference dbClientes = db.collection(ConstFirestore.CLIENTES_COLECCION);


    private Database() {

    }

    public static Database getInstance() {
        return instance;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }


    public void obtenerCliente(String id, final FirestoreCallbackCliente firestoreCallback) {
        dbClientes.document(id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            cliente = document.toObject(Cliente.class);
                            Log.d(TAG, document.getId() + " =>" + document.getData());
                            firestoreCallback.onCallBack(cliente);

                        } else {
                            Log.d(TAG, "No such document");

                        }
                    }
                });

    }

    public void obtenerClientes(final FirestoreCallbackClientes firestoreCallbackClientes) {

        dbClientes
                .whereEqualTo("user", user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Cliente> clientes = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " =>" + document.getData());
                                Cliente cliente = document.toObject(Cliente.class);
                                cliente.setId(document.getId());
                                cliente.setFoto(R.drawable.ic_persona);
                                clientes.add(cliente);


                            }
                            firestoreCallbackClientes.onCallBack(clientes);
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }

                });
    }


}

