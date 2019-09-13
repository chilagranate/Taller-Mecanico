package com.chila.tallermecanico.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.chila.tallermecanico.R;
import com.chila.tallermecanico.adapter.RepuestoManodeObraAdapter;
import com.chila.tallermecanico.model.ManoDeObra;
import com.chila.tallermecanico.model.Reparacion;
import com.chila.tallermecanico.model.Repuesto;

import org.parceler.Parcels;

public class NuevaReparacionActivity extends AppCompatActivity {
    private Reparacion reparacion;
    private RepuestoManodeObraAdapter adapter;
    private static final int REPUESTO_RESULT_CODE= 15;

    @BindView(R.id.nueva_reparacion_guardar_reparacion_bt)
    Button guardarReparacionBT;
    @BindView(R.id.nueva_reparacion_rv)
    RecyclerView reparacionesRV;
    @BindView(R.id.nueva_reparacion_nuevo_repuesto_bt)
    Button nuevoRepuestoBT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_reparacion);
        ButterKnife.bind(this);
        reparacion = new Reparacion();
        configView();
        crearReparaciones();
        adapter = new RepuestoManodeObraAdapter(reparacion.getRepuestos(), reparacion.getManoDeObra());
        reparacionesRV.setLayoutManager(new LinearLayoutManager(this));
        reparacionesRV.setAdapter(adapter);

    }

    private void configView(){
        guardarReparacionBT.setOnClickListener(view -> {
            reparacion.setDescripcion("asdasdasd");
            Intent intent = new Intent();
            intent.putExtra("REPARACION_KEY", Parcels.wrap(reparacion));
            setResult(RESULT_OK, intent);
            finish();
        });

        nuevoRepuestoBT.setOnClickListener(view -> {
            Intent intent = new Intent(this, NuevoRepuestoActivity.class);
            startActivityForResult(intent, REPUESTO_RESULT_CODE);
            overridePendingTransition(android.R.anim.fade_in, 0);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != REPUESTO_RESULT_CODE || resultCode != RESULT_OK)
            return;

        if (data == null || data.getExtras() == null)
            return;

        Repuesto repuesto = Parcels.unwrap(data.getParcelableExtra("REPUESTO_KEY"));
        reparacion.addRepuesto(repuesto);
    }

    private void crearReparaciones(){
        ManoDeObra manoDeObra = new ManoDeObra();
        manoDeObra.setDescripcion("cambio motor");
        manoDeObra.setPrecio(500);
        reparacion.addManoDeObra(manoDeObra);
        ManoDeObra manoDeObra1 = new ManoDeObra();

        manoDeObra1.setDescripcion("cambio pastillas de freno");
        manoDeObra1.setPrecio(200);
        reparacion.addManoDeObra(manoDeObra1);

        Repuesto repuesto = new Repuesto();
        repuesto.setCantidad(1);
        repuesto.setDescripcion("motor");
        repuesto.setPrecio(1000);
        reparacion.addRepuesto(repuesto);
        Repuesto repuesto1 = new Repuesto();

        repuesto1.setDescripcion("pastillas de freno");
        repuesto1.setPrecio(400);
        reparacion.addRepuesto(repuesto1);

    }
}
