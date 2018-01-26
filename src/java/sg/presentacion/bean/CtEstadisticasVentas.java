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
import sg.logica.entidades.Estadisticas;
import sg.logica.funciones.FEstadisticas;

/**
 *
 * @author Geovanny Cudco
 */
@ManagedBean
@ViewScoped
public class CtEstadisticasVentas implements Serializable {

    private String json;

    @PostConstruct
    public void init() {
        totalVendidos();
    }

    public void totalVendidos() {
        try {
            List<Estadisticas> lst = FEstadisticas.obtenerVentasActivas();

            setJson("[");
            for (int i = 0; i < lst.size(); i++) {
                setJson(getJson() + "{'pif':'" + lst.get(i).getDescripcion1() + "',");
                setJson(getJson() + "'Total':'" + lst.get(i).getTotalInt1() + "'");
                if (i != lst.size() - 1) {
                    setJson(getJson() + "},");
                } else {
                    setJson(getJson() + "}");
                }
            }
            setJson(getJson() + "]");
            System.out.println("json: " + getJson());
        } catch (Exception e) {
            System.out.println("public void totalVendidos() dice: " + e.getMessage());
        }
    }

    /**
     * @return the json
     */
    public String getJson() {
        return json;
    }

    /**
     * @param json the json to set
     */
    public void setJson(String json) {
        this.json = json;
    }

}
