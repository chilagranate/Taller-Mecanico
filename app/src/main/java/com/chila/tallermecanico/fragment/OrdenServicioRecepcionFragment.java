package com.chila.tallermecanico.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.chila.tallermecanico.R;
import com.chila.tallermecanico.model.OrdenServicio;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrdenServicioRecepcionFragment extends Fragment{

    @BindView(R.id.orden_servicio_recepcion_kilometraje_tv)
    TextView kilometrajeTV;
    @BindView(R.id.orden_servicio_recepcion_gasolina_tv)
    TextView gasolinaTv;
    @BindView(R.id.orden_servicio_recepcion_falla_cliente_tv)
    TextView fallaClienteTV;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.orden_servicio_recepcion_fragment,container,false);
        ButterKnife.bind(view);
        return view;
    }

    public void mostrarDatosRecepcion(OrdenServicio ordenServicio){
        kilometrajeTV.setText(ordenServicio.getAuto().getKm());
        gasolinaTv.setText(ordenServicio.getNaftaRestante());
        fallaClienteTV.setText(ordenServicio.getFallaCliente());
    }
}
