package com.chila.tallermecanico.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;


import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;



public class VistaContacto extends AppCompatActivity implements IVistaContacto{
    private static final int PICK_IMAGE_REQUEST = 1;

    private String idCliente;
    private ProgressBar progressBar;
    private TextView tvNombre, tvTelefono, tvEmail, tvDireccion, tvDni;
    private CircularImageView foto;
    private VistaContactoPresenter presentador;
    private Uri mImageUri;
    private  StorageReference mStorageRef;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_contacto);
        referenciarView();


        final Bundle parametros = getIntent().getExtras(); //Recupero el id de cliente
        if (parametros != null) {
            idCliente = parametros.getString("id");
            presentador = new VistaContactoPresenter(this,idCliente, this.getApplicationContext());
        }

        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirSelectorImagen();
            }
        });
    }

    protected void abrirSelectorImagen(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
            && data != null && data.getData() != null){
            mImageUri = data.getData();
            Picasso.with(this).load(mImageUri).into(foto);
            subirFoto(mImageUri);

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
        Picasso.with(this).load(Uri.parse(cliente.getFotoUrl())).into(foto);
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


    private String getFileExtension (Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime  = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


    public void subirFoto(Uri mImageUri){
        if (mImageUri != null){
            mStorageRef = FirebaseStorage.getInstance().getReference();
            StorageReference imageRef = mStorageRef.child("/imgClientes/" + System.currentTimeMillis() + "." + getFileExtension(mImageUri));
            presentador.subirFotoCliente(mImageUri, imageRef);
        }else{
            Toast.makeText(this, "No selecciono foto",Toast.LENGTH_LONG).show();
        }

    }

}
