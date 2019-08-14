package com.chila.tallermecanico.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;



import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;


import com.chila.tallermecanico.Firestore.Database;
import com.chila.tallermecanico.R;
import com.chila.tallermecanico.model.Cliente;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;





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
                finish();
                break;

        }
        return true;
    }

    private Cliente crearCliente(){
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


        Cliente cliente = new Cliente(nombre, apellido, dni, telefono, email, direccion);
        cliente.setFoto(R.drawable.ic_persona);
        cliente.setUser(FirebaseAuth.getInstance().getUid()); //le agrego UID para lectura
        return cliente;

    }



    private void guardarContacto() {

        Database db = Database.getInstance();
        db.agregarCliente(crearCliente());
    }



}



