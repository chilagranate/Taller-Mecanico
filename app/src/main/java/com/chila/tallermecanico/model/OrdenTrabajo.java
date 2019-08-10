package com.chila.tallermecanico.model;

import java.util.ArrayList;
import java.util.Date;

public class OrdenTrabajo extends Comprobante
{

    private ArrayList<Pago> pagos;

    private Date fechaEntrada;
    private Date fechaSalida;
    private boolean finalizado;
    private boolean pagado;


}
