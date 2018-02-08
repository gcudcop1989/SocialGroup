/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.presentacion.bean;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import recursos.Util;
import sg.logica.entidades.Pif;
import sg.logica.entidades.Publicidad;
import sg.logica.funciones.FCuenta;
import sg.logica.funciones.FPif;
import sg.logica.funciones.FPublicidad;

/**
 *
 * @author Geovanny Cudco
 */
@ManagedBean
@ViewScoped
public class CtPublicidad implements Serializable {

    private List<Publicidad> lstAnuncios;

    @PostConstruct
    public void init() {
        try {
            setLstAnuncios(FPublicidad.obtenerAnunciosActivos());
        } catch (Exception e) {
            Util.addErrorMessage(e.getMessage());
        }
    }

    /**
     * @return the lstAnuncios
     */
    public List<Publicidad> getLstAnuncios() {
        return lstAnuncios;
    }

    /**
     * @param lstAnuncios the lstAnuncios to set
     */
    public void setLstAnuncios(List<Publicidad> lstAnuncios) {
        this.lstAnuncios = lstAnuncios;
    }

}
