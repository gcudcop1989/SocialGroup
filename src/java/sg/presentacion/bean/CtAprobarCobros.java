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
    private SolicitudCobro objSolicitud;
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;
    private Usuario sessionUsuario;

    public CtAprobarCobros() {
        objSolicitud = new SolicitudCobro();
        sessionUsuario = new Usuario();
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
        obtenerSolicitides();
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

}
