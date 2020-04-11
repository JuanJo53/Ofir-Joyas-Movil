package com.example.ofir_joyas_movil.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ofir_joyas_movil.Entidades.Cliente;
import com.example.ofir_joyas_movil.R;

import java.util.ArrayList;

public class ClienteListAdapter extends ArrayAdapter<Cliente> {
    Context context;
    ArrayList<Cliente> clientes;
    public ClienteListAdapter(Context context, ArrayList<Cliente> clientes) {
        super(context, R.layout.layout_item_cliente,clientes);
        this.context=context;
        this.clientes=clientes;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.layout_item_cliente, parent, false);

        Cliente cliente = clientes.get(position);

        TextView tvnombre = (TextView) view.findViewById(R.id.tvNombreClienteList);
        tvnombre.setText(cliente.getNombre());

        TextView tvci = (TextView) view.findViewById(R.id.tvCiClienteList);
        tvci.setText(cliente.getCi());

        TextView tvdireccion = (TextView) view.findViewById(R.id.tvDireccionClienteList);
        tvdireccion.setText(cliente.getDireccion());

        TextView tvtelefono = (TextView) view.findViewById(R.id.tvTelefonoClienteList);
        tvtelefono.setText(cliente.getTelefono());

        return view;
    }
}
