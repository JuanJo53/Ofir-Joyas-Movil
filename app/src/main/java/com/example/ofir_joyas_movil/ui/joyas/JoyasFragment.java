package com.example.ofir_joyas_movil.ui.joyas;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.ofir_joyas_movil.Adaptadores.JoyaListAdapter;
import com.example.ofir_joyas_movil.Entidades.Joya;
import com.example.ofir_joyas_movil.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class JoyasFragment extends Fragment {
    AlertDialog.Builder aleProd;
    GridView gridViewJoyas;
    ArrayList<Joya> joyas=new ArrayList<Joya>();
    EditText etidjoya,etnombrejoya,etmetaljoya,etpesojoya,etpreciojoya,etstockjoya;
    ImageView ivFoto;
    View root;
    String currentFrag;
    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_joyas, container, false);

        joyas.add(new Joya(1,"Anillo","Oro",3.0,50.0,15,R.drawable.aretes_oro));
        joyas.add(new Joya(2,"Anillo","Oro",3.0,50.0,15,R.drawable.aretes_oro));
        joyas.add(new Joya(3,"Anillo","Oro",3.0,50.0,15,R.drawable.aretes_oro));
        joyas.add(new Joya(4,"Anillo","Oro",3.0,50.0,15,R.drawable.aretes_oro));
        joyas.add(new Joya(5,"Anillo","Oro",3.0,50.0,15,R.drawable.aretes_oro));

        JoyaListAdapter adapter=new JoyaListAdapter(root.getContext(),joyas);

        this.gridViewJoyas = (GridView)root.findViewById(R.id.gvJoyas);
        this.gridViewJoyas.setAdapter(adapter);
        this.gridViewJoyas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Joya joya = joyas.get(position);

                LayoutInflater inflador = LayoutInflater.from(root.getContext());
                View v = inflador.inflate(R.layout.layout_detalle_joya,null, false);

                ivFoto = (ImageView)v.findViewById(R.id.imJoyaDet);
                etidjoya = (EditText)v.findViewById(R.id.etIdJoyaDet);
                etnombrejoya = (EditText)v.findViewById(R.id.etNombreJoyaDet);
                etmetaljoya = (EditText)v.findViewById(R.id.etMetalJoyaDet);
                etpesojoya = (EditText) v.findViewById(R.id.etPesoJoyaDet);
                etpreciojoya = (EditText)v.findViewById(R.id.etPrecioJoya);
                etstockjoya = (EditText)v.findViewById(R.id.etStockJoya);

                ivFoto.setImageResource(joya.getFoto());
                etidjoya.setText(String.valueOf(joya.getCod_joya()));
                etnombrejoya.setText(joya.getNombre());
                etmetaljoya.setText(joya.getMetal());
                etpesojoya.setText(joya.getPeso().toString());
                etpreciojoya.setText(joya.getPrecio().toString());
                etstockjoya.setText(String.valueOf(joya.getStock()));

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
                aleProd.setPositiveButton("Guardar Cambios", new DialogInterface.OnClickListener() {
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
