package com.example.ofir_joyas_movil;

import java.util.Date;

public class Pedido {
    private int cod_pedido;
    private Date fecha_emision;
    private Date fecha_entrega;
    private String descripcion;
    private int cod_cliente;
    private int cod_empleado;
    private int cod_joya;

    public Pedido(int cod_pedido, Date fecha_emision, Date fecha_entrega, String descripcion, int cod_cliente, int cod_empleado, int cod_joya) {
        this.cod_pedido = cod_pedido;
        this.fecha_emision = fecha_emision;
        this.fecha_entrega = fecha_entrega;
        this.descripcion = descripcion;
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

    public Date getFecha_emision() {
        return fecha_emision;
    }

    public void setFecha_emision(Date fecha_emision) {
        this.fecha_emision = fecha_emision;
    }

    public Date getFecha_entrega() {
        return fecha_entrega;
    }

    public void setFecha_entrega(Date fecha_entrega) {
        this.fecha_entrega = fecha_entrega;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
