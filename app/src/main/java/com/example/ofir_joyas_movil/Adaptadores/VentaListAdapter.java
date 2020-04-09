package com.example.ofir_joyas_movil.Adaptadores;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ofir_joyas_movil.AdminDataBase;
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

        TextView tvcicliente = (TextView) view.findViewById(R.id.tvCiCliente);
        tvcicliente.setText(getCI(venta.getCod_cliente(),"Cliente"));

        TextView tvnombrejoya = (TextView) view.findViewById(R.id.tvNombreJoya);
        tvnombrejoya.setText(getNombJoya(venta.getCod_joya()));

        TextView tvcantidad = (TextView) view.findViewById(R.id.tvCantidad);
        tvcantidad.setText(String.valueOf(venta.getCantidad()));

        TextView tvtotal = (TextView) view.findViewById(R.id.tvTotal);
        tvtotal.setText(String.valueOf(venta.getTotal()));

        TextView tvfecha = (TextView) view.findViewById(R.id.tvFecha);
        tvfecha.setText(venta.getFecha_emision().toString());

        return view;
    }
    public String getCI(int cod, String tabla){
        String ci="";
        AdminDataBase admin = new AdminDataBase(this.context,"administracion",null,1);
        SQLiteDatabase db = admin.getWritableDatabase();
        try {
            Cursor sql = db.rawQuery("Select CI "+
                            "from "+tabla+" "+
                            "where cod_"+tabla+"=\""+ cod +"\"",
                    null);
            if(sql.moveToFirst()){
                ci=sql.getString(0);
                Toast.makeText(this.context,"CI obtenido!",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this.context,"Error al obtener el CI",Toast.LENGTH_LONG).show();
            }
        }catch (SQLException e){
            System.out.println(e);
        }
        db.close();
        return ci;
    }
    public String getNombJoya(int cod){
        String nombre="";
        AdminDataBase admin = new AdminDataBase(this.context,"administracion",null,1);
        SQLiteDatabase db = admin.getWritableDatabase();
        try {
            Cursor sql = db.rawQuery("Select Nombre "+
                            "from Joya "+
                            "where cod_Joya=\""+ cod +"\"",
                    null);
            if(sql.moveToFirst()){
                nombre=sql.getString(0);
                Toast.makeText(this.context,"Nombre Joya obtenido!",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this.context,"Error al obtener el nombre de la Joya",Toast.LENGTH_LONG).show();
            }
        }catch (SQLException e){
            System.out.println(e);
        }
        db.close();
        return nombre;
    }
}
