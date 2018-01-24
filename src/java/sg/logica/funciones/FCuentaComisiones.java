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
import sg.logica.entidades.CuentaComision;

/**
 *
 * @author Geovanny
 */
public class FCuentaComisiones {

    public static List<CuentaComision> obtenerComisionesDadoCuenta(int idCuenta) throws Exception {
        List<CuentaComision> lst = new ArrayList<>();
        AccesoDatos accesoDatos;
        PreparedStatement stm;
        CuentaComision comision;
        ResultSet resultSet;
        String consulta;
        try {
            accesoDatos = new AccesoDatos();
            consulta = "select * from sch_social_group.f_obtener_comisiones(?)";
            stm = accesoDatos.creaPreparedSmt(consulta);
            stm.setInt(1, idCuenta);
            resultSet = accesoDatos.ejecutaPrepared(stm);
            while (resultSet.next()) {
                comision = new CuentaComision();

                comision.setIdComision(resultSet.getInt("sr_id_comision"));
                comision.getCuenta().setIdCuenta(resultSet.getInt("int_id_cuenta"));
                comision.setComisionDirecta(resultSet.getDouble("db_comision_directa"));
                comision.setComisionResidual(resultSet.getDouble("db_comision_residual"));
                comision.setTotalComision(resultSet.getDouble("db_total_comision"));

                lst.add(comision);
            }
        } catch (Exception e) {
            throw e;
        }
        return lst;
    }

    public static List<CuentaComision> obtenerComisionesDadoCodigoCuenta(String codigo) throws Exception {
        List<CuentaComision> lst = new ArrayList<>();
        AccesoDatos accesoDatos;
        PreparedStatement stm;
        CuentaComision comision;
        ResultSet resultSet;
        String consulta;
        try {
            accesoDatos = new AccesoDatos();
            consulta = "select * from sch_social_group.f_obtener_comisiones_dado_codigo_cuenta(?)";
            stm = accesoDatos.creaPreparedSmt(consulta);
            stm.setString(1, codigo);
            resultSet = accesoDatos.ejecutaPrepared(stm);
            while (resultSet.next()) {
                comision = new CuentaComision();

                comision.setIdComision(resultSet.getInt("sr_id_comision"));
                comision.getCuenta().setIdCuenta(resultSet.getInt("int_id_cuenta"));
                comision.setComisionDirecta(resultSet.getDouble("db_comision_directa"));
                comision.setComisionResidual(resultSet.getDouble("db_comision_residual"));
                comision.setTotalComision(resultSet.getDouble("db_total_comision"));

                lst.add(comision);
            }
        } catch (Exception e) {
            throw e;
        }
        return lst;
    }

    public static CuentaComision obtenerComisionesDadoCuenta(String codigo) throws Exception {
        AccesoDatos accesoDatos;
        CuentaComision comision = null;
        ResultSet resultSet;
        String sql;
        PreparedStatement prstm;
        try {
            accesoDatos = new AccesoDatos();
            sql = "select * from sch_social_group.f_obtener_comisiones_dado_codigo_cuenta(?);";
            prstm = accesoDatos.creaPreparedSmt(sql);
            prstm.setString(1, codigo);
            resultSet = accesoDatos.ejecutaPrepared(prstm);
            while (resultSet.next()) {
                comision = new CuentaComision();
                comision.setIdComision(resultSet.getInt("sr_id_comision"));
                comision.getCuenta().setIdCuenta(resultSet.getInt("int_id_cuenta"));
                comision.setComisionDirecta(resultSet.getDouble("db_comision_directa"));
                comision.setComisionResidual(resultSet.getDouble("db_comision_residual"));
                comision.setTotalComision(resultSet.getDouble("db_total_comision"));
            }
            accesoDatos.desconectar();
        } catch (Exception e) {
            throw e;
        }
        return comision;
    }
    
    public static List<CuentaComision> obtenerComisionesDadoSocio(int socio) throws Exception {
        List<CuentaComision> lst = new ArrayList<>();
        AccesoDatos accesoDatos;
        PreparedStatement stm;
        CuentaComision comision;
        ResultSet resultSet;
        String consulta;
        try {
            accesoDatos = new AccesoDatos();
            consulta = "select * from sch_social_group.f_obtener_comisiones_dado_socio(?)";
            stm = accesoDatos.creaPreparedSmt(consulta);
            stm.setInt(1, socio);
            resultSet = accesoDatos.ejecutaPrepared(stm);
            while (resultSet.next()) {
                comision = new CuentaComision();

                comision.setIdComision(resultSet.getInt("sr_id_comision"));
                comision.getCuenta().setIdCuenta(resultSet.getInt("int_id_cuenta"));
                comision.setComisionDirecta(resultSet.getDouble("db_comision_directa"));
                comision.setComisionResidual(resultSet.getDouble("db_comision_residual"));
                comision.setTotalComision(resultSet.getDouble("db_total_comision"));

                lst.add(comision);
            }
        } catch (Exception e) {
            throw e;
        }
        return lst;
    }
    
    
}
