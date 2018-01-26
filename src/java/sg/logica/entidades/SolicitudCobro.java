/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.logica.entidades;

import java.sql.Timestamp;
import master.logica.entidades.Usuario;

/**
 *
 * @author Geovanny
 */
public class SolicitudCobro {

    private int idSolicitud;
    private Cuenta cuenta;
    private Usuario socio;
    private EstadoSolicitud estado;
    private double monto;
    private String tipoSolicitud;
    private Timestamp fechaRegistro;
    private Timestamp fechaBaja;
    private Timestamp fechaActualizacion;
    private Usuario sessionUsuario;
    private CuentaBancaria cuentaBancaria;

    public SolicitudCobro() {
        cuenta = new Cuenta();
        socio = new Usuario();
        sessionUsuario = new Usuario();
        estado = new EstadoSolicitud();
        cuentaBancaria = new CuentaBancaria();
    }

    /**
     * @return the idSolicitud
     */
    public int getIdSolicitud() {
        return idSolicitud;
    }

    /**
     * @param idSolicitud the idSolicitud to set
     */
    public void setIdSolicitud(int idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    /**
     * @return the cuenta
     */
    public Cuenta getCuenta() {
        return cuenta;
    }

    /**
     * @param cuenta the cuenta to set
     */
    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    /**
     * @return the socio
     */
    public Usuario getSocio() {
        return socio;
    }

    /**
     * @param socio the socio to set
     */
    public void setSocio(Usuario socio) {
        this.socio = socio;
    }

    /**
     * @return the estado
     */
    public EstadoSolicitud getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(EstadoSolicitud estado) {
        this.estado = estado;
    }

    /**
     * @return the monto
     */
    public double getMonto() {
        return monto;
    }

    /**
     * @param monto the monto to set
     */
    public void setMonto(double monto) {
        this.monto = monto;
    }

    /**
     * @return the tipoSolicitud
     */
    public String getTipoSolicitud() {
        return tipoSolicitud;
    }

    /**
     * @param tipoSolicitud the tipoSolicitud to set
     */
    public void setTipoSolicitud(String tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    /**
     * @return the fechaRegistro
     */
    public Timestamp getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     * @param fechaRegistro the fechaRegistro to set
     */
    public void setFechaRegistro(Timestamp fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    /**
     * @return the fechaBaja
     */
    public Timestamp getFechaBaja() {
        return fechaBaja;
    }

    /**
     * @param fechaBaja the fechaBaja to set
     */
    public void setFechaBaja(Timestamp fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    /**
     * @return the fechaActualizacion
     */
    public Timestamp getFechaActualizacion() {
        return fechaActualizacion;
    }

    /**
     * @param fechaActualizacion the fechaActualizacion to set
     */
    public void setFechaActualizacion(Timestamp fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    /**
     * @return the sessionUsuario
     */
    public Usuario getSessionUsuario() {
        return sessionUsuario;
    }

    /**
     * @param sessionUsuario the sessionUsuario to set
     */
    public void setSessionUsuario(Usuario sessionUsuario) {
        this.sessionUsuario = sessionUsuario;
    }

    /**
     * @return the cuentaBancaria
     */
    public CuentaBancaria getCuentaBancaria() {
        return cuentaBancaria;
    }

    /**
     * @param cuentaBancaria the cuentaBancaria to set
     */
    public void setCuentaBancaria(CuentaBancaria cuentaBancaria) {
        this.cuentaBancaria = cuentaBancaria;
    }

}
