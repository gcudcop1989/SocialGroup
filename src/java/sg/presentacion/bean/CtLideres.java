package sg.presentacion.bean;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import master.logica.entidades.RolUsuario;
import master.logica.entidades.Usuario;
import master.logica.funciones.FRolUsuario;
import master.logica.funciones.FUsuario;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.DefaultRequestContext;
import recursos.Util;

@ManagedBean
@ViewScoped
public class CtLideres implements Serializable {

    private List<RolUsuario> lstLideres;
    private RolUsuario rolUsuario;
    private RolUsuario objRolUsuario;
    private Usuario objCliente;
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;
    private Usuario sessionUsuario;
    private List<Usuario> lstUsuarios;

    public CtLideres() {
        objCliente = new Usuario();
        objRolUsuario = new RolUsuario();
        rolUsuario = new RolUsuario();
        sessionUsuario = new Usuario();
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
        obtenerUsuariosNoLider();
        obtenerLideres();
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

    public void obtenerLideres() {
        try {
            setLstLideres(FRolUsuario.obtenerUsuariosDadoRol(7)); /// 7 es el codigo de lider
        } catch (Exception e) {
            System.out.println("public void obtenerLideres(): " + e.getMessage());
            Util.addErrorMessage(e.getMessage());
        }
    }

    public void obtenerUsuariosNoLider() {
        try {
            setLstUsuarios(FUsuario.obtenerUsuariosNoLider());
        } catch (Exception e) {
            System.out.println("public void obtenerUsuariosNoLider(): " + e.getMessage());
            Util.addErrorMessage(e.getMessage());
        }
    }

    public void asignarLiderUsuario() {
        try {
            String msg = FUsuario.asignarUsuarioLider(objCliente.getIdUsuario(), sessionUsuario.getIdUsuario());
            Util.addSuccessMessage(msg);
            obtenerLideres();
            objCliente = new Usuario();
            resetearDataTable("frmPrincipal:tblUsuarios");
            DefaultRequestContext.getCurrentInstance().execute("PF('dlgInsertar').hide()");
        } catch (Exception e) {
            System.out.println("public void asignarLiderUsuario(): " + e.getMessage());
            Util.addErrorMessage(e.getMessage());
        }
    }

    public void desactivarLiderUsuario() {
        try {
            System.out.println("Usuario a borrar: "+rolUsuario.getUsuario().getIdUsuario());
            String msg = FUsuario.desactivarUsuarioLider(rolUsuario.getUsuario().getIdUsuario(), sessionUsuario.getIdUsuario());
            Util.addSuccessMessage(msg);
            obtenerLideres();
            rolUsuario=new RolUsuario();
            resetearDataTable("frmPrincipal:tblUsuarios");
            DefaultRequestContext.getCurrentInstance().execute("PF('dlgEliminar').hide()");
        } catch (Exception e) {
            System.out.println("public void desactivarLiderUsuario(): " + e.getMessage());
            Util.addErrorMessage(e.getMessage());
        }
    }

    public void resetearDataTable(String id) {
        DataTable table = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent(id);
        table.reset();
    }

    /**
     * @return the lstLideres
     */
    public List<RolUsuario> getLstLideres() {
        return lstLideres;
    }

    /**
     * @param lstLideres the lstLideres to set
     */
    public void setLstLideres(List<RolUsuario> lstLideres) {
        this.lstLideres = lstLideres;
    }

    /**
     * @return the rolUsuario
     */
    public RolUsuario getRolUsuario() {
        return rolUsuario;
    }

    /**
     * @param rolUsuario the rolUsuario to set
     */
    public void setRolUsuario(RolUsuario rolUsuario) {
        this.rolUsuario = rolUsuario;
    }

    /**
     * @return the objCliente
     */
    public Usuario getObjCliente() {
        return objCliente;
    }

    /**
     * @param objCliente the objCliente to set
     */
    public void setObjCliente(Usuario objCliente) {
        this.objCliente = objCliente;
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
     * @return the objRolUsuario
     */
    public RolUsuario getObjRolUsuario() {
        return objRolUsuario;
    }

    /**
     * @param objRolUsuario the objRolUsuario to set
     */
    public void setObjRolUsuario(RolUsuario objRolUsuario) {
        this.objRolUsuario = objRolUsuario;
    }

}
