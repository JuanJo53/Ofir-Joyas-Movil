package com.example.ofir_joyas_movil.Entidades;

public class Joya {
    private int cod_joya;
    private String Nombre;
    private String Metal;
    private Double Peso;
    private Double Precio;
    private int Stock;
    private byte[] Foto;
    private int Cod_Tipo;

    public Joya(int cod_joya, String nombre, String metal, Double peso, Double precio, int stock, byte[] foto, int cod_tipo) {
        this.cod_joya = cod_joya;
        Nombre = nombre;
        Metal = metal;
        Peso = peso;
        Precio = precio;
        Foto = foto;
        Stock = stock;
        Cod_Tipo = cod_tipo;
    }

    public int getCod_joya() {
        return cod_joya;
    }

    public void setCod_joya(int cod_joya) {
        this.cod_joya = cod_joya;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getMetal() {
        return Metal;
    }

    public void setMetal(String metal) {
        Metal = metal;
    }

    public Double getPeso() {
        return Peso;
    }

    public void setPeso(Double peso) {
        Peso = peso;
    }

    public Double getPrecio() {
        return Precio;
    }

    public void setPrecio(Double precio) {
        Precio = precio;
    }

    public int getStock() {
        return Stock;
    }

    public void setStock(int stock) {
        Stock = stock;
    }

    public byte[] getFoto() {
        return Foto;
    }

    public void setFoto(byte[] foto) {
        Foto = foto;
    }

    public int getCod_tipo() {
        return Cod_Tipo;
    }

    public void setCod_tipo(int cod_tipo) {
        this.Cod_Tipo = cod_tipo;
    }
}
