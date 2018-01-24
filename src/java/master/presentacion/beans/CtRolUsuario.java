package master.presentacion.beans;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import master.logica.entidades.Rol;
import master.logica.entidades.RolUsuario;
import master.logica.entidades.Usuario;
import master.logica.funciones.FRol;
import master.logica.funciones.FRolUsuario;
import master.logica.funciones.FUsuario;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.DefaultRequestContext;
import recursos.Util;

@ManagedBean
@ViewScoped
public class CtRolUsuario implements Serializable {

    private List<RolUsuario> lstRolesUsuarios;
    private RolUsuario objRolUsuario;
    private RolUsuario rolUsuarioSel;
    private String msg;
    private List<Usuario> lstUsuarios;
    private List<Rol> lstRoles;
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;
    private Usuario sessionUsuario;

    public CtRolUsuario() {
        objRolUsuario = new RolUsuario();
        rolUsuarioSel = new RolUsuario();
        sessionUsuario = new Usuario();
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
        obtenerRolesUsuarios();
        obtenerRoles();
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

    public void obtenerRolesUsuarios() {
        try {
            setLstRolesUsuarios(FRolUsuario.obtenerRolesUsuariosDadoEstado("A"));
        } catch (Exception e) {
            System.out.println("public void obtenerRolesUsuarios() " + e.getMessage());
            Util.addErrorMessage("public void obtenerRolesUsuarios() " + e.getMessage());
        }
    }

    public void obtenerUsuarios() {
        try {
            lstUsuarios = FUsuario.obtenerAllUsuarios();
        } catch (Exception e) {
            System.out.println("public void obtenerUsuarios() " + e.getMessage());
            Util.addErrorMessage("public void obtenerUsuarios() " + e.getMessage());
        }
    }

    public void obtenerRoles() {
        try {
            lstRoles = FRol.obtenerRoles();
        } catch (Exception e) {
            System.out.println("public void obtenerRoles() " + e.getMessage());
            Util.addErrorMessage("public void obtenerRoles() " + e.getMessage());
        }
    }

    public void asignarRolUsuario() {
        try {
            objRolUsuario.setSessionUsuario(sessionUsuario);
            msg = FRolUsuario.asignarRolUsuario(objRolUsuario);
            Util.addSuccessMessage(msg);
            obtenerRolesUsuarios();
            resetearDataTable("frmPrincipal:tblUsuarios");
            DefaultRequestContext.getCurrentInstance().execute("PF('dlgInsertar').hide()");
            objRolUsuario = new RolUsuario();
        } catch (Exception e) {
            System.out.println("public void asignarRolUsuario() " + e.getMessage());
            Util.addErrorMessage(e.getMessage().replace("ERROR:", "").replace("Hint:", ""));
        }
    }

    public void editarRolUsuario() {
        try {
            rolUsuarioSel.setSessionUsuario(sessionUsuario);
            msg = FRolUsuario.asignarRolUsuario(rolUsuarioSel);
            Util.addSuccessMessage(msg);
            obtenerRolesUsuarios();
            resetearDataTable("frmPrincipal:tblUsuarios");
            DefaultRequestContext.getCurrentInstance().execute("PF('dlgEditar').hide()");
            rolUsuarioSel = new RolUsuario();
        } catch (Exception e) {
            System.out.println("public void asignarRolUsuario() " + e.getMessage());
            Util.addErrorMessage(e.getMessage().replace("ERROR:", "").replace("Hint:", ""));
        }
    }

    /**
     * @return the lstRolesUsuarios
     */
    public List<RolUsuario> getLstRolesUsuarios() {
        return lstRolesUsuarios;
    }

    /**
     * @param lstRolesUsuarios the lstRolesUsuarios to set
     */
    public void setLstRolesUsuarios(List<RolUsuario> lstRolesUsuarios) {
        this.lstRolesUsuarios = lstRolesUsuarios;
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

    /**
     * @return the rolUsuarioSel
     */
    public RolUsuario getRolUsuarioSel() {
        return rolUsuarioSel;
    }

    /**
     * @param rolUsuarioSel the rolUsuarioSel to set
     */
    public void setRolUsuarioSel(RolUsuario rolUsuarioSel) {
        this.rolUsuarioSel = rolUsuarioSel;
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
     * @return the lstRoles
     */
    public List<Rol> getLstRoles() {
        return lstRoles;
    }

    /**
     * @param lstRoles the lstRoles to set
     */
    public void setLstRoles(List<Rol> lstRoles) {
        this.lstRoles = lstRoles;
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
