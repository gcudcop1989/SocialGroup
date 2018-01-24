/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package master.presentacion.beans;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import master.logica.entidades.Usuario;

@ManagedBean
@ViewScoped
public class CtTemplate implements Serializable {

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
}
