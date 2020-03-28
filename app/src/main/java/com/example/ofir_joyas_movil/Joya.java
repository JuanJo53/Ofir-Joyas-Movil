package com.example.ofir_joyas_movil;

public class Joya {
    private int cod_joya;
    private String nombre;
    private String Metal;
    private Double Peso;
    private Double Precio;
    private int Stock;

    public Joya(int cod_joya, String nombre, String metal, Double peso, Double precio, int stock) {
        this.cod_joya = cod_joya;
        this.nombre = nombre;
        Metal = metal;
        Peso = peso;
        Precio = precio;
        Stock = stock;
    }

    public int getCod_joya() {
        return cod_joya;
    }

    public void setCod_joya(int cod_joya) {
        this.cod_joya = cod_joya;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
}
