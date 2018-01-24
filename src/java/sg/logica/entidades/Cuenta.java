package sg.logica.entidades;

import java.sql.Timestamp;
import master.logica.entidades.Usuario;

public class Cuenta {

    private int idCuenta;
    private String codigo;
    private Usuario persona;
    private Cuenta referidoCuenta;
    private Pif pif;
    private EstadoCuenta estadoCuenta;
    private Timestamp fechaRegistro;
    private Timestamp fechaActivacion;
    private Timestamp fechaCaducidad;
    private Usuario sessionUsuario;
    private Usuario lider;
    //
    private double comisionDirecta;
    private double comisionResidual;
    private double totalComision;

    // tiempo restante para caducar
    private int mesesVigencia;
    private int diasVigencia;
    private int horasVigencia;

    public Cuenta() {
        estadoCuenta = new EstadoCuenta();
        lider = new Usuario();
        pif = new Pif();
        persona = new Usuario();
        sessionUsuario = new Usuario();
    }

    /**
     * @return the idCuenta
     */
    public int getIdCuenta() {
        return idCuenta;
    }

    /**
     * @param idCuenta the idCuenta to set
     */
    public void setIdCuenta(int idCuenta) {
        this.idCuenta = idCuenta;
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the persona
     */
    public Usuario getPersona() {
        return persona;
    }

    /**
     * @param persona the persona to set
     */
    public void setPersona(Usuario persona) {
        this.persona = persona;
    }

    /**
     * @return the referidoCuenta
     */
    public Cuenta getReferidoCuenta() {
        return referidoCuenta;
    }

    /**
     * @param referidoCuenta the referidoCuenta to set
     */
    public void setReferidoCuenta(Cuenta referidoCuenta) {
        this.referidoCuenta = referidoCuenta;
    }

    /**
     * @return the pif
     */
    public Pif getPif() {
        return pif;
    }

    /**
     * @param pif the pif to set
     */
    public void setPif(Pif pif) {
        this.pif = pif;
    }

    /**
     * @return the estadoCuenta
     */
    public EstadoCuenta getEstadoCuenta() {
        return estadoCuenta;
    }

    /**
     * @param estadoCuenta the estadoCuenta to set
     */
    public void setEstadoCuenta(EstadoCuenta estadoCuenta) {
        this.estadoCuenta = estadoCuenta;
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
     * @return the fechaActivacion
     */
    public Timestamp getFechaActivacion() {
        return fechaActivacion;
    }

    /**
     * @param fechaActivacion the fechaActivacion to set
     */
    public void setFechaActivacion(Timestamp fechaActivacion) {
        this.fechaActivacion = fechaActivacion;
    }

    /**
     * @return the fechaCaducidad
     */
    public Timestamp getFechaCaducidad() {
        return fechaCaducidad;
    }

    /**
     * @param fechaCaducidad the fechaCaducidad to set
     */
    public void setFechaCaducidad(Timestamp fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
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
     * @return the lider
     */
    public Usuario getLider() {
        return lider;
    }

    /**
     * @param lider the lider to set
     */
    public void setLider(Usuario lider) {
        this.lider = lider;
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
     * @return the mesesVigencia
     */
    public int getMesesVigencia() {
        return mesesVigencia;
    }

    /**
     * @param mesesVigencia the mesesVigencia to set
     */
    public void setMesesVigencia(int mesesVigencia) {
        this.mesesVigencia = mesesVigencia;
    }

    /**
     * @return the diasVigencia
     */
    public int getDiasVigencia() {
        return diasVigencia;
    }

    /**
     * @param diasVigencia the diasVigencia to set
     */
    public void setDiasVigencia(int diasVigencia) {
        this.diasVigencia = diasVigencia;
    }

    /**
     * @return the horasVigencia
     */
    public int getHorasVigencia() {
        return horasVigencia;
    }

    /**
     * @param horasVigencia the horasVigencia to set
     */
    public void setHorasVigencia(int horasVigencia) {
        this.horasVigencia = horasVigencia;
    }

}
