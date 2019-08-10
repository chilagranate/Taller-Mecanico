package com.chila.tallermecanico.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.chila.tallermecanico.Firestore.ConstFirestore;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.Toast;


import com.chila.tallermecanico.Firestore.Firestore;
import com.chila.tallermecanico.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;


import java.util.HashMap;
import java.util.Map;

public class NuevoCliente extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_cliente);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_nuevo_contacto, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.contacto_nuevo_guardar:
                guardarContacto();
                break;

        }
        return true;
    }


    private void guardarContacto() {

        TextInputEditText contactoNombre = findViewById(R.id.nuevo_contacto_nombre);
        TextInputEditText contactoApellido = findViewById(R.id.nuevo_contacto_apellido);
        TextInputEditText contactoDni = findViewById(R.id.nuevo_contacto_dni);
        TextInputEditText contactoEmail = findViewById(R.id.nuevo_contacto_email);
        TextInputEditText contactoDireccion = findViewById(R.id.nuevo_contacto_direccion);
        TextInputEditText contactoTelefono = findViewById(R.id.nuevo_contacto_telefono);

        String nombre = contactoNombre.getText().toString();
        String apellido = contactoApellido.getText().toString();
        String dni = contactoDni.getText().toString();
        String email = contactoEmail.getText().toString();
        String direccion = contactoDireccion.getText().toString();
        String telefono = contactoTelefono.getText().toString();


        Map<String, Object> cliente = new HashMap<>();
        cliente.put(ConstFirestore.CLIENTES_NOMBRE, nombre);
        cliente.put(ConstFirestore.CLIENTES_APELLIDO, apellido);
        cliente.put(ConstFirestore.CLIENTES_DNI, dni);
        cliente.put(ConstFirestore.CLIENTES_EMAIL, email);
        cliente.put(ConstFirestore.CLIENTES_DIRECCION, direccion);
        cliente.put(ConstFirestore.CLIENTES_TELEFONOS, telefono);

        Firestore db = new Firestore();
        db.insertarContacto(cliente);




// Add a new document with a generated ID


    }

}

