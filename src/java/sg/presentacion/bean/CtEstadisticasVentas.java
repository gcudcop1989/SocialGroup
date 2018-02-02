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
    private String json2;

    @PostConstruct
    public void init() {
        totalVendidos();
        totalIngresos();
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

    public void totalIngresos() {
        try {
            List<Estadisticas> lst = FEstadisticas.obtenerVentasActivasTotalVendido();

            setJson2("[");
            for (int i = 0; i < lst.size(); i++) {
                setJson2(getJson2() + "{'pif':'" + lst.get(i).getDescripcion1() + "',");
                setJson2(getJson2() + "'Total':'" + lst.get(i).getTotalDouble1() + "'");
                if (i != lst.size() - 1) {
                    setJson2(getJson2() + "},");
                } else {
                    setJson2(getJson2() + "}");
                }
            }
            setJson2(getJson2() + "]");
            System.out.println("Json2: " + getJson2());
        } catch (Exception e) {
            System.out.println("public void totalIngresos() dice: " + e.getMessage());
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

    /**
     * @return the json2
     */
    public String getJson2() {
        return json2;
    }

    /**
     * @param json2 the json2 to set
     */
    public void setJson2(String json2) {
        this.json2 = json2;
    }

}
