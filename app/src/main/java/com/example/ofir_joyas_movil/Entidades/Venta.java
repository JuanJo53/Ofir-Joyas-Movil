package com.example.ofir_joyas_movil.Entidades;

import java.util.Date;

public class Venta {
    private int cod_vent;
    private Date fecha_emision;
    private int cantidad;
    private Double total;
    private int cod_cliente;
    private int cod_empleado;
    private int cod_joya;

    public Venta(int cod_vent, Date fecha_emision, int cantidad, Double total, int cod_cliente, int cod_empleado, int cod_joya) {
        this.cod_vent = cod_vent;
        this.fecha_emision = fecha_emision;
        this.cantidad = cantidad;
        this.total = total;
        this.cod_cliente = cod_cliente;
        this.cod_empleado = cod_empleado;
        this.cod_joya = cod_joya;
    }

    public int getCod_vent() {
        return cod_vent;
    }

    public void setCod_vent(int cod_vent) {
        this.cod_vent = cod_vent;
    }

    public Date getFecha_emision() {
        return fecha_emision;
    }

    public void setFecha_emision(Date fecha_emision) {
        this.fecha_emision = fecha_emision;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
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
