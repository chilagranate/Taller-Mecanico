package com.chila.tallermecanico.view;

import com.chila.tallermecanico.adapter.ClienteAdaptador;
import com.chila.tallermecanico.model.Cliente;

import java.util.List;

public interface IListaContactos {

    void inicializarAdaptador(ClienteAdaptador adaptador);

    void generarLayout();

    ClienteAdaptador crearAdaptador(List<Cliente> clientes);



    }
