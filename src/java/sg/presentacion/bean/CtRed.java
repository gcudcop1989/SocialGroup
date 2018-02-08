package sg.presentacion.bean;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import master.logica.entidades.Usuario;
import master.logica.funciones.FUsuario;
import recursos.Util;
import sg.logica.entidades.Cuenta;
import sg.logica.funciones.FCuenta;

@ManagedBean
@ViewScoped
public class CtRed implements Serializable {

    private String json;
    private int idCuenta;
    private List<Cuenta> lstCuentas;
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;

    public CtRed() {
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
    }

    @PostConstruct
    public void init() {
        obtenerCuentas();
    }

    public void obtenerCuentas() {
        try {
            int intIdUsuario = (int) getHttpServletRequest().getSession().getAttribute("idUsuario");
            setLstCuentas(FCuenta.obtenerCuentasDadoTitular(intIdUsuario));

        } catch (Exception e) {
            System.out.println("public void obtenerCuentas() dice: " + e.getMessage());
        }
    }

    /*
    public void crearJson() {
        try {
            int nivel = 0;
            Cuenta padre = new Cuenta();
            padre = FCuenta.obtenerCuentaDadoId(getIdCuenta());
            List<Cuenta> lst = FCuenta.obtenerCuentasHijas(getIdCuenta());
            setJson("{'name':'" + padre.getCodigo() + "',");
            setJson(getJson() + "'children':[");
            for (int i = 0; i < lst.size(); i++) {
                setJson(getJson() + "{'name':'" + lst.get(i).getCodigo() + "',");
                setJson(getJson() + "'children':[]");
                if (i != lst.size() - 1) {
                    setJson(getJson() + "},");
                } else {
                    setJson(getJson() + "}]");
                }
            }
            setJson(getJson() + "}");

            System.out.println("json: " + getJson());
        } catch (Exception e) {
            Util.addErrorMessage(e.getMessage());
        }
    }
     */
    public void crearJson() {
        try {
            int intIdUsuario = (int) getHttpServletRequest().getSession().getAttribute("idUsuario");

            int nivel = 0;
            Cuenta padre = new Cuenta();
            padre = FCuenta.obtenerCuentaDadoId(getIdCuenta());
            List<Cuenta> lst = FCuenta.obtenerCuentasHijasV2(getIdCuenta());
            Usuario socio = new Usuario();
            socio = FUsuario.obtenerUsuarioDadoId(intIdUsuario);

            setJson("{'name':'" + socio.getNick() + "',");
            setJson(getJson() + "'children':[");
            for (int i = 0; i < lst.size(); i++) {
                setJson(getJson() + "{'name':'" + lst.get(i).getPersona().getNick() + "',");
                setJson(getJson() + "'children':[]");
                if (i != lst.size() - 1) {
                    setJson(getJson() + "},");
                } else {
                    setJson(getJson() + "}]");
                }
            }
            setJson(getJson() + "}");

            System.out.println("json: " + getJson());
        } catch (Exception e) {
            Util.addErrorMessage("No tiene ventas.");
        }
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
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

}
