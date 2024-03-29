package com.chila.tallermecanico.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.chila.tallermecanico.R;
import com.chila.tallermecanico.model.Cliente;
import com.chila.tallermecanico.view.VistaCliente;
import com.squareup.picasso.Picasso;


import java.util.List;

public class ClienteAdaptador extends RecyclerView.Adapter<ClienteAdaptador.ClienteViewHolder> {

    private List<Cliente> clientes;
    private Activity activity;

    public ClienteAdaptador(List<Cliente> clientes, Activity activity) {
        this.clientes = clientes;
        this.activity = activity;

    }


    @NonNull
    @Override
    public ClienteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_cliente, parent, false);
        return new ClienteViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ClienteAdaptador.ClienteViewHolder holder, int position) {
        final Cliente cliente = clientes.get(position);
        holder.tvCliente.setText(cliente.getNombre() + " " + cliente.getApellido());
        if (cliente.getFotoUrl() != null)
            Picasso.with(activity).load(Uri.parse(cliente.getFotoUrl())).into(holder.imgCliente);
        holder.cardViewCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), VistaCliente.class);
                intent.putExtra("id", cliente.getId());
                v.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return clientes.size();
    }


    public static class ClienteViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgCliente;
        private TextView tvCliente;

        private CardView cardViewCliente;


        private ClienteViewHolder(View itemView) {
            super(itemView);
            imgCliente = itemView.findViewById(R.id.cardview_cliente_foto);
            tvCliente = itemView.findViewById(R.id.cardview_cliente_nombre);
            cardViewCliente = itemView.findViewById(R.id.cardview_contacto);
        }
    }

}


//
