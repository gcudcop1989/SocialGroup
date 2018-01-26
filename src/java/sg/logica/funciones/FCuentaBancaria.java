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
    
    
    //<editor-fold defaultstate="collapsed" desc="insertar PIF">
    public static String insertarCteBancaria(CuentaBancaria cteBan, int id) throws Exception {
        String respuesta;
        AccesoDatos accesoDatos;
        String sql;
        PreparedStatement prstm;
        ResultSet resultSet;
        try {
            accesoDatos = new AccesoDatos();
            sql = "select * from sch_social_group.f_insertar_cuenta_bancaria(?,?,?,?)";
            prstm = accesoDatos.creaPreparedSmt(sql);
            prstm.setInt(1, id);
            prstm.setString(2, cteBan.getTipoCuenta());
            prstm.setString(3, cteBan.getNumeroCuenta());
            prstm.setString(4, cteBan.getEntidad());

            resultSet = accesoDatos.ejecutaPrepared(prstm);
            if (resultSet.next()) {
                respuesta = resultSet.getString(1);
                return respuesta;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw e;
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="editar PIF">
    public static String editarCteBancaria(CuentaBancaria cteBan, int id) throws Exception {
        String respuesta;
        AccesoDatos accesoDatos;
        String sql;
        PreparedStatement prstm;
        ResultSet resultSet;
        try {
            accesoDatos = new AccesoDatos();
            sql = "select * from sch_social_group.f_actualizar_cuneta_bancaria(?,?,?,?,?)";
            prstm = accesoDatos.creaPreparedSmt(sql);
            prstm.setInt(1, cteBan.getIdCuenta());
            prstm.setString(2, cteBan.getTipoCuenta());
            prstm.setString(3, cteBan.getNumeroCuenta());
            prstm.setString(4, cteBan.getEntidad());
            prstm.setInt(5, id);

            resultSet = accesoDatos.ejecutaPrepared(prstm);
            if (resultSet.next()) {
                respuesta = resultSet.getString(1);
                return respuesta;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw e;
        }
    }


    //</editor-fold>
 //<editor-fold defaultstate="collapsed" desc="editar PIF">
    public static String eliminarCteBancaria(CuentaBancaria cteBan, int id) throws Exception {
        String respuesta;
        AccesoDatos accesoDatos;
        String sql;
        PreparedStatement prstm;
        ResultSet resultSet;
        try {
            accesoDatos = new AccesoDatos();
            sql = "select * from sch_social_group.f_eliminar_cuenta_bancaria(?,?)";
            prstm = accesoDatos.creaPreparedSmt(sql);
            prstm.setInt(1, cteBan.getIdCuenta());
            prstm.setInt(2, id);

            resultSet = accesoDatos.ejecutaPrepared(prstm);
            if (resultSet.next()) {
                respuesta = resultSet.getString(1);
                return respuesta;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw e;
        }
    }
    
    
    
    
}
