package sg.logica.funciones;

import accesoDatos.AccesoDatos;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import sg.logica.entidades.Publicidad;

public class FPublicidad {

    public static List<Publicidad> obtenerAnunciosDadoCliente(int idTitular) throws Exception {
        List<Publicidad> lst = new ArrayList<>();
        AccesoDatos accesoDatos;
        Publicidad publicidad;
        ResultSet resultSet;
        String sql;
        PreparedStatement stm;

        try {
            accesoDatos = new AccesoDatos();
            sql = "select * from sch_social_group.f_obtener_publicidad_dado_titular(?)";
            stm = accesoDatos.creaPreparedSmt(sql);
            stm.setInt(1, idTitular);
            resultSet = accesoDatos.ejecutaPrepared(stm);
            while (resultSet.next()) {
                publicidad = new Publicidad();
                publicidad.setIdPublicidad(resultSet.getInt("sr_id_publicidad"));
                publicidad.setPublicidad(resultSet.getString("chv_publicidad"));
                publicidad.setDescripcion(resultSet.getString("chv_descripcion"));
                publicidad.setObservaciones(resultSet.getString("chv_observaciones"));
                publicidad.setAdjunto(resultSet.getString("chv_adjunto"));
                publicidad.setFechaSolicitud(resultSet.getTimestamp("ts_fecha_solicitud"));
                publicidad.setFechaAprobacion(resultSet.getTimestamp("ts_fecha_aprobacion"));
                publicidad.setFechaFinalizacion(resultSet.getTimestamp("ts_fecha_finalizacion"));
                // datos cuenta
                publicidad.getCuenta().setIdCuenta(resultSet.getInt("sr_id_cuenta"));
                publicidad.getCuenta().setCodigo(resultSet.getString("chv_codigo"));
                publicidad.getCuenta().getPersona().setIdPersona(resultSet.getInt("sr_id_persona"));
                publicidad.getCuenta().getPersona().setCedula(resultSet.getString("chv_pasaporte"));
                publicidad.getCuenta().getPersona().setPasaporte(resultSet.getString("chv_pasaporte"));
                publicidad.getCuenta().getPersona().setNombres(resultSet.getString("chv_nombres"));
                publicidad.getCuenta().getPersona().setApellidos(resultSet.getString("chv_apellidos"));
                publicidad.getEstado().setIdEstadoCuenta(resultSet.getInt("int_id_estado_solicitud"));
                publicidad.getEstado().setEstado(resultSet.getString("chv_estado"));

                lst.add(publicidad);
            }
        } catch (Exception e) {
            throw e;
        }
        return lst;
    }

    public static List<Publicidad> obtenerAnunciosDadoEstado(int estado) throws Exception {
        List<Publicidad> lst = new ArrayList<>();
        AccesoDatos accesoDatos;
        Publicidad publicidad;
        ResultSet resultSet;
        String sql;
        PreparedStatement stm;

        try {
            accesoDatos = new AccesoDatos();
            sql = "select * from sch_social_group.f_obtener_publicidad_dado_estado(?)";
            stm = accesoDatos.creaPreparedSmt(sql);
            stm.setInt(1, estado);
            resultSet = accesoDatos.ejecutaPrepared(stm);
            while (resultSet.next()) {
                publicidad = new Publicidad();
                publicidad.setIdPublicidad(resultSet.getInt("sr_id_publicidad"));
                publicidad.setPublicidad(resultSet.getString("chv_publicidad"));
                publicidad.setDescripcion(resultSet.getString("chv_descripcion"));
                publicidad.setObservaciones(resultSet.getString("chv_observaciones"));
                publicidad.setAdjunto(resultSet.getString("chv_adjunto"));
                publicidad.setFechaSolicitud(resultSet.getTimestamp("ts_fecha_solicitud"));
                publicidad.setFechaAprobacion(resultSet.getTimestamp("ts_fecha_aprobacion"));
                publicidad.setFechaFinalizacion(resultSet.getTimestamp("ts_fecha_finalizacion"));
                // datos cuenta
                publicidad.getCuenta().setIdCuenta(resultSet.getInt("sr_id_cuenta"));
                publicidad.getCuenta().setCodigo(resultSet.getString("chv_codigo"));
                publicidad.getCuenta().getPersona().setIdPersona(resultSet.getInt("sr_id_persona"));
                publicidad.getCuenta().getPersona().setCedula(resultSet.getString("chv_pasaporte"));
                publicidad.getCuenta().getPersona().setPasaporte(resultSet.getString("chv_pasaporte"));
                publicidad.getCuenta().getPersona().setNombres(resultSet.getString("chv_nombres"));
                publicidad.getCuenta().getPersona().setApellidos(resultSet.getString("chv_apellidos"));
                publicidad.getEstado().setIdEstadoCuenta(resultSet.getInt("int_id_estado_solicitud"));
                publicidad.getEstado().setEstado(resultSet.getString("chv_estado"));

                lst.add(publicidad);
            }
        } catch (Exception e) {
            throw e;
        }
        return lst;
    }

    public static String registrarPublicidad(Publicidad publicidad) throws Exception {
        String respuesta;
        AccesoDatos accesoDatos;
        String sql;
        PreparedStatement prstm;
        ResultSet resultSet;
        try {
            accesoDatos = new AccesoDatos();
            sql = "select * from sch_social_group.f_insertar_publicidad(?,?,?,?,?,?)";
            prstm = accesoDatos.creaPreparedSmt(sql);
            prstm.setInt(1, publicidad.getCuenta().getIdCuenta());
            prstm.setString(2, publicidad.getPublicidad());
            prstm.setString(3, publicidad.getDescripcion());
            prstm.setInt(4, publicidad.getSessionUsuario().getIdUsuario());
            prstm.setString(5, publicidad.getAdjunto());
            prstm.setInt(6, publicidad.getFormaPago().getIdFormaPago());
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

    public static String aprobarPublicidad(Publicidad publicidad) throws Exception {
        String respuesta;
        AccesoDatos accesoDatos;
        String sql;
        PreparedStatement prstm;
        ResultSet resultSet;
        try {
            accesoDatos = new AccesoDatos();
            sql = "select * from sch_social_group.f_aprobar_publicidad(?,?,?)";
            prstm = accesoDatos.creaPreparedSmt(sql);
            prstm.setInt(1, publicidad.getIdPublicidad());
            prstm.setString(2, publicidad.getObservaciones());
            prstm.setInt(3, publicidad.getSessionUsuario().getIdUsuario());
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

    public static String rechazarPublicidad(Publicidad publicidad) throws Exception {
        String respuesta;
        AccesoDatos accesoDatos;
        String sql;
        PreparedStatement prstm;
        ResultSet resultSet;
        try {
            accesoDatos = new AccesoDatos();
            sql = "select * from sch_social_group.f_rechazar_publicidad(?,?,?)";
            prstm = accesoDatos.creaPreparedSmt(sql);
            prstm.setInt(1, publicidad.getIdPublicidad());
            prstm.setString(2, publicidad.getObservaciones());
            prstm.setInt(3, publicidad.getSessionUsuario().getIdUsuario());
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

    public static List<Publicidad> obtenerAnunciosActivos() throws Exception {
        List<Publicidad> lst = new ArrayList<>();
        AccesoDatos accesoDatos;
        Publicidad publicidad;
        ResultSet resultSet;
        String sql;
        try {
            accesoDatos = new AccesoDatos();
            sql = "SELECT * FROM sch_social_group.f_obtener_publicidad_activa();";
            resultSet = accesoDatos.ejecutaQuery(sql);
            while (resultSet.next()) {
                publicidad = new Publicidad();
                publicidad.setAdjunto(resultSet.getString("chv_adjunto"));
                lst.add(publicidad);
            }
        } catch (Exception e) {
            throw e;
        }
        return lst;
    }
}
