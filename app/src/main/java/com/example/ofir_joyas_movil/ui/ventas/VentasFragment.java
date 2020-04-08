package com.example.ofir_joyas_movil.ui.ventas;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.ofir_joyas_movil.Adaptadores.JoyaListAdapter;
import com.example.ofir_joyas_movil.Adaptadores.VentaListAdapter;
import com.example.ofir_joyas_movil.Entidades.Joya;
import com.example.ofir_joyas_movil.Entidades.Venta;
import com.example.ofir_joyas_movil.R;

import java.util.ArrayList;
import java.util.Date;

public class VentasFragment extends Fragment {
    View root;
    AlertDialog.Builder aleProd;
    GridView gridViewVentas;
    ArrayList<Venta> ventas=new ArrayList<Venta>();
    EditText etcodigoventa,etcliente,etempleado,etnombrejoya,etcantidad,etcosto,etfecha;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_ventas, container, false);

        ventas.add(new Venta(1,new Date(),1,500.0,3,4,5));
        ventas.add(new Venta(2,new Date(),2,250.0,3,3,5));
        ventas.add(new Venta(3,new Date(),2,550.0,3,3,5));
        ventas.add(new Venta(4,new Date(),2,1550.0,3,3,5));
        ventas.add(new Venta(5,new Date(),2,15.0,3,3,5));
        VentaListAdapter adapter=new VentaListAdapter(root.getContext(),ventas);
        this.gridViewVentas = (GridView)root.findViewById(R.id.gvVentas);
        this.gridViewVentas.setAdapter(adapter);
        this.gridViewVentas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Venta venta = ventas.get(position);
                LayoutInflater inflador = LayoutInflater.from(root.getContext());
                View v = inflador.inflate(R.layout.layout_detalle_venta,null, false);

                etcodigoventa = (EditText)v.findViewById(R.id.etCodVentaDet);
                etcliente = (EditText)v.findViewById(R.id.etCIClienteDet);
                etempleado = (EditText)v.findViewById(R.id.etEmpleadoDet);
                etnombrejoya = (EditText) v.findViewById(R.id.etNombreJoyaDet);
                etcantidad = (EditText)v.findViewById(R.id.etCantidadDet);
                etcosto = (EditText)v.findViewById(R.id.etCostoDet);
                etfecha = (EditText)v.findViewById(R.id.etFechaDet);

                etcodigoventa.setText(String.valueOf(venta.getCod_vent()));
                etcliente.setText(String.valueOf(venta.getCod_cliente()));
                etempleado.setText(String.valueOf(venta.getCod_empleado()));
                etnombrejoya.setText(String.valueOf(venta.getCod_joya()));
                etcantidad.setText(String.valueOf(venta.getCantidad()));
                etcosto.setText(venta.getTotal().toString());
                etfecha.setText("20/20/2020");

                aleProd = new AlertDialog.Builder(root.getContext());
                aleProd.setCancelable(false);
                aleProd.setView(v);
                aleProd.setNegativeButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                aleProd.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                aleProd.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                aleProd.show();
            }
        });
        return root;
    }
}
