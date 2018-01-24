/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tester;

import java.util.List;
import recursos.Util;
import sg.logica.entidades.Cuenta;
import sg.logica.funciones.FCuenta;

/**
 *
 * @author Geovanny Cudco
 */
public class TestGraficos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            String json = "";
            List<Cuenta> lst = FCuenta.obtenerCuentasComisionesDadoTitular(2);
            for (int i = 0; i < lst.size(); i++) {
                json = json + "[{'cuenta':'" + lst.get(i).getCodigo() + "',";
                json = json + "'Venta Directa':'" + lst.get(i).getComisionDirecta() + "',";
                json = json + "'Venta Residual':'" + lst.get(i).getComisionResidual() + "',";
                json = json + "'Total':'" + lst.get(i).getTotalComision() + "'";
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
