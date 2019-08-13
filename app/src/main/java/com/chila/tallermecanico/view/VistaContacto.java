package com.chila.tallermecanico.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import com.chila.tallermecanico.R;
import com.chila.tallermecanico.model.Cliente;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mikhaellopez.circularimageview.CircularImageView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Menu;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

public class VistaContacto extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private String idCliente;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ProgressBar progressBar;

    private TextView tvNombre, tvTelefono, tvEmail, tvDireccion, tvDni;
    private CircularImageView foto;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_contacto);
        final Bundle parametros = getIntent().getExtras();
        idCliente = parametros.getString("id");
        obtenerCliente(idCliente);

        progressBar = findViewById(R.id.progressbar);

        tvNombre    = findViewById(R.id.vista_contacto_nombre);
        tvDireccion = findViewById(R.id.vista_contacto_direccion);
        tvDni       = findViewById(R.id.vista_contacto_dni);
        tvEmail     = findViewById(R.id.vista_contacto_email);
        tvTelefono  = findViewById(R.id.vista_contacto_telefono);
        foto        = findViewById(R.id.vista_contacto_foto);
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
        tvNombre.setText(getString(R.string.nombre_y_apellido,cliente.getNombre(), cliente.getApellido()));
        tvTelefono.setText(cliente.getTelefono());
        tvEmail.setText(cliente.getEmail());
        tvDni.setText(cliente.getDni());
        tvDireccion.setText(cliente.getDireccion());
        foto.setImageResource(cliente.getFoto());

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch(id) {

            case R.id.cliente_editar:
                Toast toast1 = Toast.makeText(getApplicationContext(),"editar",Toast.LENGTH_SHORT);
                toast1.show();

                break;
            case R.id.cliente_eliminar:
                Toast toast2 = Toast.makeText(getApplicationContext(),"eliminar",Toast.LENGTH_SHORT);
                toast2.show();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
