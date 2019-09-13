package com.chila.tallermecanico.Firestore;


import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.chila.tallermecanico.model.Auto;
import com.chila.tallermecanico.model.Cliente;

import com.chila.tallermecanico.model.OrdenServicio;
import com.chila.tallermecanico.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


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
                .addSnapshotListener((documentSnapshot, e) -> {
                    if (e != null) {
                        Log.w(TAG, "Listen failed.", e);
                        return;
                    }
                    if (documentSnapshot != null && documentSnapshot.exists()) {
                        Cliente cliente = documentSnapshot.toObject(Cliente.class);
                        cliente.setId(documentSnapshot.getId());
                        Log.d(TAG, documentSnapshot.getId() + " =>" + documentSnapshot.getData());
                        firestoreCallback.onCallBack(cliente);

                    }
                });
    }

    public void getClientes(final FirestoreCallbackClientes firestoreCallbackClientes) {
        dbClientes
                .whereEqualTo("user", user.getUid())
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (e != null) {
                        Log.w(TAG, "Listen failed.", e);
                        return;
                    }
                    List<Cliente> clientes = new ArrayList<>();
                    if (queryDocumentSnapshots != null) {
                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            if (doc.get("user") != null) {
                                Cliente cliente = doc.toObject(Cliente.class);
                                cliente.setId(doc.getId());
                                clientes.add(cliente);
                            }
                        }
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

    public void subirFotoCliente(final Cliente cliente, byte[] data,
                                 final FirestoreCallbackFoto firestoreCallbackFoto) {
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
        dbClientes.document(auto.getCliente().getId()).collection("autos").add(auto).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
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

    public void obtenerAutosCliente(Cliente cliente,
                                    final FirestoreCallbackAuto firestoreCallbackAuto) {
        dbClientes
                .document(cliente.getId())
                .collection("autos")
                .whereEqualTo("usermAuth", FirebaseAuth.getInstance().getUid())
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (e != null) {
                        Log.w(TAG, "error leyendo autos");
                    }
                    List<Auto> autos = new ArrayList<>();
                    if (queryDocumentSnapshots != null) {
                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            if (doc.get("usermAuth") != null) {
                                Auto auto = doc.toObject(Auto.class);
                                auto.setId(doc.getId());
                                autos.add(auto);
                            }
                        }
                    }
                    firestoreCallbackAuto.onCallBack(autos);
                });


    }

    public void obtenerAutoPatente(String patente,
                                    final FirestoreCallbackAutoPatente firestoreCallbackAutoPatente) {
        db.collectionGroup("autos")
                .whereEqualTo("patente", patente)
                .whereEqualTo("usermAuth", FirebaseAuth.getInstance().getUid())
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (e != null) {
                        Log.w(TAG, "error leyendo auto");
                    }
                    Auto auto = new Auto();
                    if (queryDocumentSnapshots != null) {
                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            if (doc.get("usermAuth") != null) {
                                auto = doc.toObject(Auto.class);
                                auto.setId(doc.getId());

                            }
                        }
                        firestoreCallbackAutoPatente.onCallBack(auto);

                    }
                });


    }



    //autos


    //ordenes de trabajo

    public void nuevaOrdenTrabajo(final OrdenServicio ordenServicio) {
        ordenServicio.setUser(FirebaseAuth.getInstance().getUid());
        dbOrdenesTrabajo
                .add(ordenServicio)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Orden de trabajo creada con exito. " + ordenServicio.toString());
                    } else {
                        //error al cargar
                        Log.d(TAG, "Error al cargar orden de trabajo");
                    }
                });

    }

    public void obtenerOrdenesServicio(
            final FirestoreCallbackOrdenesTrabajo firestoreCallbackOrdenesTrabajo) {
        dbOrdenesTrabajo
                .whereEqualTo("user", FirebaseAuth.getInstance().getUid())
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (e != null) {
                        Log.w(TAG, "Listen failed.", e);
                        return;
                    }
                    List<OrdenServicio> ordenesTrabajo = new ArrayList<>();
                    if (queryDocumentSnapshots != null) {
                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            OrdenServicio ordenServicio = doc.toObject(OrdenServicio.class);
                            ordenServicio.setId(doc.getId());
                            ordenesTrabajo.add(ordenServicio);
                        }
                        firestoreCallbackOrdenesTrabajo.onCallback(ordenesTrabajo);
                    }
                });
    }

    public void obtenerOrdenServicio(String id,final FiresotreCallbackOrdenTrabajo firesotreCallbackOrdenTrabajo) {
        dbOrdenesTrabajo.document(id)
                .addSnapshotListener((documentSnapshot, e) -> {
                    if(e!=null){
                        Log.w(TAG, "Listen failed.", e);
                        return;
                    }
                    if (documentSnapshot != null && documentSnapshot.exists()) {
                        //MutableLiveData<OrdenServicio> ordenServicioMutableLiveData = new MutableLiveData<>();
                        OrdenServicio ordenServicio = documentSnapshot.toObject(OrdenServicio.class);
                        ordenServicio.setId(documentSnapshot.getId());
                        Log.d(TAG, documentSnapshot.getId() + " =>" + documentSnapshot.getData());
                        //ordenServicioMutableLiveData.setValue(ordenServicio);
                        firesotreCallbackOrdenTrabajo.onCallback(ordenServicio);
                    }
                });


    }



    public void actualizarOrdenTrabajo(final OrdenServicio ordenServicio) {
        dbOrdenesTrabajo
                .document(cliente.getId())
                .set(ordenServicio)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, ordenServicio.getId() + " => actualizada");
                        } else {
                            Log.w(TAG, "Error actualizando orden trabajo");
                        }
                    }
                });
    }

    public void borrarOrdenTrabajo(final OrdenServicio ordenServicio) {
        dbOrdenesTrabajo
                .document(ordenServicio.getId())
                .delete()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, ordenServicio.getId() + "=> Orden de trabajo eliminada con exito. " + ordenServicio.toString());
                    } else {
                        Log.w(TAG, "Error borrando cliente. " + ordenServicio.getId());
                    }
                });
    }

    //ordenes trabajo

}

