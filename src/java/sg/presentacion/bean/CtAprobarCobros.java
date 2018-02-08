/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.presentacion.bean;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import master.logica.entidades.Usuario;
import master.logica.funciones.FUsuario;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.DefaultRequestContext;
import recursos.Util;
import sg.logica.entidades.SolicitudCobro;
import sg.logica.funciones.FSolicitudCobro;

/**
 *
 * @author Geovanny
 */
@ManagedBean
@ViewScoped
public class CtAprobarCobros implements Serializable {

    private List<SolicitudCobro> lstSolicitudes;
    private List<SolicitudCobro> lstSolRechazadas;
    private SolicitudCobro objSolicitud;
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;
    private Usuario sessionUsuario;
    private String observaciones;
    private String msg;

    public CtAprobarCobros() {
        objSolicitud = new SolicitudCobro();
        sessionUsuario = new Usuario();
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
        obtenerSolicitides();
        obtenerSolRechazadas();
    }

    @PostConstruct
    public void init() {
        obtenerSession();
    }

    public void obtenerSession() {
        try {
            int intIdUsuario = (int) getHttpServletRequest().getSession().getAttribute("idUsuario");
            setSessionUsuario(FUsuario.obtenerUsuarioDadoCodigo(intIdUsuario));
            System.out.println("Usuario Logueado: " + getSessionUsuario().getApellidos() + "id: " + getSessionUsuario().getIdUsuario());
        } catch (Exception e) {
            System.out.println("public void obtenerSession() dice: " + e.getMessage());
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public void obtenerSolicitides() {
        try {
            setLstSolicitudes(FSolicitudCobro.obteneSolicitudesDadoEstado(1));
        } catch (Exception e) {
            System.out.println(" public void obtenerSolicitides() dice: " + e.getMessage());
        }
    }

    public void obtenerSolRechazadas() {
        try {
            setLstSolRechazadas(FSolicitudCobro.obteneSolicitudesDadoEstado(5));
        } catch (Exception e) {
            System.out.println(" public void obtenerSolRechazadas() dice: " + e.getMessage());
        }
    }

    public void aprobarSolicitud() {
        try {

            System.out.println("datos: SOLICITUD " + getObjSolicitud().getIdSolicitud()
                    + ", OBSERVACIONES: " + getObservaciones()
                    + ", USUARIO " + getSessionUsuario().getIdUsuario());

            setMsg(FSolicitudCobro.aprobarSolicitud(getObjSolicitud().getIdSolicitud(), getObservaciones(), getSessionUsuario().getIdUsuario()));
            Util.addSuccessMessage(getMsg());
            setObjSolicitud(new SolicitudCobro());
            setObservaciones("");
            obtenerSolicitides();
            resetearDataTable("frmCobros:tblSolicitudes");
            DefaultRequestContext.getCurrentInstance().execute("PF('wdlgRevisar').hide()");
        } catch (Exception e) {
            System.out.println(" public void aprobarSolicitud() dice: " + e.getMessage());
            Util.addErrorMessage(e.getMessage());

        }
    }

    public void rechazarSolicitud() {
        try {
            setMsg(FSolicitudCobro.rechazarSolicitud(getObjSolicitud().getIdSolicitud(), getObservaciones(), getSessionUsuario().getIdUsuario()));
            Util.addSuccessMessage(getMsg());
            setObjSolicitud(new SolicitudCobro());
            setObservaciones("");
            obtenerSolicitides();
            resetearDataTable("frmCobros:tblSolicitudes");
            DefaultRequestContext.getCurrentInstance().execute("PF('wdlgRevisar').hide()");
        } catch (Exception e) {
            Util.addErrorMessage(e.getMessage());
            System.out.println(" public void aprobarSolicitud() dice: " + e.getMessage());
        }
    }

    public void resetearDataTable(String id) {
        DataTable table = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent(id);
        table.reset();
    }

    /**
     * @return the lstSolicitudes
     */
    public List<SolicitudCobro> getLstSolicitudes() {
        return lstSolicitudes;
    }

    /**
     * @param lstSolicitudes the lstSolicitudes to set
     */
    public void setLstSolicitudes(List<SolicitudCobro> lstSolicitudes) {
        this.lstSolicitudes = lstSolicitudes;
    }

    /**
     * @return the objSolicitud
     */
    public SolicitudCobro getObjSolicitud() {
        return objSolicitud;
    }

    /**
     * @param objSolicitud the objSolicitud to set
     */
    public void setObjSolicitud(SolicitudCobro objSolicitud) {
        this.objSolicitud = objSolicitud;
    }

    /**
     * @return the httpServletRequest
     */
    public HttpServletRequest getHttpServletRequest() {
        return httpServletRequest;
    }

    /**
     * @param httpServletRequest the httpServletRequest to set
     */
    public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    /**
     * @return the faceContext
     */
    public FacesContext getFaceContext() {
        return faceContext;
    }

    /**
     * @param faceContext the faceContext to set
     */
    public void setFaceContext(FacesContext faceContext) {
        this.faceContext = faceContext;
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
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @param msg the msg to set
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * @return the lstSolRechazadas
     */
    public List<SolicitudCobro> getLstSolRechazadas() {
        return lstSolRechazadas;
    }

    /**
     * @param lstSolRechazadas the lstSolRechazadas to set
     */
    public void setLstSolRechazadas(List<SolicitudCobro> lstSolRechazadas) {
        this.lstSolRechazadas = lstSolRechazadas;
    }

}
