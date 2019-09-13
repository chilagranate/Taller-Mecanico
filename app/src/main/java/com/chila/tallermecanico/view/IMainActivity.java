package com.chila.tallermecanico.view;

import com.chila.tallermecanico.adapter.OrdenTrabajoAdapter;
import com.chila.tallermecanico.model.OrdenServicio;

import java.util.List;

public interface IMainActivity {

    void inicializarAdaptador(OrdenTrabajoAdapter adaptador);

    void generarLayout();

    OrdenTrabajoAdapter crearAdaptador(List<OrdenServicio> ordenesTrabajo);


}
