package com.example.ofir_joyas_movil.Entidades;

public class Empleado {
    private int cod_Empleado;
    private String Nombre;
    private String CI;
    private String Cargo;
    private String Contraseña;
    private String Telefono;

    public Empleado(int cod_Empleado, String nombre, String CI, String cargo, String contraseña, String telefono) {
        this.cod_Empleado = cod_Empleado;
        this.Nombre = nombre;
        this.CI = CI;
        this.Cargo = cargo;
        this.Contraseña = contraseña;
        this.Telefono = telefono;
    }

    public int getCod_Empleado() {
        return cod_Empleado;
    }

    public void setCod_Empleado(int cod_Empleado) {
        this.cod_Empleado = cod_Empleado;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getCI() {
        return CI;
    }

    public void setCI(String CI) {
        this.CI = CI;
    }

    public String getCargo() {
        return Cargo;
    }

    public void setCargo(String cargo) {
        Cargo = cargo;
    }

    public String getContraseña() {
        return Contraseña;
    }

    public void setContraseña(String contraseña) {
        Contraseña = contraseña;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }
}
