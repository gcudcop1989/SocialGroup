package master.presentacion.beans;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import master.logica.entidades.RolUsuario;
import master.logica.entidades.Usuario;
import master.logica.funciones.FRolUsuario;
import recursos.Util;

@ManagedBean
@ViewScoped
public class CtSelectRol implements Serializable {

    private List<RolUsuario> lstRoles;
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;
    private RolUsuario rolUsuario;

    public CtSelectRol() {
        rolUsuario = new RolUsuario();
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
    }

    @PostConstruct
    public void init() {
        cargarRoles();
    }

    public void cargarRoles() {
        try {
            Usuario us = (Usuario) getHttpServletRequest().getSession().getAttribute("UsuarioLogueado");
            setLstRoles(FRolUsuario.obtenerRolesDadoUsuario(us.getIdPersona()));
            System.out.println("icono rol: " + getLstRoles().get(0).getRol().getIcono());
            Util.addSuccessMessage("total de roles: " + getLstRoles().size());
        } catch (Exception e) {
            Util.addErrorMessage(e.getMessage());
        }
    }

    /**
     * @return the lstRoles
     */
    public List<RolUsuario> getLstRoles() {
        return lstRoles;
    }

    /**
     * @param lstRoles the lstRoles to set
     */
    public void setLstRoles(List<RolUsuario> lstRoles) {
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

}
