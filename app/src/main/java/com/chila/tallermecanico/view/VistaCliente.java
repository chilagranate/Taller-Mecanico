package com.chila.tallermecanico.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.chila.tallermecanico.R;
import com.chila.tallermecanico.adapter.AutoAdaptador;
import com.chila.tallermecanico.model.Auto;
import com.chila.tallermecanico.model.Cliente;


import com.chila.tallermecanico.presenter.VistaContactoPresenter;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;


public class VistaCliente extends AppCompatActivity implements IVistaContacto {
    private static final int PICK_IMAGE_REQUEST = 1;

    private String idCliente;
    private ProgressBar progressBar;
    private TextView tvNombre, tvTelefono, tvEmail, tvDireccion, tvDni;
    private CircularImageView foto;
    private VistaContactoPresenter presentador;
    private Uri mImageUri;
    private ImageView nuevoAuto;

    private RecyclerView rvAutos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_cliente);
        referenciarView();

        final Bundle parametros = getIntent().getExtras(); //Recupero el id de cliente
        if (parametros != null) {
            idCliente = parametros.getString("id");
            presentador = new VistaContactoPresenter(this, idCliente);
        }

        foto.setOnClickListener(v -> abrirSelectorImagen());

        nuevoAuto.setOnClickListener(v -> {

            Intent intent = new Intent(v.getContext(), NuevoAutoActivity.class);
            intent.putExtra("id", idCliente);
            startActivity(intent);

        });


    }

    public void inicializarAdaptador(AutoAdaptador adaptador) {
        rvAutos.setAdapter(adaptador);
    }

    public void generarLayout() {
        rvAutos.setLayoutManager(new LinearLayoutManager(this));
    }

    public AutoAdaptador crearAdaptador(List<Auto> autos) {
        return new AutoAdaptador(autos, this);
    }

    protected void abrirSelectorImagen() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mImageUri);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] img = baos.toByteArray();
                subirFoto(img);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
        if (cliente.getFotoUrl() != null)
            Picasso.with(this).load(Uri.parse(cliente.getFotoUrl())).into(foto);
    }

    public boolean onOptionsItemSelected(MenuItem item) {

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
                        switch (which) {
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
                builder.setMessage(getString(R.string.alert_dialog_eliminar_cliente))
                        .setPositiveButton(getString(R.string.alert_dialog_eliminar_cliente_si), dialogClickListener)
                        .setNegativeButton(getString(R.string.alert_dialog_eliminar_cliente_no), dialogClickListener).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void referenciarView() {

        progressBar = findViewById(R.id.vista_contacto_progressbar);
        tvNombre = findViewById(R.id.vista_contacto_nombre);
        tvDireccion = findViewById(R.id.vista_contacto_direccion);
        tvDni = findViewById(R.id.vista_contacto_dni);
        tvEmail = findViewById(R.id.vista_contacto_email);
        tvTelefono = findViewById(R.id.vista_contacto_telefono);
        foto = findViewById(R.id.vista_contacto_foto);
        rvAutos = findViewById(R.id.rv_autos);
        nuevoAuto = findViewById(R.id.vista_contacto_agregar_auto);
    }

    public void mostrarProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void ocultarProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    public void subirFoto(byte[] data) {
        if (data != null) {
            presentador.subirFotoCliente(data);
        } else {
            Toast.makeText(this, "No selecciono foto", Toast.LENGTH_LONG).show();
        }
    }
}
