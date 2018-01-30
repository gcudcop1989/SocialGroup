package sg.logica.funciones;

import accesoDatos.AccesoDatos;
import java.sql.ResultSet;
import java.util.ArrayList;
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
                pago.setFechaRegistro(resultSet.getTimestamp("ts_fecha_registro"));
                pago.setSessionUsuario(FUsuario.obtenerUsuarioDadoCodigo(resultSet.getInt("int_id_usuario")));
                lst.add(pago);

            }
        } catch (Exception e) {
            throw e;
        }
        return lst;
    }
}
