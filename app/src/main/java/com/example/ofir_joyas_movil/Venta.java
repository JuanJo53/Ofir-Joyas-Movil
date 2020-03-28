package com.example.ofir_joyas_movil;

import java.util.Date;

public class Venta {
    private int cod_vent;
    private int cod_cliente;
    private int cod_empleado;
    private int cod_joya;
    private Date fecha_emision;

    public Venta(int cod_vent, int cod_cliente, int cod_empleado, int cod_joya, Date fecha_emision) {
        this.cod_vent = cod_vent;
        this.cod_cliente = cod_cliente;
        this.cod_empleado = cod_empleado;
        this.cod_joya = cod_joya;
        this.fecha_emision = fecha_emision;
    }

    public int getCod_vent() {
        return cod_vent;
    }

    public void setCod_vent(int cod_vent) {
        this.cod_vent = cod_vent;
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

    public Date getFecha_emision() {
        return fecha_emision;
    }

    public void setFecha_emision(Date fecha_emision) {
        this.fecha_emision = fecha_emision;
    }
}
