package com.chila.tallermecanico.model;

import java.util.ArrayList;
import java.util.Date;

public class Comprobante {
    private int id;
    private Cliente cliente;
    private Auto auto;
    private Date fecha;
    private String descripcion;
    private ArrayList<NotaComprobante> notasOrdenTrabajo;
    private ArrayList<ItemComprobante> itemComprobantes;
}
