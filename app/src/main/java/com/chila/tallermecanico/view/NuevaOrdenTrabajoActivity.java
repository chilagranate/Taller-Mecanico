package com.chila.tallermecanico.view;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.chila.tallermecanico.Firestore.Database;
import com.chila.tallermecanico.R;
import com.chila.tallermecanico.model.Auto;
import com.chila.tallermecanico.model.Cliente;
import com.chila.tallermecanico.model.OrdenServicio;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NuevaOrdenTrabajoActivity extends AppCompatActivity {

    @BindView(R.id.orden_trabajo_clientes_et)
    AutoCompleteTextView clientesET;
    @BindView(R.id.orden_trabajo_selected_cliente_container)
    View selectedClienteContainer;
    @BindView(R.id.orden_trabajo_selected_cliente)
    TextView selectedClienteTV;
    @BindView(R.id.orden_trabajo_selected_cliente_foto)
    CircularImageView selectedClienteImageView;
    @BindView(R.id.orden_trabajo_clientes_close)
    View selectedClientCloseBtn;
    @BindView(R.id.orden_trabajo_auto_et)
    AutoCompleteTextView autoET;
    @BindView(R.id.orden_trabajo_selected_car_container)
    View selectedCarContainer;
    @BindView(R.id.orden_trabajo_selected_car)
    TextView selectedCarTV;
    @BindView(R.id.orden_trabajo_selected_car_foto)
    CircularImageView selectedCarImageView;
    @BindView(R.id.orden_trabajo_car_close)
    View selectedCarCloseBtn;
    @BindView(R.id.orden_trabajo_selected_car_patente)
    TextView selectedCarPatenteTV;
    @BindView(R.id.orden_trabajo_nuevo_cliente_bt)
    Button nuevoClienteBT;
    @BindView(R.id.orden_trabajo_nuevo_auto_bt)
    Button nuevoAutoBT;
    @BindView(R.id.orden_trabajo_fecha)
    EditText fechaET;
    @BindView(R.id.nueva_orden_trabajo_gasolina_sb)
    SeekBar gasolinaSB;
    @BindView(R.id.nueva_orden_trabajo_gasolina_tv)
    TextView gasolinaTV;
    @BindView(R.id.nueva_orden_trabajo_gasolina_cb)
    CheckBox gasolinaCB;
    @BindView(R.id.nueva_orden_trabajo_kilometraje)
    TextView kilometrajeTV;
    @BindView(R.id.nueva_orden_trabajo_kilometraje_titulo_tv)
    TextView kilometrajeTituloTV;
    @BindView(R.id.nueva_orden_trabajo_kilometraje_cb)
    CheckBox kilometrajeCB;
    @BindView(R.id.nueva_orden_trabajo_kilometraje_layout)
    View kilometrajeLayout;
    @BindView(R.id.nueva_orden_trabajo_falla_cliente)
    EditText fallaClienteET;

    private List<Cliente> clientes = new ArrayList<>();
    private Cliente selectedClient;
    private Auto selectedCar;
    private Database db = Database.getInstance();
    private final Calendar myCalendar = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_orden_trabajo);
        ButterKnife.bind(this);

        selectedClientCloseBtn.setOnClickListener(view -> closeSelectedClient());
        selectedCarCloseBtn.setOnClickListener(view -> closeSelectedCar());
        selectedCarContainer.setVisibility(View.GONE);
        selectedClienteContainer.setVisibility(View.GONE);
        mostrarFecha();

        nuevoAutoBT.setOnClickListener(v -> {
            Intent intent = new Intent(NuevaOrdenTrabajoActivity.this, NuevoAutoActivity.class);
            startActivity(intent);
        });

        nuevoClienteBT.setOnClickListener(v -> {
            Intent intent1 = new Intent(NuevaOrdenTrabajoActivity.this, NuevoCliente.class);
            startActivity(intent1);
        });

        gasolinaCB.setOnClickListener(v -> {
            if (gasolinaCB.isChecked()) {
                gasolinaSB.setVisibility(View.GONE);
                gasolinaTV.setVisibility(View.GONE);
            } else {
                gasolinaSB.setVisibility(View.VISIBLE);
                gasolinaTV.setVisibility(View.VISIBLE);
            }
        });

        kilometrajeCB.setOnClickListener(v -> {
            if (kilometrajeCB.isChecked()) {
                kilometrajeTituloTV.setVisibility(View.VISIBLE);
                kilometrajeLayout.setVisibility(View.GONE);
            } else {
                kilometrajeTituloTV.setVisibility(View.GONE);
                kilometrajeLayout.setVisibility(View.VISIBLE);
            }

        });

        gasolinaSB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int seekBarValue = seekBar.getProgress();
                switch (seekBarValue) {
                    case 0:
                        gasolinaTV.setText("Vacio");
                        break;
                    case 1:
                        gasolinaTV.setText("1/4");
                        break;
                    case 2:
                        gasolinaTV.setText("Medio tanque");
                        break;
                    case 3:
                        gasolinaTV.setText("3/4");
                        break;
                    case 4:
                        gasolinaTV.setText("Lleno");
                        break;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        db.getClientes(this::populateClientesAdapter);


        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            mostrarFecha();
        };

        fechaET.setOnClickListener(v -> new DatePickerDialog(NuevaOrdenTrabajoActivity.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_nueva_orden_trabajo, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_nueva_orden_trabajo_guardar:
                guardarOrdenTrabajo();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void guardarOrdenTrabajo() {
        OrdenServicio ot = new OrdenServicio();
        if (kilometrajeCB.isChecked())
            selectedCar.setKm("Sin tablero");
        else
            selectedCar.setKm(kilometrajeTV.getText().toString());


        ot.setAuto(selectedCar);
        ot.setFechaEntrada(myCalendar.getTime());
        if (gasolinaCB.isChecked())
            ot.setNaftaRestante("Sin medidor");
        else
            ot.setNaftaRestante(gasolinaTV.getText().toString());

        ot.setFallaCliente(fallaClienteET.getText().toString());
        db.nuevaOrdenTrabajo(ot);
    }

    private void closeSelectedCar() {
        selectedCar = null;
        autoET.setVisibility(View.VISIBLE);
        autoET.setText("");
        selectedCarContainer.setVisibility(View.GONE);
        nuevoAutoBT.setVisibility(View.VISIBLE);
    }

    private void mostrarFecha() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        fechaET.setText(sdf.format(myCalendar.getTime()));

    }


    private void populateClientesAdapter(List<Cliente> clientes) {
        ArrayAdapter<Cliente> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, clientes);
        clientesET.setAdapter(adapter);

        clientesET.setOnItemClickListener((adapterView, view, position, l) -> {
            Object item = adapterView.getItemAtPosition(position);
            if (item instanceof Cliente)
                clientSelected((Cliente) item);

        });
    }

    private void clientSelected(Cliente client) {
        selectedClient = client;
        clientesET.setVisibility(View.GONE);
        nuevoClienteBT.setVisibility(View.GONE);

        selectedClienteTV.setText(client.toString());
        selectedClienteContainer.setVisibility(View.VISIBLE);
        //Picasso.with(this).load(Uri.parse(client.getFotoUrl())).into(selectedClienteImageView);
        db.obtenerAutosCliente(client, this::pupulateCarsAdapter);
    }

    private void closeSelectedClient() {
        selectedClient = null;
        clientesET.setVisibility(View.VISIBLE);
        clientesET.setText("");
        selectedClienteContainer.setVisibility(View.GONE);
        nuevoClienteBT.setVisibility(View.VISIBLE);
        closeSelectedCar();
    }

    private void pupulateCarsAdapter(List<Auto> autos) {
        if (autos.size() == 1) {
            carSelected(autos.get(0));
        } else {
            ArrayAdapter<Auto> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_dropdown_item_1line, autos);
            autoET.setAdapter(adapter);

            autoET.setOnItemClickListener((adapterView, view, position, id) -> {
                Object item = adapterView.getItemAtPosition(position);
                if (item instanceof Auto)
                    carSelected((Auto) item);
            });
        }
    }

    private void carSelected(Auto auto) {
        selectedCar = auto;
        autoET.setVisibility(View.GONE);
        nuevoAutoBT.setVisibility(View.GONE);
        selectedCarTV.setText(auto.toString());
        selectedCarPatenteTV.setText(auto.getPatente());
        selectedCarContainer.setVisibility(View.VISIBLE);
        if (auto.getFotoPortada() != null)
            Picasso.with(this).load(Uri.parse(auto.getFotoPortada())).into(selectedCarImageView);

    }
}
