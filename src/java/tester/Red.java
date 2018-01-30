/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tester;

import java.util.ArrayList;
import java.util.List;
import sg.logica.entidades.Cuenta;
import sg.logica.funciones.FCuenta;

/**
 *
 * @author Geovanny Cudco
 */
public class Red {

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
            Cuenta padre = new Cuenta();
            padre = FCuenta.obtenerCuentaDadoId(79);
            List<Cuenta> lst = FCuenta.obtenerCuentasHijas(79);
            System.out.println("Hijas: ");
            if (lst.size() != 0) {
                for (int i = 0; i < lst.size(); i++) {
                    List<Cuenta> nivel2 = FCuenta.obtenerCuentasHijas(lst.get(i).getIdCuenta());
                    System.out.println(String.valueOf(i + 1) + " " + lst.get(i).getCodigo());
                    if (nivel2.size() != 0) {
                        System.out.println("Nivel2: ");
                        for (int n1 = 0; n1 < nivel2.size(); n1++) {
                            System.out.println("\t" + String.valueOf(n1 + 1) + " " + nivel2.get(n1).getCodigo());
                            List<Cuenta> nivel3 = FCuenta.obtenerCuentasHijas(nivel2.get(n1).getIdCuenta());
                            if (nivel3.size() != 0) {
                                System.out.println("\t\t\t hijos de " + nivel2.get(n1).getCodigo());
                                for (int n3 = 0; n3 < nivel3.size(); n3++) {
                                    System.out.println("\t\t\t cuenta: " + nivel3.get(n3).getCodigo());
                                }
                            } else {
                                System.out.println("sin hijos");
                            }
                        }
                    } else {
                        System.out.println("sin hijos");
                    }
                }
            } else {

            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
