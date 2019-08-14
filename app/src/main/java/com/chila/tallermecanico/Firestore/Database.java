package com.chila.tallermecanico.Firestore;


import android.util.Log;

import androidx.annotation.NonNull;

import com.chila.tallermecanico.R;
import com.chila.tallermecanico.model.Cliente;

import com.chila.tallermecanico.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
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
    //REFERENCES
    private CollectionReference dbClientes = db.collection(ConstFirestore.CLIENTES_COLECCION);
    private CollectionReference dbUsuarios = db.collection(ConstFirestore.USUARIOS_COLECCION);

    //REFERENCES

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
                            cliente.setId(document.getId());
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

    public void actualizarCliente(final Cliente cliente) {
        dbClientes
                .document(cliente.getId())
                .set(cliente)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, cliente.getId() + " => updated: " + cliente.toString());
                        } else {
                            Log.w(TAG, "Error getting document.");
                        }
                    }
                });
    }

    public void borrarCliente(final Cliente cliente) {
        dbClientes
                .document(cliente.getId())
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, cliente.getId() + "=> Cliente eliminado con exito. " + cliente.toString());
                        } else {
                            Log.w(TAG, "Error borrando cliente. " + cliente.getId());
                        }
                    }
                });
    }

    public void agregarCliente(final Cliente cliente) {
        dbClientes
                .add(cliente)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Cliente creado con exioto. " + cliente.toString());
                        } else {
                            Log.w(TAG, "Error al agregar cliente. ");
                        }
                    }
                });
    }

    public void crearUsuario(final Usuario user) {
        dbUsuarios
                .add(user)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Log.d(TAG, "Usuario creado con exito.");
                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error creando usuario", e);
                    }
                });

    }

    public void obtenerUsuario() {

        dbUsuarios
                .whereEqualTo("uid", user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Usuario usuario = Usuario.getInstance();
                                usuario=document.toObject(Usuario.class);
                                Log.d(TAG, "usuario obtenido correctamente");
                            }
                        } else {
                            Log.d(TAG, "No such document");
                        }
                    }
                });




    }


}

