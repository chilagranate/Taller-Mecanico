package com.chila.tallermecanico.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.chila.tallermecanico.Firestore.Database;
import com.chila.tallermecanico.Firestore.FirestoreCallbackCliente;
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
    private Cliente cliente;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_cliente);
        final Bundle parametros = getIntent().getExtras();
        idCliente = parametros.getString("id");
        inicializarView();
        obtenerCliente();
    }



    public void obtenerCliente(){
        Database db = Database.getInstance();
        db.obtenerCliente(idCliente, this::mostrarCliente);
    }

    private void mostrarCliente(Cliente cliente){
        this.cliente = cliente;
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

    public void guardarCliente(){
        cliente.setNombre(tvNombre.getText().toString());
        cliente.setApellido(tvApellido.getText().toString());
        cliente.setDireccion(tvDireccion.getText().toString());
        cliente.setDni(tvDni.getText().toString());
        cliente.setEmail(tvEmail.getText().toString());
        cliente.setTelefono(tvTelefono.getText().toString());
        Database db = Database.getInstance();
        db.actualizarCliente(cliente);
        finish();

    }


    //OPCIONES DEL MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editar_cliente, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.editar_contacto:
                guardarCliente();
                finish();
                break;

        }
        return true;
    }


}



