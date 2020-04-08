package com.example.ofir_joyas_movil.ui.joyas;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.ofir_joyas_movil.Adaptadores.JoyaListAdapter;
import com.example.ofir_joyas_movil.AdminDataBase;
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

        getJoyaData();

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

                ImageView foto=(ImageView)view.findViewById(R.id.ivJoya);
                ivFoto.setImageDrawable(foto.getDrawable());
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
    public void getJoyaData(){
        AdminDataBase admin = new AdminDataBase(root.getContext(),"administracion",null,1);
        SQLiteDatabase db = admin.getWritableDatabase();
        byte[] imagen;
        try {
            Cursor sql = db.rawQuery("Select * "+
                            "from Joya ",
                    null);
            if(sql.moveToFirst()){
                while (!sql.isAfterLast()) {
                    imagen=sql.getBlob(5);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imagen,0,imagen.length);

                    joyas.add(new Joya(sql.getInt(0),
                                    sql.getString(1),
                                    sql.getString(2),
                                    Double.parseDouble(sql.getString(3)),
                                    Double.parseDouble(sql.getString(4)),
                                    Integer.parseInt(sql.getString(6)),
                                    imagen,
                                    Integer.parseInt(sql.getString(7))));
                    sql.moveToNext();
                }
            }else{
                Toast.makeText(root.getContext(),"Error",Toast.LENGTH_LONG).show();
            }
        }catch (SQLException e){
            System.out.println(e);
        }
        db.close();
    }
}
