package master.presentacion.beans;

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

@ManagedBean
@ViewScoped
public class CtUsuarios implements Serializable {

    private List<Usuario> lstUsuarios;
    private Usuario objUsuario;
    private Usuario usuarioSel;
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;
    private Usuario sessionUsuario;
    private String msg;

    public CtUsuarios() {
        objUsuario = new Usuario();
        usuarioSel = new Usuario();
        sessionUsuario = new Usuario();
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
        obtenerUsuarios();
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

    public void resetearDataTable(String id) {
        DataTable table = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent(id);
        table.reset();
    }

    public void obtenerUsuarios() {
        try {
            // 1: Usuarios del sistema
            setLstUsuarios(FUsuario.obtenerUsuariosDadoTipo(1));
            System.out.println(getLstUsuarios().size() + " Usuarios obtenidos");
        } catch (Exception e) {
            System.out.println("public void obtenerUsuarios() " + e.getMessage());
            Util.addErrorMessage("public void obtenerUsuarios() " + e.getMessage());
        }
    }

    public void registrarUsuarios() {
        try {
            int idUsuario = (int) getHttpServletRequest().getSession().getAttribute("idUsuario");
            setMsg(FUsuario.registrarUsuarioSistema(getObjUsuario(), idUsuario));
            obtenerUsuarios();
            resetearDataTable("frmPrincipal:tblUsuarios");
            setObjUsuario(new Usuario());
            DefaultRequestContext.getCurrentInstance().execute("PF('dlgRegistrar').hide()");
        } catch (Exception e) {
            System.out.println("public void registrarUsuarios() dice: " + e.getMessage());
            Util.addErrorMessage("public void registrarUsuarios() dice: " + e.getMessage());
        }
    }

    public void editarUusario() {
        try {
            int idUsuario = (int) getHttpServletRequest().getSession().getAttribute("idUsuario");
            setMsg(FUsuario.actualizarUsuarioSistema(getUsuarioSel(), idUsuario));
            obtenerUsuarios();
            resetearDataTable("frmPrincipal:tblUsuarios");
            setUsuarioSel(new Usuario());
            DefaultRequestContext.getCurrentInstance().execute("PF('dlgEditar').hide()");
        } catch (Exception e) {
            System.out.println("public void editarUusario() dice: " + e.getMessage());
            Util.addErrorMessage("public void editarUusario() dice: " + e.getMessage());
        }
    }

    /**
     * @return the lstUsuarios
     */
    public List<Usuario> getLstUsuarios() {
        return lstUsuarios;
    }

    /**
     * @param lstUsuarios the lstUsuarios to set
     */
    public void setLstUsuarios(List<Usuario> lstUsuarios) {
        this.lstUsuarios = lstUsuarios;
    }

    /**
     * @return the objUsuario
     */
    public Usuario getObjUsuario() {
        return objUsuario;
    }

    /**
     * @param objUsuario the objUsuario to set
     */
    public void setObjUsuario(Usuario objUsuario) {
        this.objUsuario = objUsuario;
    }

    /**
     * @return the usuarioSel
     */
    public Usuario getUsuarioSel() {
        return usuarioSel;
    }

    /**
     * @param usuarioSel the usuarioSel to set
     */
    public void setUsuarioSel(Usuario usuarioSel) {
        this.usuarioSel = usuarioSel;
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

}
