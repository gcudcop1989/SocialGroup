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
import org.primefaces.context.DefaultRequestContext;
import recursos.Util;
import sg.logica.entidades.Cuenta;
import sg.logica.entidades.CuentaBancaria;
import sg.logica.funciones.FCuenta;
import sg.logica.funciones.FCuentaBancaria;
import sg.logica.funciones.FSolicitudCobro;

/**
 *
 * @author Geovanny
 */
@ManagedBean
@ViewScoped
public class CtSolicitarPago implements Serializable {

    private double monto;
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;
    private Usuario sessionUsuario;
    private List<Cuenta> lstCuentas;
    private int band;
    private double totalSaldo;
    private String msg;
    private List<CuentaBancaria> lstCtasBancarias;
    private int idCtaBancaria;
    private int idCuenta;
    private Cuenta objCuenta;

    public CtSolicitarPago() {
        sessionUsuario = new Usuario();
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
        idCtaBancaria = 0;
    }

    @PostConstruct
    public void init() {
        obtenerSession();
        obtenerCuentas();
        ObtenerCuentasBancarias();
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
            setLstCuentas(FCuenta.obtenerCuentasComisionesDadoTitular(getSessionUsuario().getIdUsuario()));
            for (int i = 0; i < getLstCuentas().size(); i++) {
                setTotalSaldo(getTotalSaldo() + getLstCuentas().get(i).getTotalComision());
            }
            System.out.println("Cuentas obtenidas: " + getLstCuentas().size());
        } catch (Exception e) {
            System.out.println("public void obtenerCuentas() dice: " + e.getMessage());
        }
    }

    public void enviarSolicitud() {
        try {
            setMsg(FSolicitudCobro.registrarSolicitudGlobal(getSessionUsuario().getIdUsuario(), getTotalSaldo(), getIdCtaBancaria()));
            Util.addSuccessMessage(getMsg());
            setMonto(0);
            setIdCtaBancaria(0);
            DefaultRequestContext.getCurrentInstance().execute("PF('wgCobrarTodo').hide()");
        } catch (Exception e) {
            Util.addErrorMessage(e.getMessage().replace("ERROR:", "").replace("Hint:", ""));
            System.out.println("public void enviarSolicitud() dice: " + e.getMessage());
        }
    }

    public void enviarSolicitudCuenta() {
        try {
            setMsg(FSolicitudCobro.registrarSolicitudCuenta(getIdCuenta(), getMonto(), getIdCtaBancaria()));
            Util.addSuccessMessage(getMsg());
            setIdCuenta(0);
            setMonto(0);
            setIdCtaBancaria(0);
            objCuenta = new Cuenta();
            DefaultRequestContext.getCurrentInstance().execute("PF('wgCobrarCuenta').hide()");
        } catch (Exception e) {
            Util.addErrorMessage(e.getMessage().replace("ERROR:", "").replace("Hint:", ""));
            System.out.println("public void enviarSolicitud() dice: " + e.getMessage());
        }
    }

    public void obtenerCuentaDadoId() {
        try {
            setObjCuenta(FCuenta.obtenerCuentaDadoId(getIdCuenta()));
        } catch (Exception e) {
            Util.addErrorMessage(e.getMessage());
            System.out.println("public void obtenerCuentas() dice: " + e.getMessage());
        }
    }

    public void ObtenerCuentasBancarias() {
        try {
            setLstCtasBancarias(FCuentaBancaria.obtenerCtasDadoSocio(getSessionUsuario().getIdUsuario()));
        } catch (Exception e) {
            System.out.println("public void obtenerCuentas() dice: " + e.getMessage());
        }
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
     * @return the band
     */
    public int getBand() {
        return band;
    }

    /**
     * @param band the band to set
     */
    public void setBand(int band) {
        this.band = band;
    }

    /**
     * @return the totalSaldo
     */
    public double getTotalSaldo() {
        return totalSaldo;
    }

    /**
     * @param totalSaldo the totalSaldo to set
     */
    public void setTotalSaldo(double totalSaldo) {
        this.totalSaldo = totalSaldo;
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
     * @return the lstCtasBancarias
     */
    public List<CuentaBancaria> getLstCtasBancarias() {
        return lstCtasBancarias;
    }

    /**
     * @param lstCtasBancarias the lstCtasBancarias to set
     */
    public void setLstCtasBancarias(List<CuentaBancaria> lstCtasBancarias) {
        this.lstCtasBancarias = lstCtasBancarias;
    }

    /**
     * @return the idCtaBancaria
     */
    public int getIdCtaBancaria() {
        return idCtaBancaria;
    }

    /**
     * @param idCtaBancaria the idCtaBancaria to set
     */
    public void setIdCtaBancaria(int idCtaBancaria) {
        this.idCtaBancaria = idCtaBancaria;
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
     * @return the objCuenta
     */
    public Cuenta getObjCuenta() {
        return objCuenta;
    }

    /**
     * @param objCuenta the objCuenta to set
     */
    public void setObjCuenta(Cuenta objCuenta) {
        this.objCuenta = objCuenta;
    }

}
