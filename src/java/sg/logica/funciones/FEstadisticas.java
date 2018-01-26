/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.logica.funciones;

import accesoDatos.AccesoDatos;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import sg.logica.entidades.Estadisticas;

/**
 *
 * @author Geovanny Cudco
 */
public class FEstadisticas {

    public static List<Estadisticas> obtenerVentasActivas() throws Exception {
        List<Estadisticas> lst = new ArrayList<>();
        AccesoDatos accesoDatos;
        Estadisticas estadistica;
        ResultSet resultSet;
        String sql;
        try {
            accesoDatos = new AccesoDatos();
            sql = "SELECT * FROM sch_social_group.f_obtener_pifs_vendidos();";
            resultSet = accesoDatos.ejecutaQuery(sql);
            while (resultSet.next()) {
                estadistica = new Estadisticas();
                estadistica.setDescripcion1(resultSet.getString("_pif"));
                estadistica.setTotalInt1(resultSet.getInt("out_total"));
                lst.add(estadistica);
            }
        } catch (Exception e) {
            throw e;
        }
        return lst;
    }
}
