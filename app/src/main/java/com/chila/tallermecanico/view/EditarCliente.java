package com.chila.tallermecanico.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chila.tallermecanico.R;
import com.chila.tallermecanico.model.Cliente;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.content.ContentValues.TAG;

public class EditarCliente extends AppCompatActivity {
    private String idCliente;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private TextView tvNombre, tvApellido, tvTelefono, tvEmail, tvDireccion, tvDni;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_cliente);
        final Bundle parametros = getIntent().getExtras();
        idCliente = parametros.getString("id");
        inicializarView();
        obtenerCliente(idCliente);
    }

    private void obtenerCliente(String id) {

        DocumentReference docRef = db.collection("clientes").document(id);
        docRef
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Cliente cliente = document.toObject(Cliente.class);
                                Log.d(TAG, document.getId() + " =>" + document.getData());
                                cargarCliente(cliente);
                            } else {
                                Log.d(TAG, "No such document");
                            }

                        }




                    }
                });
    }

    private void cargarCliente(Cliente cliente){
        tvNombre.setText(cliente.getNombre());
        tvApellido.setText(cliente.getApellido());
        tvTelefono.setText(cliente.getTelefono());
        tvEmail.setText(cliente.getEmail());
        tvDni.setText(cliente.getDni());
        tvDireccion.setText(cliente.getDireccion());
  

    }

    private void inicializarView(){
        tvNombre    = findViewById(R.id.edit_contacto_nombre);
        tvApellido   =findViewById(R.id.edit_contacto_apellido);
        tvDireccion = findViewById(R.id.edit_contacto_direccion);
        tvDni       = findViewById(R.id.edit_contacto_dni);
        tvEmail     = findViewById(R.id.edit_contacto_email);
        tvTelefono  = findViewById(R.id.edit_contacto_telefono);

    }


}



