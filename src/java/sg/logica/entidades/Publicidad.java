package sg.logica.entidades;

import java.sql.Timestamp;
import master.logica.entidades.Usuario;

public class Publicidad {

    private int idPublicidad;
    private Cuenta cuenta;
    private EstadoSolicitud estado;
    private String publicidad;
    private String descripcion;
    private String observaciones;
    private String adjunto;
    private Timestamp fechaSolicitud;
    private Timestamp fechaAprobacion;
    private Timestamp fechaFinalizacion;
    private Usuario sessionUsuario;
    private FormaPago formaPago;

    public Publicidad() {
        formaPago = new FormaPago();
        cuenta = new Cuenta();
        estado = new EstadoSolicitud();
        sessionUsuario = new Usuario();
    }

    /**
     * @return the idPublicidad
     */
    public int getIdPublicidad() {
        return idPublicidad;
    }

    /**
     * @param idPublicidad the idPublicidad to set
     */
    public void setIdPublicidad(int idPublicidad) {
        this.idPublicidad = idPublicidad;
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
     * @return the publicidad
     */
    public String getPublicidad() {
        return publicidad;
    }

    /**
     * @param publicidad the publicidad to set
     */
    public void setPublicidad(String publicidad) {
        this.publicidad = publicidad;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the observaciones
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * @param observaciones the observaciones to set
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    /**
     * @return the adjunto
     */
    public String getAdjunto() {
        return adjunto;
    }

    /**
     * @param adjunto the adjunto to set
     */
    public void setAdjunto(String adjunto) {
        this.adjunto = adjunto;
    }

    /**
     * @return the fechaSolicitud
     */
    public Timestamp getFechaSolicitud() {
        return fechaSolicitud;
    }

    /**
     * @param fechaSolicitud the fechaSolicitud to set
     */
    public void setFechaSolicitud(Timestamp fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    /**
     * @return the fechaAprobacion
     */
    public Timestamp getFechaAprobacion() {
        return fechaAprobacion;
    }

    /**
     * @param fechaAprobacion the fechaAprobacion to set
     */
    public void setFechaAprobacion(Timestamp fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }

    /**
     * @return the fechaFinalizacion
     */
    public Timestamp getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    /**
     * @param fechaFinalizacion the fechaFinalizacion to set
     */
    public void setFechaFinalizacion(Timestamp fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
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
     * @return the formaPago
     */
    public FormaPago getFormaPago() {
        return formaPago;
    }

    /**
     * @param formaPago the formaPago to set
     */
    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
    }

}
