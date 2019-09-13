package com.chila.tallermecanico.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.TextView;

import com.chila.tallermecanico.R;
import com.chila.tallermecanico.fragment.OrdenServicioPresupuestoFragment;
import com.chila.tallermecanico.fragment.OrdenServicioRecepcionFragment;
import com.chila.tallermecanico.fragment.OrdenServicioTrabajoFragment;
import com.chila.tallermecanico.model.OrdenServicio;
import com.chila.tallermecanico.presenter.IVistaOrdenServicioPresentador;
import com.chila.tallermecanico.presenter.VistaOrdenServicioPresentador;
import com.chila.tallermecanico.viewmodel.OrdenServicioViewModel;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VistaOrdenServicioActivity extends AppCompatActivity implements IVistaOrdenTrabajoActivity {
    private IVistaOrdenServicioPresentador presenter;

    @BindView(R.id.vista_orden_trabajo_cliente_tv)
    TextView clienteTV;
    @BindView(R.id.vista_orden_trabajo_auto_tv)
    TextView autoTV;
    @BindView(R.id.vista_orden_trabajo_auto_patente_tv)
    TextView patenteTV;
    @BindView(R.id.vista_orden_trabajo_fecha_ingreso)
    TextView fechaIngresoTV;
    @BindView(R.id.vista_orden_trabajo_viewpager)
    ViewPager mViewPager;
    @BindView(R.id.vista_orden_trabajo_tabs)
    TabLayout tabLayout;

    private SectionPageAdapter mSectionPageAdapter;

    OrdenServicioViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_orden_trabajo);
        ButterKnife.bind(this);
        final Bundle parametros = getIntent().getExtras();
        if (parametros != null) {
            String idOT = parametros.getString("id");
            //presenter = new VistaOrdenServicioPresentador(this, idOT);
            mViewModel = ViewModelProviders.of(this).get(OrdenServicioViewModel.class);
            mViewModel.init(idOT);
            mViewModel.getOrdenServicio().observe(this, this::mostrarOT);
        }

        setupViewPager(mViewPager);
        tabLayout.setupWithViewPager(mViewPager);

    }

    private void setupViewPager(ViewPager viewPager){
        SectionPageAdapter adapter = new SectionPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new OrdenServicioPresupuestoFragment(), "Presupuesto");
        adapter.addFragment(new OrdenServicioTrabajoFragment(), "Trabajo");
        viewPager.setAdapter(adapter);
    }


    public void mostrarOT(OrdenServicio os){
        clienteTV.setText(os.getAuto().getCliente().toString());
        autoTV.setText(os.getAuto().getMarca() + " " + os.getAuto().getModelo());
        patenteTV.setText(os.getAuto().getPatente());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        fechaIngresoTV.setText(sdf.format(os.getFechaEntrada()));



    }
}
