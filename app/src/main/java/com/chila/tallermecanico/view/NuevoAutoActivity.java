package com.chila.tallermecanico.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chila.tallermecanico.Firestore.Database;
import com.chila.tallermecanico.Firestore.FirestoreCallbackCliente;
import com.chila.tallermecanico.R;
import com.chila.tallermecanico.model.Auto;
import com.chila.tallermecanico.model.Cliente;
import com.chila.tallermecanico.presenter.VistaContactoPresenter;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.w3c.dom.Text;

public class NuevoAutoActivity extends AppCompatActivity {
    private String idCliente;
    private Cliente cliente;
    private TextView tvMarca, tvModelo, tvPatente, tvVin, tvKm;
    private ImageView imgAuto;
    private String[] listaTipoVehiculo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_auto);

        final Bundle parametros = getIntent().getExtras(); //Recupero el id de cliente
        if (parametros != null) {
            idCliente = parametros.getString("id");
            Database db = Database.getInstance();
            db.obtenerCliente(idCliente, new FirestoreCallbackCliente() {
                @Override
                public void onCallBack(Cliente cliente) {
                    setCliente(cliente);
                }
            });

        }
        referenciarViews();

    }

    public void setCliente(Cliente cliente){
        this.cliente = cliente;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_nuevo_auto, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_nuevo_auto_guardar:
                guardarAuto();
                finish();
                break;
            case 0:
                break;

        }
        return true;
    }

    private void referenciarViews(){
        tvMarca= findViewById(R.id.nuevo_auto_marca);
        tvModelo= findViewById(R.id.nuevo_auto_modelo);
        tvPatente= findViewById(R.id.nuevo_auto_patente);
        tvVin= findViewById(R.id.nuevo_auto_VIN);
        tvKm= findViewById(R.id.nuevo_auto_km);
        listaTipoVehiculo = getResources().getStringArray(R.array.tipo_nuevo_auto);

        //imgAuto=findViewById(R.id.nuevo_auto_foto);
    }

    public Auto crearAuto(){
        Auto auto = new Auto();

        auto.setCliente(cliente);
        auto.setMarca(tvMarca.getText().toString());
        auto.setModelo(tvModelo.getText().toString());
        auto.setPatente(tvPatente.getText().toString());
        auto.setVIN(tvVin.getText().toString());
        auto.setKm(tvKm.getText().toString());
        return auto;
    }

    public void guardarAuto(){
        Database db = Database.getInstance();
        db.agregarAuto(crearAuto());

    }
}
