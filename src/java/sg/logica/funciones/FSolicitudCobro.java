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
import sg.logica.entidades.SolicitudCobro;

/**
 *
 * @author Geovanny
 */
public class FSolicitudCobro {

    public static String registrarSolicitudGlobal(int idTitular, double monto) throws Exception {
        String respuesta;
        AccesoDatos accesoDatos;
        String sql;
        PreparedStatement prstm;
        ResultSet resultSet;
        try {
            accesoDatos = new AccesoDatos();
            sql = "select * from sch_social_group.f_registrar_solictud_global(?,?)";
            prstm = accesoDatos.creaPreparedSmt(sql);
            prstm.setInt(1, idTitular);
            prstm.setDouble(18, monto);
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

    public static String registrarSolicitudCuenta(int idCuenta, double monto) throws Exception {
        String respuesta;
        AccesoDatos accesoDatos;
        String sql;
        PreparedStatement prstm;
        ResultSet resultSet;
        try {
            accesoDatos = new AccesoDatos();
            sql = "select * from sch_social_group.f_registrar_solictud_cuenta(?,?)";
            prstm = accesoDatos.creaPreparedSmt(sql);
            prstm.setInt(1, idCuenta);
            prstm.setDouble(18, monto);
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

    public static List<SolicitudCobro> obteneSolicitudesDadoEstado(int idEstado) throws Exception {
        List<SolicitudCobro> lst = new ArrayList<>();
        AccesoDatos accesoDatos;
        PreparedStatement stm;
        SolicitudCobro solicitud;
        ResultSet resultSet;
        String consulta;
        try {
            accesoDatos = new AccesoDatos();
            consulta = "select * from sch_social_group.f_obtener_solictudes_dado_estado(?)";
            stm = accesoDatos.creaPreparedSmt(consulta);
            stm.setInt(1, idEstado);
            resultSet = accesoDatos.ejecutaPrepared(stm);
            while (resultSet.next()) {
                solicitud = new SolicitudCobro();
                solicitud.getCuenta().setCodigo(resultSet.getString("chv_codigo"));
                solicitud.getCuenta().setIdCuenta(resultSet.getInt("sr_id_cuenta"));
                solicitud.getSocio().setIdPersona(resultSet.getInt("int_id_titular"));
                solicitud.getSocio().setNombres(resultSet.getString("chv_nombres"));
                solicitud.getSocio().setApellidos(resultSet.getString("chv_apellidos"));
                solicitud.getEstado().setIdEstadoCuenta(resultSet.getInt("int_id_estado_solicitud"));
                solicitud.getEstado().setEstado(resultSet.getString("chv_estado"));
                solicitud.setMonto(resultSet.getDouble("db_monto"));
                solicitud.setTipoSolicitud(resultSet.getString("chv_tipo_solicitud"));
                solicitud.setFechaRegistro(resultSet.getTimestamp("ts_fecha_registro"));
                solicitud.setFechaActualizacion(resultSet.getTimestamp("ts_fecha_actualizacion"));
                lst.add(solicitud);
            }
        } catch (Exception e) {
            throw e;
        }
        return lst;
    }

}
