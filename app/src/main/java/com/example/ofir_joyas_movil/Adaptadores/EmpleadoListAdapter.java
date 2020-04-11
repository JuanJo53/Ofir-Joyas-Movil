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
import com.example.ofir_joyas_movil.Entidades.Empleado;
import com.example.ofir_joyas_movil.R;

import java.util.ArrayList;

public class EmpleadoListAdapter extends ArrayAdapter<Empleado> {
    Context context;
    ArrayList<Empleado> empleados;
    public EmpleadoListAdapter(Context context, ArrayList<Empleado> empleados) {
        super(context, R.layout.layout_item_empleado, empleados);
        this.context=context;
        this.empleados=empleados;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.layout_item_empleado, parent, false);

        Empleado empleado = empleados.get(position);

        TextView tvnombre = (TextView) view.findViewById(R.id.tvNombreEmpleadoList);
        tvnombre.setText(empleado.getNombre());

        TextView tvci = (TextView) view.findViewById(R.id.tvCiEmpleadoList);
        tvci.setText(empleado.getCI());

        TextView tvcargo = (TextView) view.findViewById(R.id.tvCargoEmpleadoList);
        tvcargo.setText(empleado.getCargo());

        TextView tvtelefono = (TextView) view.findViewById(R.id.tvTelefonoEmpleadoList);
        tvtelefono.setText(empleado.getTelefono());

        return view;
    }
}
