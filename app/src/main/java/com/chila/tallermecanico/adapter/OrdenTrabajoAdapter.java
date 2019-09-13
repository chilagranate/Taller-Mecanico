package com.chila.tallermecanico.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.chila.tallermecanico.R;
import com.chila.tallermecanico.model.OrdenServicio;
import com.chila.tallermecanico.view.VistaOrdenTrabajoActivity;

import java.text.SimpleDateFormat;
import java.util.List;

public class OrdenTrabajoAdapter extends RecyclerView.Adapter<OrdenTrabajoAdapter.OrdenTrabajoViewHolder> {
    private List<OrdenServicio> ordenesTrabajo;
    private Activity activity;

    public OrdenTrabajoAdapter(List<OrdenServicio> ordenesTrabajo, Activity activity) {
        this.ordenesTrabajo = ordenesTrabajo;
        this.activity = activity;
    }

    @NonNull
    @Override
    public OrdenTrabajoAdapter.OrdenTrabajoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_orden_trabajo, parent,false);
        return new OrdenTrabajoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdenTrabajoAdapter.OrdenTrabajoViewHolder holder, int position) {
        final OrdenServicio ordenServicio = ordenesTrabajo.get(position);
        holder.tvCliente.setText(ordenServicio.getAuto().getCliente().toString());
        holder.tvAuto.setText(ordenServicio.getAuto().toString());
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String fecha = format.format(ordenServicio.getFechaEntrada());
        holder.tvFechaEntrada.setText(fecha);
        holder.cardview.setOnClickListener(v -> {
            Intent intent = new Intent (v.getContext(), VistaOrdenTrabajoActivity.class);
            intent.putExtra("id", ordenServicio.getId());
            v.getContext().startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {
        return ordenesTrabajo.size();
    }

    public class OrdenTrabajoViewHolder extends RecyclerView.ViewHolder{
        private TextView tvCliente;
        private TextView tvAuto;
        private TextView tvFechaEntrada;

        private CardView cardview;

        private OrdenTrabajoViewHolder(View itemView){
            super(itemView);
            tvCliente = itemView.findViewById(R.id.cardview_orden_trabajo_cliente_tv);
            tvAuto = itemView.findViewById(R.id.cardview_orden_trabajo_auto_tv);
            tvFechaEntrada = itemView.findViewById(R.id.orden_trabajo_fecha_entrada_tv);
            cardview = itemView.findViewById(R.id.cardview_orden_trabajo);


        }
    }
}
