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
public class NewMain2 {

    public static String devolverJson(int codigo) {
        String json = "";
        try {
            Cuenta padre = new Cuenta();
            padre = FCuenta.obtenerCuentaDadoId(codigo);
            List<Cuenta> lst = FCuenta.obtenerCuentasHijas(codigo);
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
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return json;
    }

    public static void main(String[] args) {
        try {
            System.out.println("Json: "+ devolverJson(81));
           
        } catch (Exception e) {
        }
    }

}
