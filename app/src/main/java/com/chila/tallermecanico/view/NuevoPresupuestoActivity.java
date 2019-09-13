package com.chila.tallermecanico.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chila.tallermecanico.R;
import com.chila.tallermecanico.model.ManoDeObra;
import com.chila.tallermecanico.model.OrdenServicio;
import com.chila.tallermecanico.model.Presupuesto;
import com.chila.tallermecanico.model.Reparacion;
import com.chila.tallermecanico.model.Repuesto;
import com.chila.tallermecanico.viewmodel.NuevoPresupuestoViewModel;
import com.chila.tallermecanico.viewmodel.OrdenServicioViewModel;

import org.parceler.Parcels;

import java.util.EventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NuevoPresupuestoActivity extends AppCompatActivity {

    private static final int REPARACION_RESULT_CODE = 12;

    @BindView(R.id.nuevo_presupuesto_cliente_tv)
    TextView clienteTV;
    @BindView(R.id.nuevo_presupuesto_vehiculo_tv)
    TextView vehiculoTV;
    @BindView(R.id.nuevo_presupuesto_patente_tv)
    TextView patenteTV;
    @BindView(R.id.nuevo_presupuesto_nueva_reparacion_bt)
    Button nuevaReparacionBT;


    private Presupuesto presupuesto;
    private NuevoPresupuestoViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_presupuesto);
        ButterKnife.bind(this);
        presupuesto = new Presupuesto();
        final Bundle parametros = getIntent().getExtras();
        String idOS = parametros.getString("id");
        viewModel = ViewModelProviders.of(this).get(NuevoPresupuestoViewModel.class);
        viewModel.init(idOS);
        viewModel.getOrdenServicio().observe(this, new Observer<OrdenServicio>() {
            @Override
            public void onChanged(OrdenServicio ordenServicio) {
                mostrarDatosServicio(ordenServicio);
            }
        });

        nuevaReparacionBT.setOnClickListener(v -> nuevaReparacion());

    }

    public void mostrarDatosServicio(OrdenServicio ordenServicio){
        clienteTV.setText(ordenServicio.getAuto().getCliente().getNombreCompleto());
        vehiculoTV.setText(ordenServicio.getAuto().getModeloCompleto());
        patenteTV.setText(ordenServicio.getAuto().getPatente());

    }



    public void nuevaReparacion() {
        Intent intent = new Intent(this, NuevaReparacionActivity.class);
        startActivityForResult(intent, REPARACION_RESULT_CODE);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != REPARACION_RESULT_CODE || resultCode != RESULT_OK)
            return;

        if (data == null || data.getExtras() == null)
            return;

        Reparacion reparacion = Parcels.unwrap(data.getParcelableExtra("REPARACION_KEY"));
        presupuesto.addReparacion(reparacion);

    }
}
