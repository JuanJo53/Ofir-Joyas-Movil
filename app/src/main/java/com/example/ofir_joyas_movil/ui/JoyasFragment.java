package com.example.ofir_joyas_movil.ui;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.example.ofir_joyas_movil.Adaptadores.JoyaListAdapter;
import com.example.ofir_joyas_movil.AdminDataBase;
import com.example.ofir_joyas_movil.Entidades.Joya;
import com.example.ofir_joyas_movil.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class JoyasFragment extends Fragment {
    AlertDialog.Builder aleProd;
    GridView gridViewJoyas;
    ArrayList<Joya> joyas=new ArrayList<Joya>();
    EditText etidjoya,etnombrejoya,etmetaljoya,etpesojoya,etpreciojoya,etstockjoya;
    ImageView ivFoto;
    Button btnOcFoto;
    Spinner sptipo;
    View root;
    ArrayList<String> tipos_joya= new ArrayList<String>();
    JoyaListAdapter adapter;

    final int COD_FOTO = 120;
    final String CARPETA_RAIZ = "MisFotosApp";
    final String CARPETA_IMAGENES = "imagenes";
    final String RUTA_IMAGEN = CARPETA_RAIZ + CARPETA_IMAGENES;
    String path;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_joyas, container, false);

        getJoyaData();

        getJoyaTiposSpinner();
        adapter=new JoyaListAdapter(root.getContext(),joyas);

        this.gridViewJoyas = (GridView)root.findViewById(R.id.gvJoyas);
        this.gridViewJoyas.setAdapter(adapter);
        this.gridViewJoyas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Joya joya = joyas.get(position);

                LayoutInflater inflador = LayoutInflater.from(root.getContext());
                View v = inflador.inflate(R.layout.layout_detalle_joya,null, false);
                btnOcFoto = (Button)v.findViewById(R.id.btnFoto);
                btnOcFoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ocTomaFoto(v);
                    }
                });
                ivFoto = (ImageView)v.findViewById(R.id.imJoyaDet);
                etidjoya = (EditText)v.findViewById(R.id.etIdJoyaDet);
                etnombrejoya = (EditText)v.findViewById(R.id.etNombreJoyaDet);
                etmetaljoya = (EditText)v.findViewById(R.id.etMetalJoyaDet);
                etpesojoya = (EditText) v.findViewById(R.id.etPesoJoyaDet);
                etpreciojoya = (EditText)v.findViewById(R.id.etPrecioJoya);
                etstockjoya = (EditText)v.findViewById(R.id.etStockJoya);

                sptipo = (Spinner)v.findViewById(R.id.spTipoJoya);
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(root.getContext(), android.R.layout.simple_spinner_dropdown_item, tipos_joya);
                sptipo.setAdapter(adapter1);

                ImageView foto=(ImageView)view.findViewById(R.id.ivJoya);
                ivFoto.setImageDrawable(foto.getDrawable());
                etidjoya.setText(String.valueOf(joya.getCod_joya()));
//                etidjoya.setEnabled(true);
                etnombrejoya.setText(joya.getNombre());
                etmetaljoya.setText(joya.getMetal());
                etpesojoya.setText(joya.getPeso().toString());
                etpreciojoya.setText(joya.getPrecio().toString());
                etstockjoya.setText(String.valueOf(joya.getStock()));
                sptipo.setSelection(joya.getCod_tipo());

                aleProd = new AlertDialog.Builder(root.getContext());
                aleProd.setCancelable(false);
                aleProd.setView(v);
//                aleProd.setNegativeButton("Eliminar", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        delJoya();
//                    }
//                });
                aleProd.setNeutralButton("Guardar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        updateJoya();
                        updateList();
                    }
                });
                aleProd.setPositiveButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(root.getContext(),"No se guardo!",Toast.LENGTH_LONG).show();
                    }
                });
                aleProd.show();
            }
        });
        return root;
    }
    public void updateList(){
        joyas.clear();
        getJoyaData();
        adapter.notifyDataSetChanged();
    }
    public void delJoya(){
        AdminDataBase admin = new AdminDataBase(root.getContext(),"administracion",null,1);
        SQLiteDatabase db = admin.getWritableDatabase();
        try {
            if(checkCamposJoya()){
                db.delete("Joya","cod_Joya="+etidjoya.getText().toString(),null);
                String sql1="DELETE FROM sqlite_sequence WHERE name = 'Joya'";
                db.execSQL(sql1);
                Toast.makeText(root.getContext(),"Joya Eliminada",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(root.getContext(),"Joya no se Elimino correctamente",Toast.LENGTH_LONG).show();
            }
        }catch (SQLException e){
            System.out.println(e);
        }
        db.close();
    }
    public void updateJoya(){
        AdminDataBase admin = new AdminDataBase(root.getContext(),"administracion",null,1);
        SQLiteDatabase db = admin.getWritableDatabase();
        ContentValues values = new ContentValues();
        byte[] imagen=imageViewToByte(ivFoto);
        try {
            if(checkCamposJoya()){
                values.put("Nombre",etnombrejoya.getText().toString());
                values.put("Metal",etmetaljoya.getText().toString());
                values.put("Peso",Double.parseDouble(etpesojoya.getText().toString()));
                values.put("Precio",Double.parseDouble(etpreciojoya.getText().toString()));
                values.put("Imagen",imagen);
                values.put("Stock",Integer.parseInt(etstockjoya.getText().toString()));
                values.put("cod_Tipo_Joya",Integer.parseInt(String.valueOf(sptipo.getSelectedItemPosition())));

                db.update("Joya",values,"cod_Joya="+etidjoya.getText().toString(),null);
                Toast.makeText(root.getContext(),"Datos de Joya Guardados",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(root.getContext(),"Debe llenar todos los campos",Toast.LENGTH_LONG).show();
            }
        }catch (SQLException e){
            System.out.println(e);
        }
        db.close();
    }
    public void getJoyaData(){
        AdminDataBase admin = new AdminDataBase(root.getContext(),"administracion",null,1);
        SQLiteDatabase db = admin.getWritableDatabase();
        if(!joyas.isEmpty()){
            joyas.clear();
        }
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
                Toast.makeText(root.getContext(),"Error al obtener datos de la joya",Toast.LENGTH_LONG).show();
            }
        }catch (SQLException e){
            System.out.println(e);
        }
        db.close();
    }
    public boolean checkCamposJoya(){
        if(etnombrejoya.getText().toString().isEmpty()|| etmetaljoya.getText().toString().isEmpty()
                ||etpesojoya.getText().toString().isEmpty() || etpreciojoya.getText().toString().isEmpty()
                || etstockjoya.getText().toString().isEmpty()){
            return false;
        }else{
            return true;
        }
    }
    public void getJoyaTiposSpinner(){
        AdminDataBase admin = new AdminDataBase(root.getContext(),"administracion",null,1);
        SQLiteDatabase db = admin.getWritableDatabase();
        if(!tipos_joya.isEmpty()){
            tipos_joya.clear();
        }
        try {
            Cursor sql = db.rawQuery("Select tipo "+
                            "from Tipo_Joya ",
                    null);
            if(sql.moveToFirst()){
                while ( !sql.isAfterLast() ) {
                    System.out.println("Tipo=> "+sql.getString(0));
                    tipos_joya.add(sql.getString(0)+"");
                    sql.moveToNext();
                }
            }else{
                Toast.makeText(root.getContext(),"Error al obtener los tipos de joya",Toast.LENGTH_LONG).show();
            }
        }catch (SQLException e){
            System.out.println(e);
        }
        db.close();
    }
    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
    public void ocTomaFoto(View v){
        String nombreImagen = "";

        File fileImagen = new File(Environment.getExternalStorageDirectory(), RUTA_IMAGEN);
        boolean isCreada = fileImagen.exists();

        if(isCreada == false) {
            isCreada = fileImagen.mkdirs();
        }

        if(isCreada == true) {
            //nombreImagen = (System.currentTimeMillis() / 1000) + ".jpg";
            nombreImagen = "mifoto.jpg";
        }

        path = Environment.getExternalStorageDirectory()+File.separator+RUTA_IMAGEN+File.separator+nombreImagen;
        File imagen = new File(path);

        Intent intent = null;
        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            String authorities = root.getContext().getPackageName()+".provider";
            Uri imageUri = FileProvider.getUriForFile(root.getContext(), authorities, imagen);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagen));
        }
        startActivityForResult(intent, COD_FOTO);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                case COD_FOTO:
                    MediaScannerConnection.scanFile(root.getContext(), new String[]{path}, null, new MediaScannerConnection.OnScanCompletedListener() {
                        @Override
                        public void onScanCompleted(String path, Uri uri) {

                        }
                    });
                    Bitmap bitmap = BitmapFactory.decodeFile(path);
                    ivFoto.setImageBitmap(bitmap);
                    break;
            }
        }
    }
}
