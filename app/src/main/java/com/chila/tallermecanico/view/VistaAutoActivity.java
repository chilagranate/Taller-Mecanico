package com.chila.tallermecanico.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.widget.TextView;

import com.chila.tallermecanico.R;
import com.chila.tallermecanico.model.Auto;
import com.chila.tallermecanico.viewmodel.OrdenServicioViewModel;
import com.chila.tallermecanico.viewmodel.VistaAutoViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VistaAutoActivity extends AppCompatActivity {
    VistaAutoViewModel mViewModel;

    @BindView(R.id.vista_auto_patente_tv)
    TextView patenteTV;
    @BindView(R.id.vista_auto_modelo_tv)
    TextView modeloTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_auto);
        ButterKnife.bind(this);

        final Bundle parametros = getIntent().getExtras();
        if (parametros != null) {
            String patente = parametros.getString("patente");
            mViewModel = ViewModelProviders.of(this).get(VistaAutoViewModel.class);
            mViewModel.init(patente);
            mViewModel.getAuto().observe(this, this::mostrarAuto);

        }
    }

    private void mostrarAuto(Auto auto) {
        if(auto==null)
            return;

        patenteTV.setText(auto.getPatente());
        modeloTV.setText(auto.getModelo());


    }
}
