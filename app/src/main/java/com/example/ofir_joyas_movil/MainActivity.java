package com.example.ofir_joyas_movil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView tvusername, tvpassword;
    Button btningresar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvusername=(TextView)findViewById(R.id.tvUsername);
        tvpassword=(TextView)findViewById(R.id.tvPassword);
        btningresar=(Button)findViewById(R.id.btnIngresar);

        tvusername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!credenciaslsEmpty()){
                    btningresar.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tvpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!credenciaslsEmpty()){
                    btningresar.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
//        AdminDataBase admin = new AdminDataBase(this,"administracion",null,1);
//        SQLiteDatabase db = admin.getWritableDatabase();
//        db = admin.getWritableDatabase();
//        db.execSQL("INSERT INTO Empleado (cod_Empleado,Nombre,CI,Cargo,Contraseña,Telefono)" +
//                "VALUES (1,'Joanna Martinez','12356789','Administrador','123456','60618984')");
//        db.close();

    }

    public boolean credenciaslsEmpty(){
        boolean verf=false;
        if(tvpassword.getText().toString().isEmpty()||tvusername.getText().toString().isEmpty()){
            verf=true;
        }
        return verf;
    }

    public void ocIngresar(View view){
        String user = tvusername.getText().toString();
        String pass = tvpassword.getText().toString();
        AdminDataBase admin = new AdminDataBase(this,"administracion",null,1);
        SQLiteDatabase db = admin.getWritableDatabase();
        ContentValues login = new ContentValues();

       try {
//         Cursor sql = db.rawQuery("SELECT * From Empleado",null);
           Cursor sql = db.rawQuery("Select Nombre, Contraseña " +
                           "from Empleado " +
                           "where Nombre="+"'"+user+"' "+
                           " and Contraseña="+"'"+pass+"'",
                   null);
           if(sql.moveToFirst()){
               Toast.makeText(getApplicationContext(),"Bienvenido "+ sql.getString(0),Toast.LENGTH_LONG).show();
               Intent intent = new Intent(this,bot_nav_activity.class);
               startActivity(intent);
           }else{
               Toast.makeText(getApplicationContext(),"No Logeado",Toast.LENGTH_LONG).show();
           }
       }catch (SQLException e){
           System.out.println(e);
       }
        db.close();
    }
}
