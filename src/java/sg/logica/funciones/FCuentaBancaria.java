/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.logica.funciones;

import accesoDatos.AccesoDatos;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import sg.logica.entidades.CuentaBancaria;

/**
 *
 * @author Geovanny Cudco
 */
public class FCuentaBancaria {

    public static List<CuentaBancaria> obtenerCtasDadoSocio(int idSocio) throws Exception {
        List<CuentaBancaria> lst = new ArrayList<>();
        AccesoDatos accesoDatos;
        PreparedStatement stm;
        CuentaBancaria cuenta;
        ResultSet resultSet;
        String consulta;
        try {
            accesoDatos = new AccesoDatos();
            consulta = "select * from sch_social_group.f_obtener_cuenta_bancaria_dado_socio(?)";
            stm = accesoDatos.creaPreparedSmt(consulta);
            stm.setInt(1, idSocio);
            resultSet = accesoDatos.ejecutaPrepared(stm);
            while (resultSet.next()) {
                cuenta = new CuentaBancaria();
                cuenta.setIdCuenta(resultSet.getInt("sr_id_cuenta"));
                cuenta.setEntidad(resultSet.getString("chv_entidad"));
                cuenta.setNumeroCuenta(resultSet.getString("chv_numero_cuenta"));
                cuenta.setTipoCuenta(resultSet.getString("chv_tipo_cuenta"));
                cuenta.setFechaRegistro(resultSet.getTimestamp("ts_fecha_registro"));
                cuenta.setFechaActualizacion(resultSet.getTimestamp("ts_fecha_actualizacion"));
                lst.add(cuenta);
            }
        } catch (Exception e) {
            throw e;
        }
        return lst;
    }
}
