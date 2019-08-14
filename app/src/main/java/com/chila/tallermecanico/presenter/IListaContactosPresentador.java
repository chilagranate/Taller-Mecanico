package com.chila.tallermecanico.presenter;

import com.chila.tallermecanico.model.Cliente;

import java.util.List;

public interface IListaContactosPresentador {


    public void obtenerClientes();

    public void mostrarClientes(List<Cliente> listaClientes);



    }
