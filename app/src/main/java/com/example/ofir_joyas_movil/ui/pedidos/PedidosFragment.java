package com.example.ofir_joyas_movil.ui.pedidos;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.ofir_joyas_movil.Adaptadores.PedidoListAdapter;
import com.example.ofir_joyas_movil.Adaptadores.VentaListAdapter;
import com.example.ofir_joyas_movil.AdminDataBase;
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
    EditText etcodigopedido,etcliente,etempleado,etnombrejoya,etcantidad,etfechaemision;
    DatePicker dpfechaentrega;
    Spinner spestado;
    ArrayList<String> estado= new ArrayList<String>();

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_pedidos, container, false);

        estado.clear();
        estado.add("Por Entregar");
        estado.add("Entregado");
        getPedidoData();
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
                etcantidad = (EditText)v.findViewById(R.id.etCantidadDetPed);
                spestado = (Spinner)v.findViewById(R.id.spEstado);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(root.getContext(), android.R.layout.simple_spinner_dropdown_item, estado);
                spestado.setAdapter(adapter);
                etfechaemision = (EditText)v.findViewById(R.id.etFechaEmisionDet);
                dpfechaentrega = (DatePicker)v.findViewById(R.id.etFechaEntregaDet) ;

                etcodigopedido.setText(String.valueOf(pedido.getCod_pedido()));
                etcliente.setText(getCI(pedido.getCod_cliente(),"Cliente"));
                etempleado.setText(getCI(pedido.getCod_empleado(),"Empleado"));
                etnombrejoya.setText(getNombJoya(pedido.getCod_joya()));
                etcantidad.setText(String.valueOf(pedido.getCantidad()));
                spestado.setSelection(pedido.getEstado());
                etfechaemision.setText(pedido.getFecha_emision());

                String literalMes[]={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
                String fecha[]=pedido.getFecha_entrega().split("-",3);
                int day=Integer.parseInt(fecha[0]);
                String month=fecha[1];
                int MonthNumber=0;
                int year=Integer.parseInt(fecha[2]);
                for(int i=0;i<12;i++){
                    if(literalMes[i].equals(month)){
                        MonthNumber=i;
                    }
                }
                dpfechaentrega.init(year,MonthNumber,day,null);

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
    public void getPedidoData(){
        AdminDataBase admin = new AdminDataBase(root.getContext(),"administracion",null,1);
        SQLiteDatabase db = admin.getWritableDatabase();
        try {
            Cursor sql = db.rawQuery("Select * "+
                            "from Pedido ",
                    null);
            if(sql.moveToFirst()){
                while (!sql.isAfterLast()) {
                    pedidos.add(new Pedido(
                            sql.getInt(0),
                            sql.getString(1),
                            sql.getString(2),
                            sql.getInt(3),
                            sql.getInt(4),
                            sql.getInt(5),
                            sql.getInt(6),
                            sql.getInt(7)));
                    sql.moveToNext();
                }
            }else{
                Toast.makeText(root.getContext(),"Error al obtener los datos del pedido",Toast.LENGTH_LONG).show();
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
