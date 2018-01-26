/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tester;

import java.util.List;
import sg.logica.entidades.Estadisticas;
import sg.logica.funciones.FEstadisticas;

/**
 *
 * @author Geovanny Cudco
 */
public class NewMain1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            List<Estadisticas> lst = FEstadisticas.obtenerVentasActivas();
            String json = "[";
            for (int i = 0; i < lst.size(); i++) {
                json = json + "{'pif':'" + lst.get(i).getDescripcion1() + "',";
                json = json + "'Total':'" + lst.get(i).getTotalInt1() + "'";
                if (i != lst.size() - 1) {
                    json = json + "},";
                } else {
                    json = json + "}";
                }
            }
            json = json + "]";
            System.out.println("json: " + json);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
