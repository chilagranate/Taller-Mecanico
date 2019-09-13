package com.chila.tallermecanico.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;


import com.chila.tallermecanico.R;
import com.chila.tallermecanico.adapter.ClienteAdaptador;
import com.chila.tallermecanico.model.Cliente;
import com.chila.tallermecanico.presenter.IListaContactosPresentador;
import com.chila.tallermecanico.presenter.ListaContactosPresentador;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ListaContactos extends AppCompatActivity implements IListaContactos {

    private IListaContactosPresentador presentador;

    private ProgressBar progressBar;

    @BindView(R.id.contactos_recyclerview)
    RecyclerView RvClientes;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_contactos);
        ButterKnife.bind(this);


        //RvClientes.setHasFixedSize(true);
        RvClientes = findViewById(R.id.contactos_recyclerview);
        progressBar = findViewById(R.id.progressbar);

        fab.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), NuevoCliente.class);
            startActivity(intent);
        });

        presentador = new ListaContactosPresentador(this);

    }

    public void inicializarAdaptador(ClienteAdaptador adaptador) {
        RvClientes.setAdapter(adaptador);
    }

    public void generarLayout() {
        RvClientes.setLayoutManager(new LinearLayoutManager(this));
    }

    public ClienteAdaptador crearAdaptador(List<Cliente> clientes) {
        return new ClienteAdaptador(clientes, this);
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        switch (id) {
            case R.id.clientes_actualizar:
                presentador.obtenerClientes();
                //adapter.notifyDataSetChanged();
                break;
            case 0:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        //adapter.notifyDataSetChanged();
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_clientes, menu);

        return super.onCreateOptionsMenu(menu);
    }




}
