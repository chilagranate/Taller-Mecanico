package com.chila.tallermecanico.view;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.chila.tallermecanico.Firestore.Database;
import com.chila.tallermecanico.R;
import com.chila.tallermecanico.model.Cliente;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class NuevaOrdenTrabajoActivity extends AppCompatActivity {

    @BindView(R.id.orden_trabajo_clientes_et)
    AutoCompleteTextView clientesET;

    @BindView(R.id.orden_trabajo_selected_cliente_container)
    View selectedClienteContainer;
    @BindView(R.id.orden_trabajo_selected_cliente)
    TextView selectedClienteTV;
    @BindView(R.id.orden_trabajo_selected_cliente_foto)
    CircularImageView selectedClienteImageView;
    @BindView(R.id.orden_trabajo_clientes_close)
    View selectedClientCloseBtn;

    private List<Cliente> clientes = new ArrayList<>();
    private Cliente selectedClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_orden_trabajo);
        ButterKnife.bind(this);

        selectedClientCloseBtn.setOnClickListener(view -> closeSelectedClient());

        Database.getInstance().getClientes(this::populateClientesAdapter);
    }

    private void populateClientesAdapter(List<Cliente> clientes) {
        ArrayAdapter<Cliente> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, clientes);
        clientesET.setAdapter(adapter);

        clientesET.setOnItemClickListener((adapterView, view, position, l) -> {
            Object item = adapterView.getItemAtPosition(position);
            if (item instanceof Cliente)
                clientSelected((Cliente) item);

        });
    }

    private void clientSelected(Cliente client) {
        selectedClient = client;
        clientesET.setVisibility(View.GONE);

        selectedClienteTV.setText(client.toString());
        selectedClienteContainer.setVisibility(View.VISIBLE);
        Picasso.with(this).load(Uri.parse(client.getFotoUrl())).into(selectedClienteImageView);
    }

    private void closeSelectedClient() {
        selectedClient = null;
        clientesET.setVisibility(View.VISIBLE);
        clientesET.setText("");
        selectedClienteContainer.setVisibility(View.GONE);
    }
}
