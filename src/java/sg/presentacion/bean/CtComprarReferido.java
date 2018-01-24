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
import recursos.Util;
import sg.logica.entidades.Cuenta;
import sg.logica.entidades.FormaPago;
import sg.logica.funciones.FCuenta;
import sg.logica.funciones.FFormaPago;

@ManagedBean
@ViewScoped
public class CtComprarReferido implements Serializable {

    private String codigoReferido;
    private Cuenta objCuenta;
    private int idFormaPago;
    private List<FormaPago> lstFormasPago;
    private List<Cuenta> lstCuentasActivas;
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;
    private Usuario sessionUsuario;

    public CtComprarReferido() {
        objCuenta = new Cuenta();
        sessionUsuario = new Usuario();
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
        obtenerFormasPago();
        obtenerCuentasActivas();
    }

    @PostConstruct
    public void init() {
        obtenerSession();
    }

    public void obtenerSession() {
        try {
            int idUsuario = (int) getHttpServletRequest().getSession().getAttribute("idUsuario");
            setSessionUsuario(FUsuario.obtenerUsuarioDadoCodigo(idUsuario));
            System.out.println("Id usuario: " + idUsuario
                    + "Usuario session: " + getSessionUsuario().getNick());
        } catch (Exception e) {
            System.out.println("public void obtenerSession() dice: " + e.getMessage());
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, message);

        }
    }

    public void obtenerCuentasActivas() {
        try {
            setLstCuentasActivas(FCuenta.obtenerCuentasActivas());
        } catch (Exception e) {
            System.out.println("public void obtenerCuentasActivas() dice: " + e.getMessage());
            Util.addErrorMessage(e, e.getMessage());
        }
    }

    public void obtenerFormasPago() {
        try {
            setLstFormasPago(FFormaPago.obtenerFormasPagoActivas());
        } catch (Exception e) {
            System.out.println("public void obtenerFormasPago() dice: " + e.getMessage());
            Util.addErrorMessage(e, e.getMessage());
        }
    }

    public void obtenerCuentaDadoCodigo() {
        try {
            System.out.println("Codigo referido: " + getCodigoReferido());

            setObjCuenta(FCuenta.obtenerCuentaDadoCodigo(getCodigoReferido()));
            Util.addSuccessMessage("Se obtuvo la cuenta " + getObjCuenta().getCodigo());

            System.out.println("Se obtuvo la cuenta " + getObjCuenta().getCodigo());
        } catch (Exception e) {
            setObjCuenta(new Cuenta());

            System.out.println("public void obtenerCuentaDadoCodigo() dice: " + e.getMessage());
            Util.addErrorMessage(e.getMessage().replace("ERROR:", "").replace("Hint:", ""));
        }
    }

    public void comprarMedianteReferido() {
        try {
            int idUsuario = (int) getHttpServletRequest().getSession().getAttribute("idUsuario");

            System.out.println("idSocio: " + idUsuario + " cuenta: " + getObjCuenta().getIdCuenta() + " forma de pago: " + getIdFormaPago());

            String msg = FCuenta.comprarMedianteReferido(idUsuario, getObjCuenta().getIdCuenta(), getIdFormaPago());
            Util.addSuccessMessage(msg);

            objCuenta = new Cuenta();
            idFormaPago = 0;
        } catch (Exception e) {
            System.out.println("public void comprarMedianteReferido() dice: " + e.getMessage());
            Util.addErrorMessage(e.getMessage());
        }
    }

    /**
     * @return the codigoReferido
     */
    public String getCodigoReferido() {
        return codigoReferido;
    }

    /**
     * @param codigoReferido the codigoReferido to set
     */
    public void setCodigoReferido(String codigoReferido) {
        this.codigoReferido = codigoReferido;
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
     * @return the idFormaPago
     */
    public int getIdFormaPago() {
        return idFormaPago;
    }

    /**
     * @param idFormaPago the idFormaPago to set
     */
    public void setIdFormaPago(int idFormaPago) {
        this.idFormaPago = idFormaPago;
    }

    /**
     * @return the lstFormasPago
     */
    public List<FormaPago> getLstFormasPago() {
        return lstFormasPago;
    }

    /**
     * @param lstFormasPago the lstFormasPago to set
     */
    public void setLstFormasPago(List<FormaPago> lstFormasPago) {
        this.lstFormasPago = lstFormasPago;
    }

    /**
     * @return the lstCuentasActivas
     */
    public List<Cuenta> getLstCuentasActivas() {
        return lstCuentasActivas;
    }

    /**
     * @param lstCuentasActivas the lstCuentasActivas to set
     */
    public void setLstCuentasActivas(List<Cuenta> lstCuentasActivas) {
        this.lstCuentasActivas = lstCuentasActivas;
    }

}
