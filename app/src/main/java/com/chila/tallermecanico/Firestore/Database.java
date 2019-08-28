package com.chila.tallermecanico.Firestore;


import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.chila.tallermecanico.model.Auto;
import com.chila.tallermecanico.model.Cliente;

import com.chila.tallermecanico.model.OrdenTrabajo;
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
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static android.content.ContentValues.TAG;


public class Database {
    private static final Database instance = new Database();


    private Cliente cliente = new Cliente();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseStorage storage = FirebaseStorage.getInstance();

    //REFERENCES
    private CollectionReference dbClientes = db.collection(ConstFirestore.CLIENTES_COLECCION);
    private CollectionReference dbUsuarios = db.collection(ConstFirestore.USUARIOS_COLECCION);
    private CollectionReference dbAutos = db.collection(ConstFirestore.AUTOS_COLECCION);
    private CollectionReference dbOrdenesTrabajo = db.collection(ConstFirestore.ORDENTRABAJO_COLECCION);

    //REFERENCES

    private Database() {

    }

    public static Database getInstance() {
        return instance;
    }


    //clientes
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
                            if (task.getResult() != null) {
                                DocumentSnapshot document = task.getResult();
                                cliente = document.toObject(Cliente.class);
                                cliente.setId(document.getId());
                                Log.d(TAG, document.getId() + " =>" + document.getData());
                                firestoreCallback.onCallBack(cliente);
                            }
                        } else {
                            Log.d(TAG, "No such document");
                        }
                    }
                });

    }

    public void getClientes(final FirestoreCallbackClientes firestoreCallbackClientes) {
        dbClientes.whereEqualTo("user", user.getUid()).get().addOnCompleteListener(task -> {

            if (!task.isSuccessful()) {
                Log.w(TAG, "Error getting documents.", task.getException());
                return;
            }

            if (task.getResult() == null)
                return;

            List<Cliente> clientes = new ArrayList<>();
            for (QueryDocumentSnapshot document : task.getResult()) {
                Log.d(TAG, document.getId() + " =>" + document.getData());
                Cliente cliente = document.toObject(Cliente.class);
                cliente.setId(document.getId());
                clientes.add(cliente);
            }

            firestoreCallbackClientes.onCallBack(clientes);
        });
    }

    public void actualizarCliente(final Cliente cliente) {
        dbClientes.document(cliente.getId()).set(cliente).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d(TAG, cliente.getId() + " => updated: " + cliente.toString());
            } else {
                Log.w(TAG, "Error actualizando cliente.");
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
        cliente.setUser(FirebaseAuth.getInstance().getUid()); //le agrego UID para lectura
        dbClientes
                .add(cliente)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Cliente creado con exito. " + cliente.toString());
                        } else {
                            Log.w(TAG, "Error al agregar cliente. ");
                        }
                    }
                });
    }

    public void subirFotoCliente(final Cliente cliente, byte[] data, final FirestoreCallbackFoto firestoreCallbackFoto) {
        String path = "imgClientes/" + UUID.randomUUID() + ".PNG";
        StorageReference imgClientesRef = storage.getReference(path);

        StorageMetadata metadata = new StorageMetadata.Builder()
                .setCustomMetadata("UserID", cliente.getId())
                .build();

        UploadTask uploadTask = imgClientesRef.putBytes(data, metadata);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                Task<Uri> downloadUrl = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                downloadUrl.addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        cliente.setFotoUrl(uri.toString());
                        actualizarCliente(cliente);
                        firestoreCallbackFoto.onCallBack();
                    }
                });
            }


        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //ERROR AL SUBIR

                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());

                    }
                });
    }

    public void obtenerFotoCliente() throws IOException {

        File localFile = File.createTempFile("images", "jpg");
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference riversRef = storageRef.child("images/rivers.jpg");
        riversRef.getFile(localFile)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        // Successfully downloaded data to local file
                        // ...
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle failed download
                // ...
            }
        });

    }

    //clientes

    //usuario
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
                            if (task.getResult() != null) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Usuario usuario = Usuario.getInstance();
                                    usuario = document.toObject(Usuario.class);
                                    Log.d(TAG, "usuario obtenido correctamente");
                                }
                            }
                        } else {
                            Log.d(TAG, "No such document");
                        }
                    }
                });


    }
    //usuario


    //autos
    public void agregarAuto(final Auto auto) {
        auto.setUsermAuth(FirebaseAuth.getInstance().getUid());
        dbAutos
                .add(auto)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Auto creado con exito. " + auto.toString());
                        } else {
                            Log.w(TAG, "Error al agregar cliente. ");
                        }
                    }
                });
    }

    public void obtenerAutosCliente(Cliente cliente, final FirestoreCallbackAuto firestoreCallbackAuto) {
        dbAutos
                .whereEqualTo("usermAuth", FirebaseAuth.getInstance().getUid())
                .whereEqualTo("cliente", cliente.getId())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<Auto> autos = new ArrayList<>();
                        if (task.getResult() != null) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Auto auto = document.toObject(Auto.class);
                                auto.setId(document.getId());
                                autos.add(auto);
                            }
                            firestoreCallbackAuto.onCallBack(autos);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, e);
                    }
                });


    }
    //autos


    //ordenes de trabajo

    public void nuevaOrdenTrabajo(final OrdenTrabajo ordenTrabajo) {
        ordenTrabajo.setUser(FirebaseAuth.getInstance().getUid());
        dbOrdenesTrabajo
                .add(ordenTrabajo)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Orden de trabajo creada con exito. " + ordenTrabajo.toString());
                        } else {
                            //error al cargar
                            Log.d(TAG, "Error al cargar orden de trabajo");
                        }
                    }
                });

    }

    public void obtenerOrdenesTrabajo(final FirestoreCallbackOrdenesTrabajo firestoreCallbackOrdenesTrabajo) {
        dbOrdenesTrabajo
                .whereEqualTo("user", FirebaseAuth.getInstance().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<OrdenTrabajo> ordenesTrabajo = new ArrayList<>();
                        if (task.getResult() != null) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                OrdenTrabajo ordenTrabajo = document.toObject(OrdenTrabajo.class);
                                ordenTrabajo.setId(document.getId());
                                ordenesTrabajo.add(ordenTrabajo);
                            }
                            firestoreCallbackOrdenesTrabajo.onCallback(ordenesTrabajo);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "error al obtener ordenes de trabajo");
                    }
                });

    }

    public void obtenerOrdenTrabajo(String id, final FiresotreCallbackOrdenTrabajo firesotreCallbackOrdenTrabajo) {
        dbClientes.document(id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult() != null) {
                                DocumentSnapshot document = task.getResult();
                                OrdenTrabajo ordenTrabajo = document.toObject(OrdenTrabajo.class);
                                ordenTrabajo.setId(document.getId());
                                Log.d(TAG, document.getId() + " =>" + document.getData());
                                firesotreCallbackOrdenTrabajo.onCallback(ordenTrabajo);
                            }
                        } else {
                            Log.d(TAG, "No such document");

                        }
                    }
                });
    }

    public void actualizarOrdenTrabajo(final OrdenTrabajo ordenTrabajo) {
        dbOrdenesTrabajo
                .document(cliente.getId())
                .set(ordenTrabajo)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, ordenTrabajo.getId() + " => actualizada");
                        } else {
                            Log.w(TAG, "Error actualizando orden trabajo");
                        }
                    }
                });
    }

    public void borrarOrdenTrabajo(final OrdenTrabajo ordenTrabajo) {
        dbOrdenesTrabajo
                .document(ordenTrabajo.getId())
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, ordenTrabajo.getId() + "=> Orden de trabajo eliminada con exito. " + ordenTrabajo.toString());
                        } else {
                            Log.w(TAG, "Error borrando cliente. " + ordenTrabajo.getId());
                        }
                    }
                });
    }

    //ordenes trabajo

}

