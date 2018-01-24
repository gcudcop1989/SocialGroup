package master.logica.funciones;

import accesoDatos.AccesoDatos;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import master.logica.entidades.Rol;
import master.logica.entidades.RolUsuario;
import master.logica.entidades.Usuario;

public class FRolUsuario {

    public static List<RolUsuario> obtenerRolesDadoUsuario(int idUsuario) throws Exception {
        List<RolUsuario> lst = new ArrayList<>();
        AccesoDatos accesoDatos;
        PreparedStatement stm;
        RolUsuario ru;
        ResultSet resultSet;
        String consulta;
        try {
            accesoDatos = new AccesoDatos();
            consulta = "select * from sch_admin.f_obtener_roles_dado_usuario(?)";
            stm = accesoDatos.creaPreparedSmt(consulta);
            stm.setInt(1, idUsuario);
            resultSet = accesoDatos.ejecutaPrepared(stm);

            while (resultSet.next()) {
                ru = new RolUsuario();
                ru.getRol().setIdRol(resultSet.getInt("int_id_rol"));
                ru.getRol().setRol(resultSet.getString("chv_rol"));
                ru.getRol().setDescripcion(resultSet.getString("descripcion_rol"));
                ru.getRol().setIcono(resultSet.getString("icono_rol"));
                ru.getUsuario().setIdPersona(resultSet.getInt("sr_id_persona"));
                ru.setPrivInsertar(resultSet.getInt("int_priv_insertar"));
                ru.setPrivEditar(resultSet.getInt("int_priv_editar"));
                ru.setPrivEliminar(resultSet.getInt("int_priv_eliminar"));
                ru.setPrivSeleccionar(resultSet.getInt("int_priv_seleccionar"));
                lst.add(ru);
            }
        } catch (Exception e) {
            throw e;
        }
        return lst;
    }

    public static RolUsuario obtenerRolUsuario(int idRol, int idUsuario) throws Exception {
        RolUsuario ru = null;
        AccesoDatos accesoDatos;
        String sql;
        PreparedStatement prstm;
        ResultSet resultSet;
        try {
            accesoDatos = new AccesoDatos();
            sql = "SELECT * FROM sch_admin.f_obtener_rol_usuario_dado_codigos(?,?)";
            prstm = accesoDatos.creaPreparedSmt(sql);
            prstm.setInt(1, idRol);
            prstm.setInt(2, idUsuario);
            resultSet = accesoDatos.ejecutaPrepared(prstm);
            while (resultSet.next()) {
                ru = new RolUsuario();
                ru.setRol(FRol.obtenerRolDadoCodigo(resultSet.getInt("int_id_rol")));
                ru.setUsuario(FUsuario.obtenerUsuarioDadoCodigo(resultSet.getInt("int_id_usuario")));
                ru.setPrivInsertar(resultSet.getInt("int_priv_insertar"));
                ru.setPrivEditar(resultSet.getInt("int_priv_editar"));
                ru.setPrivEliminar(resultSet.getInt("int_priv_eliminar"));
                ru.setPrivSeleccionar(resultSet.getInt("int_priv_seleccionar"));
            }
            accesoDatos.desconectar();
        } catch (Exception e) {
            throw e;
        }
        return ru;
    }

    public static List<RolUsuario> obtenerUsuariosDadoRol(int idRol) throws Exception {
        List<RolUsuario> lst = new ArrayList<>();
        AccesoDatos accesoDatos;
        PreparedStatement stm;
        RolUsuario ru;
        ResultSet resultSet;
        String consulta;
        try {
            accesoDatos = new AccesoDatos();
            consulta = "select * from sch_admin.f_obtener_usuarios_dado_codigo_rol(?)";
            stm = accesoDatos.creaPreparedSmt(consulta);
            stm.setInt(1, idRol);
            resultSet = accesoDatos.ejecutaPrepared(stm);

            while (resultSet.next()) {
                ru = new RolUsuario();
                ru.getRol().setIdRol(resultSet.getInt("int_id_rol"));
                ru.getRol().setRol(resultSet.getString("chv_rol"));
                ru.getRol().setDescripcion(resultSet.getString("chv_descripcion"));
                ru.getUsuario().setIdPersona(resultSet.getInt("sr_id_persona"));
                ru.getUsuario().setIdUsuario(resultSet.getInt("int_id_usuario"));
                ru.getUsuario().setCedula(resultSet.getString("chv_cedula"));
                ru.getUsuario().setRuc(resultSet.getString("chv_ruc"));
                ru.getUsuario().setPasaporte(resultSet.getString("chv_pasaporte"));
                ru.getUsuario().setNombres(resultSet.getString("chv_nombres"));
                ru.getUsuario().setApellidos(resultSet.getString("chv_apellidos"));
                ru.getUsuario().setCelular(resultSet.getString("chv_celular"));
                ru.getUsuario().setTelefono(resultSet.getString("chv_telefono"));
                ru.getUsuario().setFoto(resultSet.getString("chv_foto"));
                ru.getUsuario().setFechaNacimiento(resultSet.getDate("dt_fecha_nacimiento"));
                ru.getUsuario().setGenero(resultSet.getString("ch_genero"));
                ru.getUsuario().setEstadoCivil(resultSet.getString("chv_estado_civil"));
                ru.getUsuario().setCiudad(resultSet.getString("chv_ciudad"));
                ru.getUsuario().setDireccion(resultSet.getString("chv_direccion"));
                ru.getUsuario().setNick(resultSet.getString("chv_nick"));
                ru.getUsuario().setMail(resultSet.getString("chv_mail"));
                ru.getUsuario().setPassword(resultSet.getString("chv_password"));
                ru.getUsuario().setFechaRegistro(resultSet.getTimestamp("fecha_registro_rol_usuario"));
                ru.getUsuario().setEstadoLogico(resultSet.getString("ch_estado_logico"));
                lst.add(ru);
            }
        } catch (Exception e) {
            throw e;
        }
        return lst;
    }

    public static List<RolUsuario> obtenerRolesUsuariosDadoEstado(String estado) throws Exception {
        List<RolUsuario> lst = new ArrayList<>();
        AccesoDatos accesoDatos;
        PreparedStatement stm;
        RolUsuario ru;
        ResultSet resultSet;
        String consulta;
        try {
            accesoDatos = new AccesoDatos();
            consulta = "select * from sch_admin.f_obtener_roles_usuarios_dado_estado(?)";
            stm = accesoDatos.creaPreparedSmt(consulta);
            stm.setString(1, estado);
            resultSet = accesoDatos.ejecutaPrepared(stm);

            while (resultSet.next()) {
                ru = new RolUsuario();
                ru.setUsuario(new Usuario());
                ru.setRol(new Rol());
                ru.getRol().setIdRol(resultSet.getInt("int_id_rol"));
                ru.getRol().setRol(resultSet.getString("chv_rol"));
                ru.getRol().setDescripcion(resultSet.getString("chv_descripcion"));
                ru.getUsuario().setIdPersona(resultSet.getInt("sr_id_persona"));
                ru.getUsuario().setIdUsuario(resultSet.getInt("int_id_usuario"));
                ru.getUsuario().setNombres(resultSet.getString("chv_nombres"));
                ru.getUsuario().setApellidos(resultSet.getString("chv_apellidos"));
                ru.getUsuario().setNick(resultSet.getString("chv_nick"));
                ru.getUsuario().setMail(resultSet.getString("chv_mail"));
                ru.setFechaRegistro(resultSet.getTimestamp("ts_fecha_registro"));
                lst.add(ru);
            }
        } catch (Exception e) {
            throw e;
        }
        return lst;
    }

    public static String asignarRolUsuario(RolUsuario rolUsuario) throws Exception {
        String respuesta;
        AccesoDatos accesoDatos;
        String sql;
        PreparedStatement prstm;
        ResultSet resultSet;
        try {
            accesoDatos = new AccesoDatos();
            sql = "select * from sch_admin.f_asignar_rol_usuario(?,?,?)";
            prstm = accesoDatos.creaPreparedSmt(sql);
            prstm.setInt(1, rolUsuario.getRol().getIdRol());
            prstm.setInt(2, rolUsuario.getUsuario().getIdUsuario());
            prstm.setInt(3, rolUsuario.getSessionUsuario().getIdUsuario());
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

    public static String editarRolUsuario(RolUsuario rolUsuario) throws Exception {
        String respuesta;
        AccesoDatos accesoDatos;
        String sql;
        PreparedStatement prstm;
        ResultSet resultSet;
        try {
            accesoDatos = new AccesoDatos();
            sql = "select * from sch_admin.f_editar_rol_usuario(?,?,?)";
            prstm = accesoDatos.creaPreparedSmt(sql);
            prstm.setInt(1, rolUsuario.getRol().getIdRol());
            prstm.setInt(2, rolUsuario.getUsuario().getIdUsuario());
            prstm.setInt(3, rolUsuario.getSessionUsuario().getIdUsuario());
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
