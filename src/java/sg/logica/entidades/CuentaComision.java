package sg.logica.entidades;

import java.sql.Timestamp;


public class CuentaComision {
       private int idComision;
    private Cuenta cuenta;
    private String estadoLogico;
    private double comisionDirecta;
    private double comisionResidual;
    private double totalComision;
    private Timestamp fechaRegistro;
    private Timestamp fechaActualizacion;
    private Timestamp fechaBaja;

    public CuentaComision() {
        cuenta = new Cuenta();
    }

    /**
     * @return the idComision
     */
    public int getIdComision() {
        return idComision;
    }

    /**
     * @param idComision the idComision to set
     */
    public void setIdComision(int idComision) {
        this.idComision = idComision;
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
     * @return the estadoLogico
     */
    public String getEstadoLogico() {
        return estadoLogico;
    }

    /**
     * @param estadoLogico the estadoLogico to set
     */
    public void setEstadoLogico(String estadoLogico) {
        this.estadoLogico = estadoLogico;
    }

    /**
     * @return the comisionDirecta
     */
    public double getComisionDirecta() {
        return comisionDirecta;
    }

    /**
     * @param comisionDirecta the comisionDirecta to set
     */
    public void setComisionDirecta(double comisionDirecta) {
        this.comisionDirecta = comisionDirecta;
    }

    /**
     * @return the comisionResidual
     */
    public double getComisionResidual() {
        return comisionResidual;
    }

    /**
     * @param comisionResidual the comisionResidual to set
     */
    public void setComisionResidual(double comisionResidual) {
        this.comisionResidual = comisionResidual;
    }

    /**
     * @return the totalComision
     */
    public double getTotalComision() {
        return totalComision;
    }

    /**
     * @param totalComision the totalComision to set
     */
    public void setTotalComision(double totalComision) {
        this.totalComision = totalComision;
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
    
    
    
}
