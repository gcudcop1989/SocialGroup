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
import sg.logica.entidades.Compra;
import sg.logica.entidades.FormaPago;
import sg.logica.entidades.Pif;
import sg.logica.funciones.FCompra;

@ManagedBean
@ViewScoped
public class CtVentasLideres implements Serializable {

    private List<Compra> lstCompras;
    private Compra compraSel;
    private List<Pif> lstPifs;
    private List<FormaPago> lstFormasPago;
    private Pif objPif;
    private Pif pifSel;
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;
    private Usuario sessionUsuario;

    public CtVentasLideres() {
        compraSel = new Compra();
        objPif = new Pif();
        pifSel = new Pif();
        sessionUsuario = new Usuario();
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
        obtenerCompras();
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

    public void obtenerCompras() {
        try {
            int intIdUsuario = (int) getHttpServletRequest().getSession().getAttribute("idUsuario");
            setLstCompras(FCompra.obtenerComprasDadoLider(intIdUsuario));
            System.out.println("Total compras: " + getLstCompras().size());
        } catch (Exception e) {
            System.out.println("public void obtenerCompras() dice: " + e.getMessage());
            Util.addErrorMessage("public void obtenerCompras() dice: " + e.getMessage());
        }
    }

    /**
     * @return the lstCompras
     */
    public List<Compra> getLstCompras() {
        return lstCompras;
    }

    /**
     * @param lstCompras the lstCompras to set
     */
    public void setLstCompras(List<Compra> lstCompras) {
        this.lstCompras = lstCompras;
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
     * @return the objPif
     */
    public Pif getObjPif() {
        return objPif;
    }

    /**
     * @param objPif the objPif to set
     */
    public void setObjPif(Pif objPif) {
        this.objPif = objPif;
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
     * @return the compraSel
     */
    public Compra getCompraSel() {
        return compraSel;
    }

    /**
     * @param compraSel the compraSel to set
     */
    public void setCompraSel(Compra compraSel) {
        this.compraSel = compraSel;
    }

}
