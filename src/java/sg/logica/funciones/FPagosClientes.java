package sg.logica.funciones;

import accesoDatos.AccesoDatos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import master.logica.entidades.Usuario;
import master.logica.funciones.FUsuario;
import sg.logica.entidades.PagosClientes;

public class FPagosClientes {
    
    public static List<PagosClientes> obtenerPagos() throws Exception {
        List<PagosClientes> lst = new ArrayList<>();
        AccesoDatos accesoDatos;
        PagosClientes pago;
        ResultSet resultSet;
        String sql;
        try {
            accesoDatos = new AccesoDatos();
            sql = "SELECT * FROM sch_social_group.f_obtener_pagos_clientes();";
            resultSet = accesoDatos.ejecutaQuery(sql);
            while (resultSet.next()) {
                pago = new PagosClientes();

                //datos socio
                pago.setSocio(new Usuario());
                pago.getSocio().setIdPersona(resultSet.getInt("sr_id_persona"));
                pago.getSocio().setPasaporte(resultSet.getString("chv_pasaporte"));
                pago.getSocio().setNombres(resultSet.getString("chv_nombres"));
                pago.getSocio().setApellidos(resultSet.getString("chv_apellidos"));

                //datos cuenta
                pago.getCuenta().setIdCuenta(resultSet.getInt("sr_id_cuenta"));
                pago.getCuenta().setCodigo(resultSet.getString("chv_codigo"));

                //datos 
                pago.setIdPago(resultSet.getInt("sr_id_pago"));
                pago.setFechaRegistro(resultSet.getTimestamp("ts_fecha_registro_pago"));
//                pago.setSessionUsuario(FUsuario.obtenerUsuarioDadoCodigo(resultSet.getInt("int_id_usuario")));

                pago.getSolicitud().getCuentaBancaria().setEntidad(resultSet.getString("chv_entidad"));
                pago.getSolicitud().getCuentaBancaria().setNumeroCuenta(resultSet.getString("chv_numero_cuenta"));
                pago.getSolicitud().getCuentaBancaria().setTipoCuenta(resultSet.getString("chv_tipo_cuenta"));
                
                pago.setTipoPago(resultSet.getString("chv_tipo_pago"));
                pago.setMonto(resultSet.getDouble("db_monto_pago"));
                lst.add(pago);
                
            }
        } catch (Exception e) {
            throw e;
        }
        return lst;
    }
    
    public static List<PagosClientes> obtenerPagosRangoFecha(Date fechaIn, Date fechaFin) throws Exception {
        List<PagosClientes> lst = new ArrayList<>();
        AccesoDatos accesoDatos;
        PagosClientes pago;
        ResultSet resultSet;
        String sql;
        PreparedStatement stm;
        
        try {
            accesoDatos = new AccesoDatos();
            sql = "select * from sch_social_group.f_obtener_pagos_clientes_entre_fechas(?,?)";
            stm = accesoDatos.creaPreparedSmt(sql);
            stm.setDate(1, (java.sql.Date) fechaIn);
            stm.setDate(2, (java.sql.Date) fechaFin);
            resultSet = accesoDatos.ejecutaPrepared(stm);
            while (resultSet.next()) {
                pago = new PagosClientes();

                //datos socio
                pago.setSocio(new Usuario());
                pago.getSocio().setIdPersona(resultSet.getInt("sr_id_persona"));
                pago.getSocio().setPasaporte(resultSet.getString("chv_pasaporte"));
                pago.getSocio().setNombres(resultSet.getString("chv_nombres"));
                pago.getSocio().setApellidos(resultSet.getString("chv_apellidos"));

                //datos cuenta
                pago.getCuenta().setIdCuenta(resultSet.getInt("sr_id_cuenta"));
                pago.getCuenta().setCodigo(resultSet.getString("chv_codigo"));

                //datos 
                pago.setIdPago(resultSet.getInt("sr_id_pago"));
                pago.setFechaRegistro(resultSet.getTimestamp("ts_fecha_registro_pago"));
//                pago.setSessionUsuario(FUsuario.obtenerUsuarioDadoCodigo(resultSet.getInt("int_id_usuario")));

                pago.getSolicitud().getCuentaBancaria().setEntidad(resultSet.getString("chv_entidad"));
                pago.getSolicitud().getCuentaBancaria().setNumeroCuenta(resultSet.getString("chv_numero_cuenta"));
                pago.getSolicitud().getCuentaBancaria().setTipoCuenta(resultSet.getString("chv_tipo_cuenta"));
                
                pago.setMonto(resultSet.getDouble("db_monto_pago"));
                lst.add(pago);
            }
        } catch (Exception e) {
            throw e;
        }
        return lst;
    }
    
}
