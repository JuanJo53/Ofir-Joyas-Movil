package com.example.ofir_joyas_movil.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ofir_joyas_movil.Entidades.Joya;
import com.example.ofir_joyas_movil.Entidades.Venta;
import com.example.ofir_joyas_movil.R;

import java.util.ArrayList;

public class VentaListAdapter extends ArrayAdapter<Venta> {
    Context context;
    ArrayList<Venta> ventas;

    public VentaListAdapter(Context context, ArrayList<Venta> ventas) {
        super(context, R.layout.layout_item_venta,ventas);
        this.context = context;
        this.ventas = ventas;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.layout_item_venta, parent, false);

        Venta venta = ventas.get(position);

        TextView tvnombrecliente = (TextView) view.findViewById(R.id.tvCiCliente);
        tvnombrecliente.setText(String.valueOf(venta.getCod_cliente()));

        TextView tvnombrejoya = (TextView) view.findViewById(R.id.tvNombreJoya);
        tvnombrejoya.setText(String.valueOf(venta.getCod_joya()));

        TextView tvcantidad = (TextView) view.findViewById(R.id.tvCantidad);
        tvcantidad.setText(String.valueOf(venta.getCantidad()));

        TextView tvtotal = (TextView) view.findViewById(R.id.tvTotal);
        tvtotal.setText(String.valueOf(venta.getTotal()));

        TextView tvfecha = (TextView) view.findViewById(R.id.tvFecha);
        tvfecha.setText(venta.getFecha_emision().toString());

        return view;
    }
}
