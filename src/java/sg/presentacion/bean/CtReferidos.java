/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.presentacion.bean;

import java.io.Serializable;
import java.util.ArrayList;
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
import org.primefaces.context.RequestContext;
import recursos.Util;
import sg.logica.entidades.Cuenta;
import sg.logica.entidades.FormaPago;
import sg.logica.entidades.Pif;
import sg.logica.funciones.FCuenta;
import sg.logica.funciones.FFormaPago;
import sg.logica.funciones.FPif;

@ManagedBean
@ViewScoped
public class CtReferidos implements Serializable {

    private List<Cuenta> lstCuentas;
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;
    private Usuario sessionUsuario;
    private Cuenta objCuenta;
    private int idCuenta;
    private List<Pif> lstPifs;
    private List<FormaPago> lstFormasPago;
    private Pif pifSel;
    private int idFormaPago;
    private String msg;

    public CtReferidos() {
        pifSel = new Pif();
        objCuenta = new Cuenta();
        sessionUsuario = new Usuario();
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
        obtenerFormasPago();
    }

    @PostConstruct
    public void init() {
        obtenerSession();
        obtenerCuentas();
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

    public void obtenerFormasPago() {
        try {
            setLstFormasPago(FFormaPago.obtenerFormasPagoActivas());
        } catch (Exception e) {
            System.out.println("public void obtenerFormasPago() dice: " + e.getMessage());
            Util.addErrorMessage(e, e.getMessage());
        }
    }

    public void obtenerCuentas() {
        try {
            int idUsu = (int) getHttpServletRequest().getSession().getAttribute("idUsuario");
            System.out.println("Usuario: " + idUsu);

            setLstCuentas(FCuenta.obtenerCuentasNoTitular(idUsu));
            System.out.println("Cuentas obtenidad: " + getLstCuentas().size());
        } catch (Exception e) {
            Util.addErrorMessage(e.getMessage());
        }
    }

    public void buscarCuenta() {
        try {
            setObjCuenta(FCuenta.obtenerCuentaDadoId(getIdCuenta()));
            Util.addSuccessMessage("Cuenta obtenida: " + getObjCuenta().getCodigo() + "\n\n\n\t Titular: " + getObjCuenta().getPersona().getNombres() + " " + getObjCuenta().getPersona().getApellidos());
            System.out.println("Cuenta: " + getObjCuenta().getPif().getPif());

            obtenerPifsAComprar();
        } catch (Exception e) {
            System.out.println("Obtener cuenta dice: " + e.getMessage());
            Util.addErrorMessage("Obtener cuenta dice: " + e.getMessage());
        }
    }

    public void obtenerPifsAComprar() {
        try {
            setLstPifs(FPif.obtenerPifsDadoReferido(getIdCuenta()));
        } catch (Exception e) {
            System.out.println("public void obtenerPifsAComprar dice: " + e.getMessage());
            Util.addErrorMessage("public void obtenerPifsAComprar dice: " + e.getMessage());
        }
    }

    public void comprarPif() {
        try {
            msg = FCuenta.comprarMedianteReferidoV2(sessionUsuario.getIdUsuario(), objCuenta.getIdCuenta(), idFormaPago, pifSel.getIdPif());

            DefaultRequestContext.getCurrentInstance().execute("PF('dlgComprar').hide()");
            Util.addSuccessMessage(msg);

            idCuenta = 0;
            idFormaPago = 0;
            objCuenta = new Cuenta();
            pifSel = new Pif();
            lstPifs = new ArrayList<>();

        } catch (Exception e) {
            System.out.println("public void comprarPif() dice: " + e.getMessage());
            Util.addErrorMessage(e, e.getMessage());
        }
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
     * @return the lstPifs
     */
    public List<Pif> getLstPifs() {
        return lstPifs;
    }

    /**
     * @param lstPifs the lstPifs to set
     */
    public void setLstPifs(List<Pif> lstPifs) {
        this.lstPifs = lstPifs;
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
     * @return the pifSel
     */
    public Pif getPifSel() {
        return pifSel;
    }

    /**
     * @param pifSel the pifSel to set
     */
    public void setPifSel(Pif pifSel) {
        this.pifSel = pifSel;
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

}
