package com.chila.tallermecanico.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.chila.tallermecanico.R;

import com.chila.tallermecanico.view.NuevoPresupuestoActivity;
import com.chila.tallermecanico.viewmodel.OrdenServicioViewModel;


public class OrdenServicioPresupuestoFragment extends Fragment {
    private static final String TAG ="Tab1Fragment";
    private OrdenServicioViewModel mViewModel;


    public OrdenServicioPresupuestoFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.orden_servicio_presupuesto_fragment,container,false);
        mViewModel = ViewModelProviders.of(getActivity()).get(OrdenServicioViewModel.class);



        iniciarViews(view);



        return view;

    }

    private void iniciarViews(View view){
        Button nuevoPresupuestoBT = view.findViewById(R.id.orden_servicio_nuevo_presupuesto_bt);
        nuevoPresupuestoBT.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), NuevoPresupuestoActivity.class);
            String id = mViewModel.getOrdenServicio().getValue().getId();
            intent.putExtra("id", id);

            startActivity(intent);
        });
    }




}
