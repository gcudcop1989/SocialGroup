package sg.presentacion.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import master.logica.entidades.Usuario;
import master.logica.funciones.FUsuario;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.DefaultRequestContext;
import recursos.Util;

@ManagedBean
@ViewScoped
public class CtActvarCuentas implements Serializable {

    private List<Usuario> lstClientes;
    private Usuario objCliente;
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;
    private Usuario sessionUsuario;

    public CtActvarCuentas() {
        objCliente = new Usuario();
        sessionUsuario = new Usuario();
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
        obtenerSolicitudes();
    }

    @PostConstruct
    public void init() {
        obtenerSession();
    }

    public void obtenerSession() {
        try {
            int intIdUsuario = (int) getHttpServletRequest().getSession().getAttribute("idUsuario");
            setSessionUsuario(FUsuario.obtenerUsuarioDadoCodigo(intIdUsuario));
            System.out.println("Usuario Logueado: " + getSessionUsuario().getApellidos() + "id: " + getSessionUsuario().getIdUsuario());
        } catch (Exception e) {
            System.out.println("public void obtenerSession() dice: " + e.getMessage());
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public void activarCuenta() {
        try {
            String msg = FUsuario.validarCuenta(objCliente.getIdUsuario(), sessionUsuario.getIdPersona());
            Util.addSuccessMessage(msg);
             CtActvarCuentas objCtActvarCuentas = new CtActvarCuentas();
            objCtActvarCuentas.enviarMensajeActivacionCuenta(objCliente.getMail(), objCliente.getNombres());
            obtenerSolicitudes();
            objCliente = new Usuario();
            resetearDataTable("frmPrincipal:tblSolicitudes");
            DefaultRequestContext.getCurrentInstance().execute("PF('dlgActivarCuenta').hide()");
        } catch (Exception e) {
            System.out.println("public void activarCuenta() dice: " + e.getMessage());
            Util.addErrorMessage(e.getMessage());
        }
    }

    public void obtenerSolicitudes() {
        try {
            setLstClientes(FUsuario.obtenerSolictudesActivacion());
            Util.addSuccessMessage("Tiene " + getLstClientes().size() + " solicitud(es) pendientes.");
        } catch (Exception e) {
            System.out.println("public void obtenerSolicitudes() dice: " + e.getMessage());
            Util.addErrorMessage("public void obtenerSolicitudes() dice: " + e.getMessage());
        }
    }
public void enviarMensajeActivacionCuenta(String strEmail,  String nombreUsuario ) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        final String strUsername = "njchiquita101@gmail.com";
        final String strPassWord = "programacion";
        String To = strEmail;
        String Subject = "Social Group";
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(strUsername, strPassWord);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(strUsername));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(To));
            message.setSubject(Subject);
            message.setContent(" <html style=\"font-family:calibri\">\n"
                    + "     <div style=\"-webkit-box-shadow: 20px 20px 30px -6px rgba(0,0,0,0.3);\n"
                    + "-moz-box-shadow: 20px 20px 30px -6px rgba(0,0,0,0.3);\n"
                    + "box-shadow: 20px 20px 30px -6px rgba(0,0,0,0.3); width:600px; \">\n"
                    + "        <div style=\"background-color:#112f54; width:auto; marging-left:10px\"><img style=\"padding-left:250px; padding-top:20px; padding-bottom:20px;width:100px;\" src=\"http://localhost:8080/SocialGroup/resources/imagenes/logo.png\">\n"
                    + "    </div>\n"
                    + "        <div style=\"background-color:#F5F4F3;\">\n"
                    + "        <h2 style=\"text-align:center;\">Activación de Cuenta </h2>\n"
                    + "            <p style=\"padding-left:40px; padding-right:40; text-align:justify;\">\n"
                    + "                ESTIMADO@: " + nombreUsuario + "\n"
                    + "                <br>\n"
                    + "                <br>\n"
                    + "                SU CUENTA HA SIDO VERIFICADA Y ACTIVADA EXITOSAMENTE\n" 
                    + "                <br>\n"
                    + "                <br>\n"
                    + "                SocialGroup agradece por su confianza.\n"
                    + "                <br>\n"
                    + "                <br>\n"
                    + "                 Ingreso al Sistema <a href=http://64.15.146.126/SocialGroup>www.SocialGroup.com</a></p>\n"
                    + "            <br>\n"
                    + "                \n"
                    + "            </p>\n"
                    + "        </div>\n"
                    + "        <div style=\"background-color:#112f54; width:auto;  color:#fff; padding-left:120px; \">www.SocialGroup.com - Derechos Reservados &#169;\n"
                    + "    </div>\n"
                    + "         <br>\n"
                    + "    </div>  \n"
                    + "    \n"
                    + "</html>", "text / html");
            Transport.send(message);
        } catch (MessagingException e) {
            Util.addErrorMessage("Problemas de conexión a Internet, no se ha enviado su correo electrónico, Intentelo más tarde.");
            throw new RuntimeException(e);
        }
    }
    public void resetearDataTable(String id) {
        DataTable table = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent(id);
        table.reset();
    }

    /**
     * @return the httpServletRequest
     */
    public HttpServletRequest getHttpServletRequest() {
        return httpServletRequest;
    }

    /**
     * @param httpServletRequest the httpServletRequest to set
     */
    public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    /**
     * @return the faceContext
     */
    public FacesContext getFaceContext() {
        return faceContext;
    }

    /**
     * @param faceContext the faceContext to set
     */
    public void setFaceContext(FacesContext faceContext) {
        this.faceContext = faceContext;
    }

    /**
     * @return the sessionUsuario
     */
    public Usuario getSessionUsuario() {
        return sessionUsuario;
    }

    /**
     * @param sessionUsuario the sessionUsuario to set
     */
    public void setSessionUsuario(Usuario sessionUsuario) {
        this.sessionUsuario = sessionUsuario;
    }

    /**
     * @return the lstClientes
     */
    public List<Usuario> getLstClientes() {
        return lstClientes;
    }

    /**
     * @param lstClientes the lstClientes to set
     */
    public void setLstClientes(List<Usuario> lstClientes) {
        this.lstClientes = lstClientes;
    }

    /**
     * @return the objCliente
     */
    public Usuario getObjCliente() {
        return objCliente;
    }

    /**
     * @param objCliente the objCliente to set
     */
    public void setObjCliente(Usuario objCliente) {
        this.objCliente = objCliente;
    }

}
