package com.example.ofir_joyas_movil.ui.pedidos;

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

import com.example.ofir_joyas_movil.Adaptadores.PedidoListAdapter;
import com.example.ofir_joyas_movil.Adaptadores.VentaListAdapter;
import com.example.ofir_joyas_movil.Entidades.Pedido;
import com.example.ofir_joyas_movil.Entidades.Venta;
import com.example.ofir_joyas_movil.R;

import java.util.ArrayList;
import java.util.Date;

public class PedidosFragment extends Fragment {
    View root;
    AlertDialog.Builder aleProd;
    GridView gridViewPedido;
    ArrayList<Pedido> pedidos=new ArrayList<Pedido>();
    EditText etcodigopedido,etcliente,etempleado,etnombrejoya,etcantidad,etestado,etfechaemision,etfechaentrega;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_pedidos, container, false);

        pedidos.add(new Pedido(1,new Date("02/20/2000"),new Date("02/20/2000"),1,0,3,2,5));
        pedidos.add(new Pedido(2,new Date("02/20/2000"),new Date("02/20/2000"),2,1,3,2,5));
        pedidos.add(new Pedido(3,new Date("02/20/2000"),new Date("02/20/2000"),3,1,3,6,7));
        pedidos.add(new Pedido(4,new Date("02/20/2000"),new Date("02/20/2000"),4,0,3,3,4));
        pedidos.add(new Pedido(5,new Date("02/20/2000"),new Date("02/20/2000"),5,0,3,2,3));

        PedidoListAdapter adapter=new PedidoListAdapter(root.getContext(),pedidos);
        this.gridViewPedido = (GridView)root.findViewById(R.id.gvPedidos);
        this.gridViewPedido.setAdapter(adapter);
        this.gridViewPedido.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Pedido pedido = pedidos.get(position);
                LayoutInflater inflador = LayoutInflater.from(root.getContext());
                View v = inflador.inflate(R.layout.layout_detalle_pedido,null, false);

                etcodigopedido = (EditText)v.findViewById(R.id.etCodPedidoDet);
                etcliente = (EditText)v.findViewById(R.id.etCIPedidoDet);
                etempleado = (EditText)v.findViewById(R.id.etEmpPedDet);
                etnombrejoya = (EditText) v.findViewById(R.id.etJoyaPedDet);
                etcantidad = (EditText)v.findViewById(R.id.etCantidadDet);
                etestado = (EditText)v.findViewById(R.id.etEstado);
                etfechaemision = (EditText)v.findViewById(R.id.etFechaEmisionDet);
                etfechaentrega = (EditText)v.findViewById(R.id.etFechaEntregaDet);

                etcodigopedido.setText(String.valueOf(pedido.getCod_pedido()));
                etcliente.setText(String.valueOf(pedido.getCod_cliente()));
                etempleado.setText(String.valueOf(pedido.getCod_empleado()));
                etnombrejoya.setText(String.valueOf(pedido.getCod_joya()));
                etcantidad.setText(String.valueOf(pedido.getCantidad()));
                etestado.setText(String.valueOf(pedido.getEstado()));
                etfechaemision.setText("20/20/2020");
                etfechaentrega.setText("20/20/2020");

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
