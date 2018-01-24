/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tester;

import sg.logica.funciones.FCuenta;

/**
 *
 * @author Geovanny
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            System.out.println("Total de cuentas: "+FCuenta.obtenerCuentasDadoTitular(2).size());
        } catch (Exception e) {
            System.out.println("error: "+e.getMessage());
        }
    }
    
}
