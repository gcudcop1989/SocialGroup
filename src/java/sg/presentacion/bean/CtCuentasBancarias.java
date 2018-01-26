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
import sg.logica.entidades.CuentaBancaria;
import sg.logica.entidades.SolicitudCobro;
import sg.logica.funciones.FCuentaBancaria;
import recursos.Util;
import org.primefaces.context.DefaultRequestContext;
/**
 *
 * @author Geovanny Cudco
 */
@ManagedBean
@ViewScoped
public class CtCuentasBancarias implements Serializable {

    private List<CuentaBancaria> lstCuentas;
    private CuentaBancaria objCuenta;
    private CuentaBancaria cuentaSel;
    private String msg;
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;
    private Usuario sessionUsuario;

    public CtCuentasBancarias() {
        sessionUsuario = new Usuario();
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
    }

    @PostConstruct
    public void init() {
        obtenerSession();
        obtenerCuentas();
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

    public void obtenerCuentas() {
        try {
            lstCuentas = FCuentaBancaria.obtenerCtasDadoSocio(sessionUsuario.getIdUsuario());
        } catch (Exception e) {
            System.out.println("public void obtenerCuentas() dice: " + e.getMessage());
        }
    }

    public void registrarCuentaBanc() {
        try {
            String msg = FCuentaBancaria.insertarCteBancaria(getObjCuenta(), sessionUsuario.getIdUsuario());
            objCuenta= new CuentaBancaria();
            obtenerCuentas();
            Util.addSuccessMessage(msg);   
            resetearDataTable("frmCuentas:tblCuentas");
            DefaultRequestContext.getCurrentInstance().execute("PF('dlgInsertarCteBan').hide()");

        } catch (Exception e) { 
             System.out.println("public void RegistrarCtebancaria dice: " + e.getMessage());
            Util.addErrorMessage(e.getMessage());
        }
    }
    
     //<editor-fold defaultstate="collapsed" desc="Editar PIF">
    public void editarCteBan() {
        try {
            
            //cuentaSel.setSessionUsuario(sessionUsuario);
            String msg = FCuentaBancaria.editarCteBancaria(cuentaSel, sessionUsuario.getIdUsuario());
            Util.addSuccessMessage(msg);
            cuentaSel = new CuentaBancaria();
            resetearDataTable("frmCuentas:tblCuentas");
            DefaultRequestContext.getCurrentInstance().execute("PF('dlgEditarCteBan').hide()");
            obtenerCuentas();
        } catch (Exception e) {
            System.out.println("public void editarCteBancaria dice: " + e.getMessage());
            Util.addErrorMessage(e.getMessage());
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Eliminar PIF">

    public void eliminarCteBan() {
        try {
            
            String msg = FCuentaBancaria.eliminarCteBancaria(cuentaSel, sessionUsuario.getIdUsuario());
            Util.addSuccessMessage(msg);
            cuentaSel = new CuentaBancaria();
            resetearDataTable("frmCuentas:tblCuentas"); 
            DefaultRequestContext.getCurrentInstance().execute("PF('dlgEliminarCteBan').hide()");
            obtenerCuentas();
        } catch (Exception e) {
            System.out.println("public void eliminarCteBancaria() dice: " + e.getMessage());
            Util.addErrorMessage(e.getMessage());
        }
    }
    //</editor-fold>
    

    public void resetearDataTable(String id) {
        DataTable table = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent(id);
        table.reset();
    }

    /**
     * @return the lstCuentas
     */
    public List<CuentaBancaria> getLstCuentas() {
        return lstCuentas;
    }

    /**
     * @param lstCuentas the lstCuentas to set
     */
    public void setLstCuentas(List<CuentaBancaria> lstCuentas) {
        this.lstCuentas = lstCuentas;
    }

    /**
     * @return the objCuenta
     */
    public CuentaBancaria getObjCuenta() {
        return objCuenta;
    }

    /**
     * @param objCuenta the objCuenta to set
     */
    public void setObjCuenta(CuentaBancaria objCuenta) {
        this.objCuenta = objCuenta;
    }

    /**
     * @return the cuentaSel
     */
    public CuentaBancaria getCuentaSel() {
        return cuentaSel;
    }

    /**
     * @param cuentaSel the cuentaSel to set
     */
    public void setCuentaSel(CuentaBancaria cuentaSel) {
        this.cuentaSel = cuentaSel;
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

    public Usuario getSessionUsuario() {
        return sessionUsuario;
    }

    public void setSessionUsuario(Usuario sessionUsuario) {
        this.sessionUsuario = sessionUsuario;
    }

  
    

}
