package com.chila.tallermecanico.view;

import android.content.Intent;
import android.os.Bundle;

import com.chila.tallermecanico.Firestore.Database;
import com.chila.tallermecanico.R;
import com.chila.tallermecanico.adapter.OrdenTrabajoAdapter;
import com.chila.tallermecanico.model.OrdenServicio;
import com.chila.tallermecanico.model.Usuario;
import com.chila.tallermecanico.presenter.IMainActivityPresenter;
import com.chila.tallermecanico.presenter.MainActivityPresenter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, IMainActivity  {
    private FirebaseAuth mAuth;
    private IMainActivityPresenter presenter;

    @BindView(R.id.ordenes_trabajo_rv)
    RecyclerView rvOrdenesTrabajo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        presenter = new MainActivityPresenter(this);


        checkmAuthUser();


        FloatingActionButton fab = findViewById(R.id.main_fab);
        fab.setOnClickListener(view -> startActivity(
                new Intent(MainActivity.this, NuevaOrdenTrabajoActivity.class)));

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.obtenerOrdenesTrabajo();
    }

    private void checkmAuthUser() {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser ==null){
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
        }
        Database db = Database.getInstance();
        db.obtenerUsuario();
        Usuario usuario=Usuario.getInstance();
        //agregarAutos();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case 0:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")



    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch(id) {

            case R.id.nav_clientes:
                Intent intent1 = new Intent(this, ListaContactos.class);
                startActivity(intent1);

                break;
            case R.id.nav_cerrar_sesion:
                mAuth.signOut();
                Intent intent2 = new Intent(this, LoginActivity.class);
                startActivity(intent2);
                finish();

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void inicializarAdaptador(OrdenTrabajoAdapter adaptador) {
        rvOrdenesTrabajo.setAdapter(adaptador);
    }

    public void generarLayout() {
        rvOrdenesTrabajo.setLayoutManager(new LinearLayoutManager(this));
    }

    public OrdenTrabajoAdapter crearAdaptador(List<OrdenServicio> ordenesTrabajo) {
        return new OrdenTrabajoAdapter(ordenesTrabajo, this);
    }




}
