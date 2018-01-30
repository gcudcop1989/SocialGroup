package sg.presentacion.bean;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import recursos.Util;
import sg.logica.entidades.Cuenta;
import sg.logica.funciones.FCuenta;

@ManagedBean
@ViewScoped
public class CtRed implements Serializable {

    private String json;

    @PostConstruct
    public void init() {
        crearJson();
    }

    private void crearJson() {
        try {
            int nivel = 0;
            Cuenta padre = new Cuenta();
            padre = FCuenta.obtenerCuentaDadoId(79);
            List<Cuenta> lst = FCuenta.obtenerCuentasHijas(79);
            json = "{'name':'" + padre.getCodigo() + "',";
            json = json + "'children':[";
            for (int i = 0; i < lst.size(); i++) {
                json = json + "{'name':'" + lst.get(i).getCodigo() + "',";
                json = json + "'children':[]";
                if (i != lst.size() - 1) {
                    json = json + "},";
                } else {
                    json = json + "}]";
                }
            }
            json = json + "}";

            System.out.println("json: " + json);
        } catch (Exception e) {
            Util.addErrorMessage(e.getMessage());
        }
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

}
