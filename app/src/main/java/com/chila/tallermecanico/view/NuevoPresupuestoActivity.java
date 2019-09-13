package com.chila.tallermecanico.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chila.tallermecanico.R;
import com.chila.tallermecanico.model.ManoDeObra;
import com.chila.tallermecanico.model.Presupuesto;
import com.chila.tallermecanico.model.Reparacion;
import com.chila.tallermecanico.model.Repuesto;
import com.chila.tallermecanico.viewmodel.OrdenServicioViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NuevoPresupuestoActivity extends AppCompatActivity {


    @BindView(R.id.nuevo_presupuesto_cliente_tv)
    TextView clienteTV;
    @BindView(R.id.nuevo_presupuesto_vehiculo_tv)
    TextView vehiculoTV;
    @BindView(R.id.nuevo_presupuesto_patente_tv)
    TextView patenteTV;
    @BindView(R.id.nuevo_presupuesto_nueva_reparacion_cardview)
    CardView nuevaReparacionCV;
    @BindView(R.id.nuevo_presupuesto_nueva_reparacion_nombre)
    EditText nuevaReparacionNombreET;
    @BindView(R.id.nuevo_presupuesto_nueva_reparacion_descripcion)
    EditText nuevaReparacionDescripcionET;
    @BindView(R.id.nuevo_presupuesto_nuevo_repuesto_layout)
    View nuevoRepuestoLayout;
    @BindView(R.id.nuevo_presupuesto_nueva_reparacion_pieza)
    EditText nuevoRepuestoNombreET;
    @BindView(R.id.nuevo_presupuesto_nueva_pieza_precio)
    EditText nuevoRepuestoPrecioET;
    @BindView(R.id.nuevo_presupuesto_nueva_pieza_cantidad)
    EditText nuevoRepuestoCantidadET;
    @BindView(R.id.nuevo_repuesto_nueva_pieza_bt)
    Button nuevaPiezaBT;
    @BindView(R.id.nuevo_presupuesto_nuevo_repuesto_bt)
    Button nuevoRepuestoBT;
    @BindView(R.id.nuevo_presupuesto_nueva_mano_obra_bt)
    Button nuevaManoObraBT;
    @BindView(R.id.nuevo_presupuesto_nueva_reparacion_bt)
    Button nuevaReparacionBT;

    private Presupuesto presupuesto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_presupuesto);
        ButterKnife.bind(this);
        presupuesto = new Presupuesto();
        final Bundle parametros = getIntent().getExtras();
        String idOS = parametros.getString("id");

        nuevaReparacionBT.setOnClickListener(v -> nuevaReparacion());

        ocultarCargaNuevaReparacion();
        ocultarCargaNuevoRepuesto();
    }

    public void mostrarDatosServicio(String cliente, String vehiculo, String patente){
        clienteTV.setText(cliente);
        vehiculoTV.setText(vehiculo);
        patenteTV.setText(patente);

    }
    public void mostrarCargaNuevoRepuesto() {
        nuevoRepuestoLayout.setVisibility(View.VISIBLE);
    }

    public void ocultarCargaNuevoRepuesto() {
        nuevoRepuestoLayout.setVisibility(View.GONE);
    }

    public void mostrarCargaNuevaReparacion() {
        nuevaReparacionCV.setVisibility(View.VISIBLE);
    }

    public void ocultarCargaNuevaReparacion() {
        nuevaReparacionCV.setVisibility(View.GONE);
    }

    public void nuevaReparacion() {
        Reparacion reparacion = new Reparacion();
        mostrarCargaNuevaReparacion();

        nuevoRepuestoBT.setOnClickListener(v -> mostrarCargaNuevoRepuesto());


        nuevaPiezaBT.setOnClickListener(v -> {
            reparacion.getRepuestos().add(nuevoRepuesto());
            ocultarCargaNuevoRepuesto();
            nuevoRepuestoNombreET.setText("");
            nuevoRepuestoPrecioET.setText("");
            nuevoRepuestoCantidadET.setText("");

        });

    }

    public Repuesto nuevoRepuesto() {
        Repuesto repuesto = new Repuesto();
        repuesto.setDescripcion(nuevoRepuestoNombreET.getText().toString());
        repuesto.setPrecio(Double.parseDouble(nuevoRepuestoPrecioET.getText().toString()));
        repuesto.setCantidad(Integer.parseInt(nuevoRepuestoCantidadET.getText().toString()));
        return repuesto;

    }

    public ManoDeObra nuevaManoDeObra() {
        ManoDeObra manoDeObra = new ManoDeObra();


        return manoDeObra;
    }
}
