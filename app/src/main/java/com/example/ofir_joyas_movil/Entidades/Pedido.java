package com.example.ofir_joyas_movil.Entidades;

import java.util.Date;

public class Pedido {
    private int cod_pedido;
    private String fecha_emision;
    private String fecha_entrega;
    private int cantidad;
    private int estado;
    private int cod_cliente;
    private int cod_empleado;
    private int cod_joya;

    public Pedido(int cod_pedido, String fecha_emision, String fecha_entrega, int cantidad, int estado, int cod_cliente, int cod_empleado, int cod_joya) {
        this.cod_pedido = cod_pedido;
        this.fecha_emision = fecha_emision;
        this.fecha_entrega = fecha_entrega;
        this.cantidad = cantidad;
        this.estado = estado;
        this.cod_cliente = cod_cliente;
        this.cod_empleado = cod_empleado;
        this.cod_joya = cod_joya;
    }

    public int getCod_pedido() {
        return cod_pedido;
    }

    public void setCod_pedido(int cod_pedido) {
        this.cod_pedido = cod_pedido;
    }

    public String getFecha_emision() {
        return fecha_emision;
    }

    public void setFecha_emision(String fecha_emision) {
        this.fecha_emision = fecha_emision;
    }

    public String getFecha_entrega() {
        return fecha_entrega;
    }

    public void setFecha_entrega(String fecha_entrega) {
        this.fecha_entrega = fecha_entrega;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getCod_cliente() {
        return cod_cliente;
    }

    public void setCod_cliente(int cod_cliente) {
        this.cod_cliente = cod_cliente;
    }

    public int getCod_empleado() {
        return cod_empleado;
    }

    public void setCod_empleado(int cod_empleado) {
        this.cod_empleado = cod_empleado;
    }

    public int getCod_joya() {
        return cod_joya;
    }

    public void setCod_joya(int cod_joya) {
        this.cod_joya = cod_joya;
    }
}
