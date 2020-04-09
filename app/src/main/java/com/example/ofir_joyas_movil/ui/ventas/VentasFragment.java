package com.example.ofir_joyas_movil.ui.ventas;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.ofir_joyas_movil.Adaptadores.JoyaListAdapter;
import com.example.ofir_joyas_movil.Adaptadores.VentaListAdapter;
import com.example.ofir_joyas_movil.AdminDataBase;
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

        getVentaData();
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
                etcliente.setText(getCI(venta.getCod_cliente(),"Cliente"));
                etempleado.setText(getCI(venta.getCod_empleado(),"Empleado"));
                etnombrejoya.setText(getNombJoya(venta.getCod_joya()));
                etcantidad.setText(String.valueOf(venta.getCantidad()));
                etcosto.setText(venta.getTotal().toString());
                etfecha.setText(venta.getFecha_emision());

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
    public void getVentaData(){
        AdminDataBase admin = new AdminDataBase(root.getContext(),"administracion",null,1);
        SQLiteDatabase db = admin.getWritableDatabase();
        try {
            Cursor sql = db.rawQuery("Select * "+
                            "from Venta ",
                    null);
            if(sql.moveToFirst()){
                while (!sql.isAfterLast()) {
                    ventas.add(new Venta(
                            sql.getInt(0),
                            sql.getString(1),
                            sql.getInt(2),
                            sql.getDouble(3),
                            sql.getInt(4),
                            sql.getInt(5),
                            sql.getInt(6)));
                    sql.moveToNext();
                }
            }else{
                Toast.makeText(root.getContext(),"Error al obtener los datos de la venta",Toast.LENGTH_LONG).show();
            }
        }catch (SQLException e){
            System.out.println(e);
        }
        db.close();
    }
    public String getCI(int cod, String tabla){
        String ci="";
        AdminDataBase admin = new AdminDataBase(root.getContext(),"administracion",null,1);
        SQLiteDatabase db = admin.getWritableDatabase();
        try {
            Cursor sql = db.rawQuery("Select CI "+
                            "from "+tabla+" "+
                            "where cod_"+tabla+"=\""+ cod +"\"",
                    null);
            if(sql.moveToFirst()){
                ci=sql.getString(0);
            }else{
                Toast.makeText(root.getContext(),"Error al obtener el CI",Toast.LENGTH_LONG).show();
            }
        }catch (SQLException e){
            System.out.println(e);
        }
        db.close();
        return ci;
    }
    public String getNombJoya(int cod){
        String nombre="";
        AdminDataBase admin = new AdminDataBase(root.getContext(),"administracion",null,1);
        SQLiteDatabase db = admin.getWritableDatabase();
        try {
            Cursor sql = db.rawQuery("Select Nombre "+
                            "from Joya "+
                            "where cod_Joya=\""+ cod +"\"",
                    null);
            if(sql.moveToFirst()){
                nombre=sql.getString(0);
            }else{
                Toast.makeText(root.getContext(),"Error al obtener el Nombre de la Joya",Toast.LENGTH_LONG).show();
            }
        }catch (SQLException e){
            System.out.println(e);
        }
        db.close();
        return nombre;
    }
}
