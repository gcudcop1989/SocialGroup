/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tester;

import java.util.List;
import sg.logica.entidades.Cuenta;
import sg.logica.funciones.FCuenta;

/**
 *
 * @author Geovanny Cudco
 */
public class NewMain3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            String json = "[";
            List<Cuenta> lst = FCuenta.obtenerCuentasComisionesDadoTitular(2);

            for (int j = 0; j < lst.size(); j++) {
                json = json + "{'cuenta':'" + lst.get(j).getCodigo() + "',";
                json = json + "'comision':'" + lst.get(j).getTotalComision() + "'";

                if (lst.get(j).getPif().getIdPif() == 3) {
                    json = json + "'color':'" + "'#C64809'" + "'";
                } else if (lst.get(j).getPif().getIdPif() == 4) {
                    json = json + "'color':'" + "'#58a595'" + "'";
                } else {
                    json = json + "'color':'" + "'#78c6d3'" + "'";
                }

                if (j != lst.size() - 1) {
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
