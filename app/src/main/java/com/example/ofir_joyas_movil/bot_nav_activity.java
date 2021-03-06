package com.example.ofir_joyas_movil;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.ofir_joyas_movil.Adaptadores.ClienteListAdapter;
import com.example.ofir_joyas_movil.Adaptadores.EmpleadoListAdapter;
import com.example.ofir_joyas_movil.Entidades.Cliente;
import com.example.ofir_joyas_movil.Entidades.Empleado;
import com.example.ofir_joyas_movil.Entidades.Joya;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class bot_nav_activity extends AppCompatActivity {
    AlertDialog.Builder aleProd;
    ListView listViewClientes,listViewEmpleados;

    ImageView ivFoto;
    EditText etcodigo,etnombre,etmetal,etpeso,etprecio,etcantidad;

    EditText etcodventa,etciclienteventa,etnombrejoya,etempleadoventa,etcantidadventa,etcostoventa,etfechaventa;

    EditText etcodpedido,etciclientepedido,etnombrejoyapedido,etempleadopedido,etcantidadpedido,etfechaemision;
    DatePicker dpentrega;

    EditText etcodcliente,etcicliente,etnombrecliente,etdireccioncliente,ettelefonocliente;
    EditText etcodempleado,etciempleado,etnombreempleado,etcargoempleado,ettelefonoempleado,etcontraempelado;
    Spinner sptipo,spestado,spJoyas;

    ArrayList<String> tipos_joya= new ArrayList<String>();
    ArrayList<String> estadoPedido= new ArrayList<String>();
    ArrayList<String> nombJoyas= new ArrayList<String>();
    ArrayList<Cliente> clientes= new ArrayList<Cliente>();
    ArrayList<Empleado> empleados= new ArrayList<Empleado>();
    ArrayAdapter<String> adapter;

    final int COD_FOTO = 120;
    final String CARPETA_RAIZ = "MisFotosApp";
    final String CARPETA_IMAGENES = "imagenes";
    final String RUTA_IMAGEN = CARPETA_RAIZ + CARPETA_IMAGENES;
    String path;
    int cod_empleado;
    NavController navController;
    BottomNavigationView navView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bot_nav_activity);
        navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        Bundle bolsa = getIntent().getExtras();
        cod_empleado= bolsa.getInt("CodEmpleado");

        getJoyaTiposSpinner();
        getJoyasNombsSpinner();
        getClientesData();
        getEmpleadosData();

        estadoPedido.clear();
        estadoPedido.add("Por Entregar");
        estadoPedido.add("Entregado");

        // PERMISOS PARA ANDROID 6 O SUPERIOR
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        LayoutInflater inflador;
        View v;
        Date fechaActual= Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(fechaActual);
        switch (item.getItemId()){
            case R.id.mnuAgregarJoya:
                inflador = LayoutInflater.from(this);
                v = inflador.inflate(R.layout.layout_detalle_joya,null, false);
                ivFoto = (ImageView)v.findViewById(R.id.imJoyaDet);

                etcodigo = (EditText)v.findViewById(R.id.etIdJoyaDet);
                etnombre = (EditText)v.findViewById(R.id.etNombreJoyaDet);
                etmetal = (EditText)v.findViewById(R.id.etMetalJoyaDet);
                etpeso = (EditText)v.findViewById(R.id.etPesoJoyaDet);
                etprecio = (EditText)v.findViewById(R.id.etPrecioJoya);
                etcantidad = (EditText)v.findViewById(R.id.etStockJoya);

                sptipo = (Spinner)v.findViewById(R.id.spTipoJoya);
                adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, tipos_joya);
                sptipo.setAdapter(adapter);

                etcodigo.setText(String.valueOf(checkItemsCant("Joya")+1));

                aleProd = new AlertDialog.Builder(this);
                aleProd.setCancelable(false);
                aleProd.setView(v);
                aleProd.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Toast.makeText(getApplicationContext(),"No se guardo!",Toast.LENGTH_LONG).show();
                    }
                });
//                aleProd.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        delByCod(Integer.parseInt(etcodigo.getText().toString()),"Joya");
//                    }
//                });
                aleProd.setNeutralButton("Agregar Joya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        agregarJoya();
                        navController.navigate(R.id.navigation_home);
                    }
                });
                aleProd.show();
                break;
            case R.id.mnuAgregarVenta:
                inflador = LayoutInflater.from(this);
                v = inflador.inflate(R.layout.layout_detalle_venta,null, false);
                checkTablesDB();
                etcodventa = (EditText)v.findViewById(R.id.etCodVentaDet);
                etciclienteventa = (EditText)v.findViewById(R.id.etCIClienteDet);
                spJoyas = (Spinner) v.findViewById(R.id.etNombreJoyaDet);
                etempleadoventa = (EditText)v.findViewById(R.id.etEmpleadoDet);
                etcantidadventa = (EditText)v.findViewById(R.id.etCantidadDet);
                etcostoventa = (EditText)v.findViewById(R.id.etCostoDet);
                etfechaventa = (EditText)v.findViewById(R.id.etFechaDet);

                getJoyasNombsSpinner();
                spJoyas = (Spinner)v.findViewById(R.id.etNombreJoyaDet);
                adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, nombJoyas);
                spJoyas.setAdapter(adapter);

                System.out.println("Current time => " +formattedDate);

                etcodventa.setText(String.valueOf(checkItemsCant("Venta")+1));
                etfechaventa.setText(formattedDate);
                etempleadoventa.setText(getCIEmpleado());

                aleProd = new AlertDialog.Builder(this);
                aleProd.setCancelable(false);
                aleProd.setView(v);
                aleProd.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Toast.makeText(getApplicationContext(),"No se guardo!",Toast.LENGTH_LONG).show();
                    }
                });
//                aleProd.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        delByCod(Integer.parseInt(etcodventa.getText().toString()),"Venta");
//                    }
//                });
                aleProd.setNeutralButton("Agregar Venta", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        agregarVenta();
                        navController.navigate(R.id.navigation_dashboard);
                    }
                });
                aleProd.show();
                break;
            case R.id.mnuAgregarPedido:
                inflador = LayoutInflater.from(this);
                v = inflador.inflate(R.layout.layout_detalle_pedido,null, false);

                etcodpedido = (EditText)v.findViewById(R.id.etCodPedidoDet);
                etciclientepedido = (EditText)v.findViewById(R.id.etCIPedidoDet);
                etnombrejoyapedido = (EditText)v.findViewById(R.id.etJoyaPedDet);
                etempleadopedido = (EditText)v.findViewById(R.id.etEmpPedDet);
                etcantidadpedido = (EditText)v.findViewById(R.id.etCantidadDetPed);
                spestado = (Spinner) v.findViewById(R.id.spEstado);
                etfechaemision = (EditText)v.findViewById(R.id.etFechaEmisionDet);
                dpentrega = (DatePicker) v.findViewById(R.id.etFechaEntregaDet);

                sptipo = (Spinner)v.findViewById(R.id.spEstado);
                adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, estadoPedido);
                sptipo.setAdapter(adapter);

                System.out.println("Current time => " +formattedDate);

                etcodpedido.setText(String.valueOf(checkItemsCant("Pedido")+1));
                etempleadopedido.setText(getCIEmpleado());
                etfechaemision.setText(formattedDate);

                aleProd = new AlertDialog.Builder(this);
                aleProd.setCancelable(false);
                aleProd.setView(v);
                aleProd.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(),"No se guardo!",Toast.LENGTH_LONG).show();
                    }
                });
//                aleProd.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        delByCod(Integer.parseInt(etcodpedido.getText().toString()),"Pedido");
//                    }
//                });
                aleProd.setNeutralButton("Agregar Pedido", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        agregarPedido();
                        navController.navigate(R.id.navigation_notifications);
                    }
                });
                aleProd.show();
                break;
            case R.id.mnuAgregarCliente:
                inflador = LayoutInflater.from(this);
                v = inflador.inflate(R.layout.layout_datos_cliente,null, false);

                etcodcliente = (EditText)v.findViewById(R.id.etCodCliente);
                etnombrecliente = (EditText)v.findViewById(R.id.etNombreCliente);
                etcicliente = (EditText)v.findViewById(R.id.etCICliente);
                etdireccioncliente = (EditText)v.findViewById(R.id.etDireccionCliente);
                ettelefonocliente = (EditText)v.findViewById(R.id.etTelefonoCliente);
                listViewClientes=(ListView)v.findViewById(R.id.lvClientes);

                etcodcliente.setText(String.valueOf(checkItemsCant("Cliente")+1));

                ClienteListAdapter clienteAdapter=new ClienteListAdapter(v.getContext(),clientes);
                listViewClientes.setAdapter(clienteAdapter);
                listViewClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        etcodcliente.setEnabled(true);
                        etcodcliente.setText((String.valueOf(clientes.get(position).getCod_cliente())));
                        etnombrecliente.setText((String.valueOf(clientes.get(position).getNombre())));
                        etcicliente.setText((String.valueOf(clientes.get(position).getCi())));
                        etdireccioncliente.setText((String.valueOf(clientes.get(position).getDireccion())));
                        ettelefonocliente.setText((String.valueOf(clientes.get(position).getTelefono())));
                    }
                });

                aleProd = new AlertDialog.Builder(this);
                aleProd.setCancelable(false);
                aleProd.setView(v);
                aleProd.setNegativeButton("Guardar Cambios", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        updateCliente();
                        updateClienteList();
                    }
                });
                aleProd.setPositiveButton("Agregar Cliente", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(!etcodcliente.isEnabled()){
                            agregarCliente();
                            updateClienteList();
                        }else{
                            Toast.makeText(getApplicationContext(),"Esta editando un cliente!",Toast.LENGTH_LONG).show();
                        }
                    }
                });
//                aleProd.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        delByCod(Integer.parseInt(etcodcliente.getText().toString()),"Cliente");
//                    }
//                });
                aleProd.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(getApplicationContext(),"No se guardo!",Toast.LENGTH_LONG).show();
                    }
                });
                aleProd.show();
                break;
            case R.id.mnuAgregarEmpleado:
                inflador = LayoutInflater.from(this);
                v = inflador.inflate(R.layout.layout_datos_empleado,null, false);
                etcodempleado = (EditText)v.findViewById(R.id.etCodEmpleado);
                etciempleado = (EditText)v.findViewById(R.id.etCIEmpleado);
                etnombreempleado = (EditText)v.findViewById(R.id.etNombreEmpleado);
                etcargoempleado = (EditText)v.findViewById(R.id.etCargoEmpleado);
                ettelefonoempleado = (EditText)v.findViewById(R.id.etTelefonoEmpleado);
                etcontraempelado = (EditText)v.findViewById(R.id.etContraEmpleado);
                listViewEmpleados=(ListView)v.findViewById(R.id.lvEmpleados);

                etcodempleado.setText(String.valueOf(checkItemsCant("Empleado")+1));

                EmpleadoListAdapter empleadoAdapter=new EmpleadoListAdapter(v.getContext(),empleados);
                listViewEmpleados.setAdapter(empleadoAdapter);
                listViewEmpleados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        etcodempleado.setEnabled(true);
                        etcodempleado.setText((String.valueOf(empleados.get(position).getCod_Empleado())));
                        etnombreempleado.setText((String.valueOf(empleados.get(position).getNombre())));
                        etciempleado.setText((String.valueOf(empleados.get(position).getCI())));
                        etcargoempleado.setText((String.valueOf(empleados.get(position).getCargo())));
                        ettelefonoempleado.setText((String.valueOf(empleados.get(position).getTelefono())));
                        etcontraempelado.setText((String.valueOf(empleados.get(position).getContraseña())));
                    }
                });

                aleProd = new AlertDialog.Builder(this);
                aleProd.setCancelable(false);
                aleProd.setView(v);
                aleProd.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Toast.makeText(getApplicationContext(),"No se guardo!",Toast.LENGTH_LONG).show();
                    }
                });
                aleProd.setNegativeButton("Guardar Cambios", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        updateEmpleado();
                        updateEmpleadoList();
                    }
                });
//                aleProd.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        delByCod(Integer.parseInt(etcodempleado.getText().toString()),"Empleado");
//                    }
//                });
                aleProd.setPositiveButton("Agregar Empleado", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(!etcodempleado.isEnabled()){
                            agregarEmpleado();
                            updateEmpleadoList();
                        }else{
                            Toast.makeText(getApplicationContext(),"Esta editando un empleado!",Toast.LENGTH_LONG).show();
                        }
                    }
                });
                aleProd.show();
                break;
            case R.id.mnuSalir:
                Toast.makeText(getApplicationContext(),"Hasta Luego!",Toast.LENGTH_LONG).show();
                finish();
                System.exit(0);
                break;
        }
        return true;
    }

    public boolean checkCamposJoya(){
        if(etnombre.getText().toString().isEmpty()|| etmetal.getText().toString().isEmpty()
                ||etpeso.getText().toString().isEmpty() || etprecio.getText().toString().isEmpty()
                || etcantidad.getText().toString().isEmpty()){
            return false;
        }else{
            return true;
        }
    }
    public boolean checkCamposVenta(){
        if(etciclienteventa.getText().toString().isEmpty()|| etcantidadventa.getText().toString().isEmpty()
                || etcostoventa.getText().toString().isEmpty()){
            return false;
        }else{
            return true;
        }
    }
    public boolean checkCamposPedido(){
        if(etciclientepedido.getText().toString().isEmpty()|| etnombrejoyapedido.getText().toString().isEmpty()
                || etcantidadpedido.getText().toString().isEmpty()){
            return false;
        }else{
            return true;
        }
    }
    public boolean checkCamposEmpleado(){
        if(etciempleado.getText().toString().isEmpty()|| etnombreempleado.getText().toString().isEmpty()
                || ettelefonoempleado.getText().toString().isEmpty() || etcargoempleado.getText().toString().isEmpty()
                || etcontraempelado.getText().toString().isEmpty()){
            return false;
        }else{
            return true;
        }
    }
    public boolean checkCamposCliente(){
        if(etnombrecliente.getText().toString().isEmpty()|| etdireccioncliente.getText().toString().isEmpty()
                || ettelefonocliente.getText().toString().isEmpty() || etcicliente.getText().toString().isEmpty()){
            return false;
        }else{
            return true;
        }
    }
    public void updateClienteList(){
        clientes.clear();
        getClientesData();
    }
    public void updateEmpleadoList(){
        empleados.clear();
        getEmpleadosData();
    }


    public void delByCod(int cod,String tabla){
        AdminDataBase admin = new AdminDataBase(this,"administracion",null,1);
        SQLiteDatabase db = admin.getWritableDatabase();
        db.delete(tabla,"cod_"+tabla+"='"+cod+"'",null);
        //String sql1="DELETE FROM sqlite_sequence WHERE name = '"+tabla+"'";
        //db.execSQL(sql1);
    }
    public void updateEmpleado(){
        AdminDataBase admin = new AdminDataBase(this,"administracion",null,1);
        SQLiteDatabase db = admin.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            if(checkCamposEmpleado()){
                values.put("Nombre",etnombreempleado.getText().toString());
                values.put("CI",etciempleado.getText().toString());
                values.put("Telefono",ettelefonoempleado.getText().toString());
                values.put("Cargo",etcargoempleado.getText().toString());
                values.put("Contraseña",etcontraempelado.getText().toString());
                db.update("Empleado",values,"cod_Empleado='"+etcodempleado.getText().toString()+"'",null);
                Toast.makeText(this,"Datos de Empleado Actualizados!",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this,"Debe llenar todos los campos",Toast.LENGTH_LONG).show();
            }
        }catch (SQLException e){
            System.out.println(e);
        }
        db.close();
    }
    public void updateCliente(){
        AdminDataBase admin = new AdminDataBase(this,"administracion",null,1);
        SQLiteDatabase db = admin.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            if(checkCamposCliente()){
                values.put("Nombre",etnombrecliente.getText().toString());
                values.put("CI",etcicliente.getText().toString());
                values.put("Telefono",ettelefonocliente.getText().toString());
                values.put("Direccion",etdireccioncliente.getText().toString());
                db.update("Cliente",values,"cod_Cliente='"+etcodcliente.getText().toString()+"'",null);
                Toast.makeText(this,"Datos de Cliente Actualizados!",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this,"Debe llenar todos los campos",Toast.LENGTH_LONG).show();
            }
        }catch (SQLException e){
            System.out.println(e);
        }
        db.close();
    }
    public void agregarJoya(){
        AdminDataBase admin = new AdminDataBase(this,"administracion",null,1);
        SQLiteDatabase db = admin.getWritableDatabase();
        ContentValues values = new ContentValues();
        byte[] imagen=imageViewToByte(ivFoto);
        try {
            if(checkCamposJoya()){
                values.put("Nombre",etnombre.getText().toString());
                values.put("Metal",etmetal.getText().toString());
                values.put("Peso",Double.parseDouble(etpeso.getText().toString()));
                values.put("Precio",Double.parseDouble(etprecio.getText().toString()));
                values.put("Imagen",imagen);
                values.put("Stock",Integer.parseInt(etcantidad.getText().toString()));
                values.put("cod_Tipo_Joya",Integer.parseInt(String.valueOf(sptipo.getSelectedItemPosition())));
                Toast.makeText(getApplicationContext(),"Datos de Joya Guardados",Toast.LENGTH_LONG).show();
                long sql = db.insert("Joya",null,values);
            }else{
                Toast.makeText(getApplicationContext(),"Debe llenar todos los campos",Toast.LENGTH_LONG).show();
            }
        }catch (SQLException e){
            System.out.println(e);
        }
        db.close();
    }
    public void agregarCliente(){
        AdminDataBase admin = new AdminDataBase(this,"administracion",null,1);
        SQLiteDatabase db = admin.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            if(checkCamposCliente()){
                values.put("Nombre",etnombrecliente.getText().toString());
                values.put("Direccion",etdireccioncliente.getText().toString());
                values.put("Telefono",ettelefonocliente.getText().toString());
                values.put("CI",etcicliente.getText().toString());
                long sql = db.insert("Cliente",null,values);
                Toast.makeText(getApplicationContext(),"Datos de Cliente Guardados",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getApplicationContext(),"Debe llenar todos los campos",Toast.LENGTH_LONG).show();
            }
        }catch (SQLException e){
            System.out.println(e);
        }
        db.close();
    }
    public void agregarEmpleado(){
        AdminDataBase admin = new AdminDataBase(this,"administracion",null,1);
        SQLiteDatabase db = admin.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            if(checkCamposEmpleado()){
                values.put("cod_Empleado",etcodempleado.getText().toString());
                values.put("Nombre",etnombreempleado.getText().toString());
                values.put("CI",etciempleado.getText().toString());
                values.put("Cargo",etcargoempleado.getText().toString());
                values.put("Contraseña",etcontraempelado.getText().toString());
                values.put("Telefono",ettelefonoempleado.getText().toString());
                long sql = db.insert("Empleado",null,values);
                Toast.makeText(getApplicationContext(),"Datos de Empleado Guardados ",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getApplicationContext(),"Debe llenar todos los campos",Toast.LENGTH_LONG).show();
            }
        }catch (SQLException e){
            System.out.println(e);
        }
        db.close();
    }
    public void agregarPedido(){
        AdminDataBase admin = new AdminDataBase(this,"administracion",null,1);
        SQLiteDatabase db = admin.getWritableDatabase();
        ContentValues values = new ContentValues();
        String literalMes[]={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
        int CodCliente=getCodCliente(etciclientepedido.getText().toString());
        int CodJoya=getCodJoya(etnombrejoyapedido.getText().toString());
        String fechaEntrega=dpentrega.getDayOfMonth()+"-"+literalMes[dpentrega.getMonth()]+"-"+dpentrega.getYear();
        try {
            if(checkCamposPedido()){
                if(CodCliente!=0&&CodJoya!=0){
                    values.put("fecha_emision",etfechaemision.getText().toString());
                    values.put("fecha_entrega",fechaEntrega);
                    values.put("cantidad",Integer.parseInt(etcantidadpedido.getText().toString()));
                    if(spestado.getSelectedItemPosition()==0){
                        values.put("estatus",0);
                    }else{
                        values.put("estatus",1);
                    }
                    values.put("cod_Cliente",CodCliente);
                    values.put("cod_Empleado",cod_empleado);
                    values.put("cod_Joya",CodJoya);

                    long sql = db.insert("Pedido",null,values);
                    Toast.makeText(getApplicationContext(),"Datos de Pedido Guardados ",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Cliente no Registrados",Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(getApplicationContext(),"Debe llenar todos los campos",Toast.LENGTH_LONG).show();
            }
        }catch (SQLException e){
            System.out.println(e);
        }
        db.close();
    }
    public void agregarVenta(){
        AdminDataBase admin = new AdminDataBase(this,"administracion",null,1);
        SQLiteDatabase db = admin.getWritableDatabase();
        ContentValues valuesAdd = new ContentValues();
        ContentValues valuesUpdate = new ContentValues();
        int CodCliente=getCodCliente(etciclienteventa.getText().toString());
        int cant=0;
        try {
            Cursor sql1 = db.rawQuery("Select Stock "+
                            "from Joya "+
                            "where cod_Joya="+"'"+(Integer.parseInt(String.valueOf(spJoyas.getSelectedItemPosition()))+1)+"'",
                    null);
            if(sql1.moveToFirst()){
                cant=sql1.getInt(0);
                System.out.println(cant);
            }else{
                Toast.makeText(getApplicationContext(),"Error al obtener los tipos de joya",Toast.LENGTH_LONG).show();
            }
            if(checkCamposVenta()){
                if(CodCliente!=0 ){
                    valuesAdd.put("Fecha",etfechaventa.getText().toString());
                    valuesAdd.put("cantidad",Integer.parseInt(etcantidadventa.getText().toString()));
                    valuesAdd.put("total",Double.parseDouble(etcostoventa.getText().toString()));
                    valuesAdd.put("cod_Cliente",CodCliente);
                    valuesAdd.put("cod_Empleado",cod_empleado);
                    valuesAdd.put("cod_Joya",Integer.parseInt(String.valueOf(spJoyas.getSelectedItemPosition()))+1);
                    valuesUpdate.put("Stock",(cant-Integer.parseInt(etcantidadventa.getText().toString())));
                    System.out.println(Integer.parseInt(String.valueOf(spJoyas.getSelectedItemPosition()))+1);
                    db.update("Joya",valuesUpdate, "cod_Joya='"+(Integer.parseInt(String.valueOf(spJoyas.getSelectedItemPosition()))+1)+"'",null);
                    long sql = db.insert("Venta",null,valuesAdd);
                    Toast.makeText(getApplicationContext(),"Datos de Venta Guardados ",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Cliente no Registrados",Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(getApplicationContext(),"Debe llenar todos los campos",Toast.LENGTH_LONG).show();
            }
        }catch (SQLException e){
            System.out.println(e);
        }
        db.close();
    }
    public void getClientesData(){
        AdminDataBase admin = new AdminDataBase(this,"administracion",null,1);
        SQLiteDatabase db = admin.getWritableDatabase();
        if(!clientes.isEmpty()){
            clientes.clear();
        }
        try {
            Cursor sql = db.rawQuery("Select * "+
                            "from Cliente ",
                    null);
            if(sql.moveToFirst()){
                while (!sql.isAfterLast()) {
                    clientes.add(new Cliente(
                            sql.getInt(0),
                            sql.getString(1),
                            sql.getString(2),
                            sql.getString(3),
                            sql.getString(4)));
                    sql.moveToNext();
                }
            }else{
                Toast.makeText(this,"Error al obtener los datos de los clientes",Toast.LENGTH_LONG).show();
            }
        }catch (SQLException e){
            System.out.println(e);
        }
        db.close();
    }
    public void getEmpleadosData(){
        AdminDataBase admin = new AdminDataBase(this,"administracion",null,1);
        SQLiteDatabase db = admin.getWritableDatabase();
        if(!empleados.isEmpty()){
            empleados.clear();
        }
        try {
            Cursor sql = db.rawQuery("Select * "+
                            "from Empleado ",
                    null);
            if(sql.moveToFirst()){
                while (!sql.isAfterLast()) {
                    empleados.add(new Empleado(
                            sql.getInt(0),
                            sql.getString(1),
                            sql.getString(2),
                            sql.getString(3),
                            sql.getString(4),
                            sql.getString(5)));
                    sql.moveToNext();
                }
            }else{
                Toast.makeText(this,"Error al obtener los datos de los Empleados",Toast.LENGTH_LONG).show();
            }
        }catch (SQLException e){
            System.out.println(e);
        }
        db.close();
    }
    public String getCIEmpleado(){
        String ci="";
        AdminDataBase admin = new AdminDataBase(this,"administracion",null,1);
        SQLiteDatabase db = admin.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            Cursor sql = db.rawQuery("Select CI "+
                            "from Empleado "+
                            "where cod_Empleado=\""+ cod_empleado +"\"",
                    null);
            if(sql.moveToFirst()){
                ci=sql.getString(0);
            }else{
                Toast.makeText(getApplicationContext(),"Error al obtener datos del empleado",Toast.LENGTH_LONG).show();
            }
        }catch (SQLException e){
            System.out.println(e);
        }
        db.close();
        return ci;
    }
    public int getCodJoya(String nombre){
        int cod=0;
        AdminDataBase admin = new AdminDataBase(this,"administracion",null,1);
        SQLiteDatabase db = admin.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            Cursor sql = db.rawQuery("Select cod_Joya "+
                            "from Joya "+
                            "where Nombre=\""+ nombre +"\"",
                    null);
            if(sql.moveToFirst()){
                if(sql.getInt(0)!=0){
                    cod=sql.getInt(0);
                }else{
                    Toast.makeText(getApplicationContext(),"Error Joya no disponible",Toast.LENGTH_LONG).show();
                }
            }
        }catch (SQLException e){
            System.out.println(e);
        }
        db.close();
        return cod;
    }
    public int getCodCliente(String ci){
        int cod_Cliente=0;
        AdminDataBase admin = new AdminDataBase(this,"administracion",null,1);
        SQLiteDatabase db = admin.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            Cursor sql = db.rawQuery("Select cod_Cliente"+
                            " from Cliente"
                            +" where CI=\""+ ci +"\"",
                    null);
            if(sql.moveToFirst()){
                cod_Cliente=sql.getInt(0);
                if(sql.getInt(0)!=0){
                    Toast.makeText(getApplicationContext(),"Cod_Cliente: "+ sql.getInt(0),Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Cliente Nuevo!\nAgreguelo antes de continuar",Toast.LENGTH_LONG).show();
                }
            }
        }catch (SQLException e){
            System.out.println(e);
        }
        db.close();
        return cod_Cliente;
    }
    public int checkItemsCant(String tabla){
        int cant=0;
        AdminDataBase admin = new AdminDataBase(this,"administracion",null,1);
        SQLiteDatabase db = admin.getWritableDatabase();
        ContentValues login = new ContentValues();
        try {
            Cursor sql = db.rawQuery("Select COUNT(*)"+
                            "from "+tabla,
                    null);
            if(sql.moveToFirst()){
//                Toast.makeText(getApplicationContext(),"Cantidad "+tabla+": "+ sql.getString(0),Toast.LENGTH_LONG).show();
                cant=Integer.parseInt(sql.getString(0));
            }else{
                Toast.makeText(getApplicationContext(),"Error al buscar la cantidad en la base de datos",Toast.LENGTH_LONG).show();
            }
        }catch (SQLException e){
            System.out.println(e);
        }
        db.close();
        return cant;
    }
    public void getJoyasNombsSpinner(){
        AdminDataBase admin = new AdminDataBase(this,"administracion",null,1);
        SQLiteDatabase db = admin.getWritableDatabase();
        if(!nombJoyas.isEmpty()){
            nombJoyas.clear();
        }
        try {
            Cursor sql = db.rawQuery("Select Nombre "+
                            "from Joya ",
                    null);
            if(sql.moveToFirst()){
                while ( !sql.isAfterLast() ) {
                    System.out.println("Nombre=> "+sql.getString(0));
                    nombJoyas.add(sql.getString(0)+"");
                    sql.moveToNext();
                }
            }else{
                Toast.makeText(getApplicationContext(),"Error al obtener los tipos de joya",Toast.LENGTH_LONG).show();
            }
        }catch (SQLException e){
            System.out.println(e);
        }
        db.close();
    }
    public void getJoyaTiposSpinner(){
        AdminDataBase admin = new AdminDataBase(this,"administracion",null,1);
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
//                    System.out.println("Tipo=> "+sql.getString(0));
                    tipos_joya.add(sql.getString(0)+"");
                    sql.moveToNext();
                }
            }else{
                Toast.makeText(getApplicationContext(),"Error al obtener los tipos de joya",Toast.LENGTH_LONG).show();
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
            String authorities = this.getPackageName()+".provider";
            Uri imageUri = FileProvider.getUriForFile(this, authorities, imagen);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagen));
        }
        startActivityForResult(intent, COD_FOTO);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                case COD_FOTO:
                    MediaScannerConnection.scanFile(this, new String[]{path}, null, new MediaScannerConnection.OnScanCompletedListener() {
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
    public void checkTablesDB(){
        AdminDataBase admin = new AdminDataBase(this,"administracion",null,1);
        SQLiteDatabase db = admin.getWritableDatabase();
        ContentValues login = new ContentValues();
        try {
//            Cursor sql = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table';",null);
            Cursor sql = db.rawQuery("SELECT * FROM Pedido ;",null);
//            String sql1="DELETE FROM sqlite_sequence WHERE name = 'Pedido'";
//            db.execSQL(sql1);
            if(sql.moveToFirst()){
                while ( !sql.isAfterLast() ) {
//                    System.out.println("Table Name=> "+sql.getString(0));
                    System.out.println("COD => "+sql.getInt(0));
                    System.out.println("NOMBRE => "+sql.getString(1));
                    System.out.println("DIRECCION => "+sql.getString(2));
                    System.out.println("TELEFONO => "+sql.getString(3));
                    System.out.println("CI => "+sql.getString(4));
                    sql.moveToNext();
                }
            }else{
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
            }
        }catch (SQLException e){
            System.out.println(e);
        }
        db.close();
    }
}
