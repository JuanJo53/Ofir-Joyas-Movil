package com.example.ofir_joyas_movil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AdminDataBase extends SQLiteOpenHelper{

    public AdminDataBase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON");
        //Creacion de la Tabla Empleado
        String dbEmpleado="create table Empleado("+
                "cod_Empleado int PRIMARY KEY,"+
                "Nombre text,"+
                "CI text,"+
                "Cargo text,"+
                "Contraseña text,"+
                "Telefono text)";
        db.execSQL(dbEmpleado);
        //Creacion de la Tabla Cliente
        String dbCliente="create table Cliente("+
                "cod_Cliente int PRIMARY KEY AUTOINCREMENT,"+
                "Nombre text,"+
                "Direccion text,"+
                "telefono text,"+
                "CI text)";
        db.execSQL(dbCliente);
        //Creacion de la Tabla Tipo_Joya
        String dbTipoJoya="create table Tipo_Joya("+
                "cod_Tipo_Joya int PRIMARY KEY AUTOINCREMENT,"+
                "tipo text)";
        db.execSQL(dbTipoJoya);
        //Creacion de la Tabla Joya
        String dbJoya="create table Joya("+
                "cod_Joya int PRIMARY KEY AUTOINCREMENT,"+
                "Nombre text,"+
                "Metal text,"+
                "Peso real,"+
                "Precio real,"+
                "Stock int,"+
                "cod_Tipo_Joya int,"+
                "FOREIGN KEY(cod_Tipo_Joya) REFERENCES Tipo_Joya(cod_Tipo_Joya))";
        db.execSQL(dbJoya);
        //Creacion de la Tabla Venta
        String dbVenta="create table Venta("+
                "cod_Joya int PRIMARY KEY AUTOINCREMENT,"+
                "fecha date,"+
                "cantidad int,"+
                "total real,"+
                "cod_Cliente int,"+
                "cod_Empleado int,"+
                "cod_Joya int,"+
                "FOREIGN KEY(cod_Cliente) REFERENCES Cliente(cod_Cliente),"+
                "FOREIGN KEY(cod_Empleado) REFERENCES Empleado(cod_Empleado),"+
                "FOREIGN KEY(cod_Joya) REFERENCES Joya(cod_Joya))";
        db.execSQL(dbVenta);
        //Creacion de la Tabla Pedido
        String dbPedido="create table Pedido("+
                "cod_Pedido int PRIMARY KEY AUTOINCREMENT,"+
                "fecha_emision date,"+
                "fecha_entrega date,"+
                "cantidad int,"+
                "estatus int,"+
                "cod_Cliente int,"+
                "cod_Empleado int,"+
                "cod_Joya int,"+
                "FOREIGN KEY(cod_Cliente) REFERENCES Cliente(cod_Cliente),"+
                "FOREIGN KEY(cod_Empleado) REFERENCES Empleado(cod_Empleado),"+
                "FOREIGN KEY(cod_Joya) REFERENCES Joya(cod_Joya))";
        db.execSQL(dbPedido);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
