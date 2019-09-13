package com.chila.tallermecanico.adapter;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.chila.tallermecanico.R;
import com.chila.tallermecanico.model.Auto;
import com.chila.tallermecanico.view.VistaAutoActivity;
import com.chila.tallermecanico.view.VistaOrdenTrabajoActivity;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

public class AutoAdaptador extends RecyclerView.Adapter<AutoAdaptador.AutoViewHolder> {
    List<Auto> autos;
    Activity activity;

    public AutoAdaptador(List<Auto> autos, Activity activity){
        this.autos = autos;
        this.activity = activity;
    }



    @NonNull
    @Override
    public AutoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_auto, parent,false);

        return new AutoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AutoAdaptador.AutoViewHolder holder, int position) {
        final Auto auto = autos.get(position);

        holder.tvNombreAuto.setText(auto.getMarca() + " " + auto.getModelo());
        holder.tvPatente.setText(auto.getPatente());
        holder.tvKilometraje.setText(auto.getKm());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), VistaAutoActivity.class);
                intent.putExtra("patente", auto.getPatente());
                v.getContext().startActivity(intent);
            }
        });

        //holder.tvOrdenTrabajoActivas.setText("OT ACTIVAS");
        //holder.tvNotActivas.setText("5");


    }

    @Override
    public int getItemCount() {
        return autos.size();
    }


    public static class AutoViewHolder extends RecyclerView.ViewHolder{
        private CircularImageView fotoAuto;
        private TextView tvNombreAuto, tvPatente, tvKilometraje, tvOrdenTrabajoActivas, tvNotActivas;
        private CardView cardView;


        private AutoViewHolder(View itemView){
            super(itemView);
            //fotoAuto = itemView.findViewById(R.id.image_auto);
            tvNombreAuto = itemView.findViewById(R.id.tv_nombre_auto);
            tvPatente = itemView.findViewById(R.id.tv_patente);
            tvKilometraje=itemView.findViewById(R.id.tv_kilometraje);
            tvOrdenTrabajoActivas = itemView.findViewById(R.id.tv_ot_activas);
            tvNotActivas = itemView.findViewById(R.id.tv_n_ot_activas);
            cardView = itemView.findViewById(R.id.cardview_auto);


        }
    }


}


