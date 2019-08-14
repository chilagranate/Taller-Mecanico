package com.chila.tallermecanico.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import com.chila.tallermecanico.Firestore.Database;
import com.chila.tallermecanico.Firestore.FirestoreCallbackCliente;
import com.chila.tallermecanico.R;
import com.chila.tallermecanico.model.Cliente;


import com.chila.tallermecanico.presenter.VistaContactoPresenter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mikhaellopez.circularimageview.CircularImageView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;

import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;



public class VistaContacto extends AppCompatActivity implements IVistaContacto{

    private String idCliente;
    private ProgressBar progressBar;
    private TextView tvNombre, tvTelefono, tvEmail, tvDireccion, tvDni;
    private CircularImageView foto;
    private VistaContactoPresenter presentador;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_contacto);
        referenciarView();

        final Bundle parametros = getIntent().getExtras(); //Recupero el id de cliente
        if (parametros != null) {
            idCliente = parametros.getString("id");
            presentador = new VistaContactoPresenter(this,idCliente);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        presentador.obtenerCliente();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vista_cliente, menu);
        return super.onCreateOptionsMenu(menu);
    }


    public void cargarCliente(Cliente cliente) {
        tvNombre.setText(getString(R.string.nombre_y_apellido, cliente.getNombre(), cliente.getApellido()));
        tvTelefono.setText(cliente.getTelefono());
        tvEmail.setText(cliente.getEmail());
        tvDni.setText(cliente.getDni());
        tvDireccion.setText(cliente.getDireccion());
        foto.setImageResource(cliente.getFoto());
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.cliente_editar:
                Intent intent = new Intent(this, EditarCliente.class);
                intent.putExtra("id", idCliente);
                startActivity(intent);
                break;
            case R.id.cliente_eliminar:
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                presentador.eliminarCliente();
                                finish();

                                //Yes button clicked
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder .setMessage(getString(R.string.alert_dialog_eliminar_cliente))
                        .setPositiveButton(getString(R.string.alert_dialog_eliminar_cliente_si), dialogClickListener)
                        .setNegativeButton(getString(R.string.alert_dialog_eliminar_cliente_no), dialogClickListener).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void referenciarView(){

        progressBar = findViewById(R.id.vista_contacto_progressbar);
        tvNombre = findViewById(R.id.vista_contacto_nombre);
        tvDireccion = findViewById(R.id.vista_contacto_direccion);
        tvDni = findViewById(R.id.vista_contacto_dni);
        tvEmail = findViewById(R.id.vista_contacto_email);
        tvTelefono = findViewById(R.id.vista_contacto_telefono);
        foto = findViewById(R.id.vista_contacto_foto);
    }


}
