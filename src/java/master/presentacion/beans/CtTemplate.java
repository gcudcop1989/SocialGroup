/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package master.presentacion.beans;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import master.logica.entidades.RolUsuario;
import master.logica.entidades.Usuario;
import master.logica.funciones.FRolUsuario;

@ManagedBean
@ViewScoped
public class CtTemplate implements Serializable {

    private Usuario usuario;
    private RolUsuario rolUsuario;
    private RolUsuario ruSession;
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;
    private FacesMessage facesMessage;

    public CtTemplate() {
        ruSession = new RolUsuario();
        rolUsuario = new RolUsuario();
        usuario = new Usuario();
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
    }

    public void verificarLogin() {
        try {
            int intIdUsuario = (int) httpServletRequest.getSession().getAttribute("idUsuario");
            int intIdRol = (int) httpServletRequest.getSession().getAttribute("idRol");
            System.out.println("======>>>>> PRE RENDER VIEW");

            System.out.println("======>>>>> IdRol: " + intIdRol + " idUsuario " + intIdUsuario);

            rolUsuario = FRolUsuario.obtenerRolUsuario(intIdRol, intIdUsuario);

            System.out.println("======>>>>> Rol Usuario: " + rolUsuario.getRol().getRol());

            ////obtrengo el RolUsuario de la session
            ruSession = (RolUsuario) httpServletRequest.getSession().getAttribute("rolUsuarioSession");
            Usuario us = (Usuario) faceContext.getExternalContext().getSessionMap().get("UsuarioLogueado");

            System.out.println("======>>>>> Rol Usuario de la session: " + ruSession.getRol().getRol()
                    + "\n\n =============>>>>>> Usuario de la Session " + us.getNick());

            if (us == null) {
                faceContext.getExternalContext().redirect("/SocialGroup/permisos.jsf");
            }

        } catch (Exception e) {
        }
    }

    public void verificarSesion() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            Usuario us = (Usuario) context.getExternalContext().getSessionMap().get("UsuarioLogueado");
            if (us == null) {
                context.getExternalContext().redirect("../permisos.jsf");
            }
        } catch (Exception e) {

        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public RolUsuario getRolUsuario() {
        return rolUsuario;
    }

    public void setRolUsuario(RolUsuario rolUsuario) {
        this.rolUsuario = rolUsuario;
    }

    public RolUsuario getRuSession() {
        return ruSession;
    }

    public void setRuSession(RolUsuario ruSession) {
        this.ruSession = ruSession;
    }

    public HttpServletRequest getHttpServletRequest() {
        return httpServletRequest;
    }

    public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    public FacesContext getFaceContext() {
        return faceContext;
    }

    public void setFaceContext(FacesContext faceContext) {
        this.faceContext = faceContext;
    }

    public FacesMessage getFacesMessage() {
        return facesMessage;
    }

    public void setFacesMessage(FacesMessage facesMessage) {
        this.facesMessage = facesMessage;
    }

}
