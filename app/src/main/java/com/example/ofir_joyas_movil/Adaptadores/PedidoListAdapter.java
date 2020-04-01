package com.example.ofir_joyas_movil.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ofir_joyas_movil.Entidades.Pedido;
import com.example.ofir_joyas_movil.R;

import java.util.ArrayList;

public class PedidoListAdapter extends ArrayAdapter<Pedido> {
    Context context;
    ArrayList<Pedido> pedidos;

    public PedidoListAdapter(@NonNull Context context, ArrayList<Pedido> pedidos) {
        super(context, R.layout.layout_item_pedido, pedidos);
        this.context = context;
        this.pedidos = pedidos;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.layout_item_pedido, parent, false);

        Pedido pedido = pedidos.get(position);

        TextView tvcicliente = (TextView) view.findViewById(R.id.tvCiCliente);
        tvcicliente.setText(String.valueOf(pedido.getCod_cliente()));

        TextView tvnombrejoya = (TextView) view.findViewById(R.id.tvNombreJoya);
        tvnombrejoya.setText(String.valueOf(pedido.getCod_joya()));

        TextView tvcantidad = (TextView) view.findViewById(R.id.tvCantidad);
        tvcantidad.setText(String.valueOf(pedido.getCantidad()));

        TextView tvestado = (TextView) view.findViewById(R.id.tvEstado);
        tvestado.setText(String.valueOf(pedido.getEstado()));

        TextView tvfechaemision = (TextView) view.findViewById(R.id.tvFechaEmision);
//        tvfechaemision.setText(String.valueOf(pedido.getFecha_emision()));
        tvfechaemision.setText("20/20/2020");

        TextView tvfechaentrega = (TextView) view.findViewById(R.id.tvFechaEntrega);
//        tvfechaentrega.setText(String.valueOf(pedido.getFecha_entrega()));
        tvfechaentrega.setText("20/20/2020");

        return view;
    }
}
