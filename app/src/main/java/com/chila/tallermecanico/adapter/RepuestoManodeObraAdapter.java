package com.chila.tallermecanico.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chila.tallermecanico.R;
import com.chila.tallermecanico.model.ManoDeObra;
import com.chila.tallermecanico.model.Repuesto;
import com.google.protobuf.StringValue;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Facundo A. Paredes on 13/9/2019.
 */
public class RepuestoManodeObraAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int REPUESTO_VIEW_TYPE = 1;
    private static final int MANO_DE_OBRA_VIEW_TYPE = 2;

    private List<Repuesto> repuestos;
    private List<ManoDeObra> manodeObraList;

    private List<Object> data = new ArrayList<>();

    public RepuestoManodeObraAdapter(List<Repuesto> repuestos, List<ManoDeObra> manodeObraList) {
        data.clear();
        data.addAll(repuestos);
        data.addAll(manodeObraList);

        this.repuestos = repuestos;
        this.manodeObraList = manodeObraList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == REPUESTO_VIEW_TYPE)
            return new RepuestoViewHolder(LayoutInflater.from(
                    parent.getContext()).inflate(R.layout.respuesto_item, parent, false));
        else
            return new ManoDeObraViewHolder(LayoutInflater.from(
                    parent.getContext()).inflate(R.layout.manodeobra_item, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RepuestoViewHolder) {
            // TODO
            Repuesto repuesto = (Repuesto) data.get(position);
            ((RepuestoViewHolder) holder).repuestoCantidadTV.setText(String.valueOf(repuesto.getCantidad()));
            ((RepuestoViewHolder) holder).repuestoNombreTV.setText(repuesto.getDescripcion());
            ((RepuestoViewHolder) holder).repuestoPrecioUnitarioTv.setText(String.valueOf(repuesto.getPrecio()));

            return;
        }

        if (holder instanceof ManoDeObraViewHolder) {
            // TODO
            ManoDeObra manoDeObra = (ManoDeObra) data.get(position);
            ((ManoDeObraViewHolder) holder).detalleTV.setText(manoDeObra.getDescripcion());
            ((ManoDeObraViewHolder) holder).precioTV.setText(String.valueOf(manoDeObra.getPrecio()));
        }
    }

    @Override
    public int getItemViewType(int position) {
        Object object = data.get(position);
        if (object instanceof Repuesto)
            return REPUESTO_VIEW_TYPE;

        if (object instanceof ManoDeObra)
            return MANO_DE_OBRA_VIEW_TYPE;

        return 0;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class RepuestoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.repuesto_item_nombre_tv)
        TextView repuestoNombreTV;
        @BindView(R.id.repuesto_item_cantidad_tv)
        TextView repuestoCantidadTV;
        @BindView(R.id.repuesto_item_precio_unitario_tv)
        TextView repuestoPrecioUnitarioTv;
        @BindView(R.id.repuesto_item_subtotal_tv)
        TextView repuestoSubtotalTV;
        @BindView(R.id.repuesto_item_edit_ib)
        ImageView repuestoEditIB;
        @BindView(R.id.repuesto_item_delete_ib)
        ImageView repuestoDeleteIB;

        RepuestoViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }

    class ManoDeObraViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.manodeobra_item_detalle_tv)
        TextView detalleTV;
        @BindView(R.id.manodeobra_item_precio_tv)
        TextView precioTV;
        @BindView(R.id.manodeobra_item_subtotal_tv)
        TextView subtotalTV;
        @BindView(R.id.manodeobra_item_edit_ib)
        ImageView editIB;
        @BindView(R.id.manodeobra_item_delete_ib)
        ImageView deleteIB;

        ManoDeObraViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }
}
