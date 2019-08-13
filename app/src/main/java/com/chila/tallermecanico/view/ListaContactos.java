package com.chila.tallermecanico.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;


import com.chila.tallermecanico.R;
import com.chila.tallermecanico.adapter.ClienteAdaptador;
import com.chila.tallermecanico.model.Cliente;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class ListaContactos extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private RecyclerView listaClientes;

    private Activity activity;
    private ClienteAdaptador adapter;
    private List<Cliente> clientes;
    private ProgressBar progressBar;
    LocalActivityManager mlam = new LocalActivityManager(this, true);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_contactos);

        listaClientes = findViewById(R.id.contactos_recyclerview);
        listaClientes.setHasFixedSize(true);
        listaClientes.setLayoutManager(new LinearLayoutManager(this));

        progressBar = findViewById(R.id.progressbar);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), NuevoCliente.class);
                startActivity(intent);
            }
        });

        clientes = new ArrayList<>();
        adapter = new ClienteAdaptador(clientes, this);
        listaClientes.setAdapter(adapter);

        activity = this;
        obtenerClientes();
    }


    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {

            case R.id.clientes_actualizar:
                obtenerClientes();
                adapter.notifyDataSetChanged();
                break;
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }





    @Override
    protected void onStart() {
        adapter.notifyDataSetChanged();
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_clientes, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();


    }

    public void obtenerClientes() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        db.collection("clientes").whereEqualTo("user", user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override

                    public void onComplete(Task<QuerySnapshot> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " =>" + document.getData());
                                Cliente cliente = document.toObject(Cliente.class);
                                cliente.setId(document.getId());
                                cliente.setFoto(R.drawable.ic_persona);
                                ListaContactos.this.clientes.add(cliente);
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                        adapter.notifyDataSetChanged();

                    }

                });


    }




}
