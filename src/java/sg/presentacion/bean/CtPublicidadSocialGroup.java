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
import sg.logica.entidades.Cuenta;
import sg.logica.entidades.Publicidad;
import sg.logica.funciones.FPublicidad;

/**
 *
 * @author Geovanny Cudco
 */
@ManagedBean
@ViewScoped
public class CtPublicidadSocialGroup implements Serializable {

    private List<Publicidad> lstSolicitudes;
    private List<Publicidad> lstSolicitudesRech;
    private List<Publicidad> anunciosActivos;
    private Publicidad objPublicidad;
    private Publicidad publicidadSel;
    private String msg;
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;
    private Usuario sessionUsuario;
    private List<Cuenta> lstCuentas;
    private int idCuenta;
    private String observaciones;

    public CtPublicidadSocialGroup() {
        publicidadSel = new Publicidad();
        objPublicidad = new Publicidad();
        sessionUsuario = new Usuario();
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
    }

    @PostConstruct
    public void init() {
        obtenerSession();
        obtenerAnuncios();
        obtenerAnunciosActivos();
        obtenerAnunciosRechazados();
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

    public void obtenerAnuncios() {
        try {
            setLstSolicitudes(FPublicidad.obtenerAnunciosDadoEstado(1));
        } catch (Exception e) {
            System.out.println("public void obtenerAnuncios() dice: " + e.getMessage());
        }
    }

    public void obtenerAnunciosActivos() {
        try {
            setAnunciosActivos(FPublicidad.obtenerAnunciosDadoEstado(2));
        } catch (Exception e) {
            System.out.println("public void obtenerAnunciosActivos() dice: " + e.getMessage());
        }
    }

    public void obtenerAnunciosRechazados() {
        try {
            setLstSolicitudesRech(FPublicidad.obtenerAnunciosDadoEstado(5));
        } catch (Exception e) {
            System.out.println("public void obtenerAnunciosRechazados() dice: " + e.getMessage());
        }
    }

    public void aprobarPublicidad() {
        try {
            getPublicidadSel().setObservaciones(getObservaciones());
            getPublicidadSel().setSessionUsuario(getSessionUsuario());
            setMsg(FPublicidad.aprobarPublicidad(getPublicidadSel()));
            Util.addSuccessMessage(getMsg());
            obtenerAnuncios();
            setPublicidadSel(new Publicidad());
            resetearDataTable("frmPrincipal:tblSolicitudes");
            DefaultRequestContext.getCurrentInstance().execute("PF('wdlgAprobar').hide()");
        } catch (Exception e) {
            System.out.println("public void aprobarPublicidad() dice: " + e.getMessage());
            Util.addErrorMessage(e.getMessage());
        }
    }

    public void rechazarPublicidad() {
        try {
            getPublicidadSel().setObservaciones(getObservaciones());
            getPublicidadSel().setSessionUsuario(getSessionUsuario());
            setMsg(FPublicidad.rechazarPublicidad(getPublicidadSel()));
            Util.addSuccessMessage(getMsg());
            obtenerAnuncios();
            setPublicidadSel(new Publicidad());
            resetearDataTable("frmPrincipal:tblSolicitudes");
            DefaultRequestContext.getCurrentInstance().execute("PF('wdlgAprobar').hide()");
        } catch (Exception e) {
            System.out.println("public void rechazarPublicidad() dice: " + e.getMessage());
            Util.addErrorMessage(e.getMessage());
        }
    }

    public void resetearDataTable(String id) {
        DataTable table = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent(id);
        table.reset();
    }

    /**
     * @return the lstSolicitudes
     */
    public List<Publicidad> getLstSolicitudes() {
        return lstSolicitudes;
    }

    /**
     * @param lstSolicitudes the lstSolicitudes to set
     */
    public void setLstSolicitudes(List<Publicidad> lstSolicitudes) {
        this.lstSolicitudes = lstSolicitudes;
    }

    /**
     * @return the objPublicidad
     */
    public Publicidad getObjPublicidad() {
        return objPublicidad;
    }

    /**
     * @param objPublicidad the objPublicidad to set
     */
    public void setObjPublicidad(Publicidad objPublicidad) {
        this.objPublicidad = objPublicidad;
    }

    /**
     * @return the publicidadSel
     */
    public Publicidad getPublicidadSel() {
        return publicidadSel;
    }

    /**
     * @param publicidadSel the publicidadSel to set
     */
    public void setPublicidadSel(Publicidad publicidadSel) {
        this.publicidadSel = publicidadSel;
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
     * @return the lstCuentas
     */
    public List<Cuenta> getLstCuentas() {
        return lstCuentas;
    }

    /**
     * @param lstCuentas the lstCuentas to set
     */
    public void setLstCuentas(List<Cuenta> lstCuentas) {
        this.lstCuentas = lstCuentas;
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
     * @return the lstSolicitudesRech
     */
    public List<Publicidad> getLstSolicitudesRech() {
        return lstSolicitudesRech;
    }

    /**
     * @param lstSolicitudesRech the lstSolicitudesRech to set
     */
    public void setLstSolicitudesRech(List<Publicidad> lstSolicitudesRech) {
        this.lstSolicitudesRech = lstSolicitudesRech;
    }

    /**
     * @return the anunciosActivos
     */
    public List<Publicidad> getAnunciosActivos() {
        return anunciosActivos;
    }

    /**
     * @param anunciosActivos the anunciosActivos to set
     */
    public void setAnunciosActivos(List<Publicidad> anunciosActivos) {
        this.anunciosActivos = anunciosActivos;
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

}
