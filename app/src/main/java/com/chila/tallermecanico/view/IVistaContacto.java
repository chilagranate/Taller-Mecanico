package com.chila.tallermecanico.view;

import com.chila.tallermecanico.adapter.AutoAdaptador;
import com.chila.tallermecanico.model.Auto;
import com.chila.tallermecanico.model.Cliente;

import java.util.List;

public interface IVistaContacto {

    void cargarCliente(Cliente cliente);

    void mostrarProgressBar();

    void ocultarProgressBar();

    void inicializarAdaptador(AutoAdaptador adaptador);

    void generarLayout();

    AutoAdaptador crearAdaptador(List<Auto> autos);


    }
