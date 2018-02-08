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
import sg.logica.entidades.Compra;
import sg.logica.funciones.FCompra;

@ManagedBean
@ViewScoped
public class CtActivarCompras implements Serializable {

    private Compra compraSel;
    private List<Compra> lstCompras;
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;
    private Usuario sessionUsuario;
    private Usuario objCliente;

    public CtActivarCompras() {
        compraSel = new Compra();
        sessionUsuario = new Usuario();
        objCliente = new Usuario();
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
        obtenerCompras();
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

    public void obtenerCompras() {
        try {
            setLstCompras(FCompra.obtenerComprasPorActivar());
            System.out.println("Total compras: " + getLstCompras().size());
        } catch (Exception e) {
            System.out.println("public void obtenerCompras() dice: " + e.getMessage());
            Util.addErrorMessage("public void obtenerCompras() dice: " + e.getMessage());
        }
    }

       public void activarCompra() {
        //objCliente = new Usuario();
        //CtActivarCompras objCtActivarCompras = new CtActivarCompras();
        try {

            System.out.println("Compra " + compraSel.getIdCompra() + " cuenta: " + compraSel.getCuenta().getIdCuenta()+"D CLIENTE:    "+ compraSel.getCuenta().getPersona().getIdPersona());
            String codigoCompra = compraSel.getCuenta().getCodigo();
            String msg = FCompra.activarCompra(compraSel, sessionUsuario.getIdUsuario());
            Util.addSuccessMessage(msg);
            setObjCliente(FUsuario.obtenerUsuarioDadoCodigo(compraSel.getCuenta().getPersona().getIdPersona()));
            CtActivarCompras objCtActivarCompras= new CtActivarCompras();
            objCtActivarCompras.enviarMensajeActivacion(objCliente.getMail(), codigoCompra);
            obtenerCompras();
            compraSel = new Compra();
            resetearDataTable("frmPrincipal:tblCompras");
            DefaultRequestContext.getCurrentInstance().execute("PF('dlgActivarCompra').hide()");
            objCliente = new Usuario();

        } catch (Exception e) {
            System.out.println("public void activarCompra() dice: " + e.getMessage());
            Util.addErrorMessage("public void activarCompra() dice: " + e.getMessage());
        }
    }
   
 public void reportarCompra() {
        //objCliente = new Usuario();
        //CtActivarCompras objCtActivarCompras = new CtActivarCompras();
        try {

            System.out.println("Compra " + compraSel.getIdCompra() + " cuenta: " + compraSel.getCuenta().getIdCuenta()+"D CLIENTE:    "+ compraSel.getCuenta().getPersona().getIdPersona());
            String codigoCompra = compraSel.getCuenta().getCodigo();
            String msg = FCompra.reportarCompra(compraSel, sessionUsuario.getIdUsuario());
            Util.addSuccessMessage(msg);
            setObjCliente(FUsuario.obtenerUsuarioDadoCodigo(compraSel.getCuenta().getPersona().getIdPersona()));
            CtActivarCompras objCtActivarCompras= new CtActivarCompras();
            objCtActivarCompras.enviarMensajeReporteDoc(objCliente.getMail(), codigoCompra);
            obtenerCompras();
            compraSel = new Compra();
            resetearDataTable("frmPrincipal:tblCompras");
            DefaultRequestContext.getCurrentInstance().execute("PF('dlgSolicitarReVerificar').hide()");
            objCliente = new Usuario();

        } catch (Exception e) {
            System.out.println("public void activarCompra() dice: " + e.getMessage());
            Util.addErrorMessage("public void activarCompra() dice: " + e.getMessage());
        }
    }
 
    //<editor-fold defaultstate="collapsed" desc="Enviar Mensaje al correo de Documento NO VALIDADO">
    public void enviarMensajeReporteDoc(String strEmail,  String codigoCompra ) {
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
                    + "        <h2 style=\"text-align:center;\">Activación de Compra de Paquetes de Independencia Financiera</h2>\n"
                    + "            <p style=\"padding-left:40px; padding-right:40; text-align:justify;\">\n"
                    + "                ESTIMADO@ USUARIO: " + strEmail + "\n"
                    + "                <br>\n"
                    + "                <br>\n"
                    + "                CODIGO DE COMPRA: " +  codigoCompra+ "\n"
                    + "                <br>\n"
                    + "                <br>\n"
                    + "                ESTADO DE ACTIVACIÓN: Documento enviado NO Válido,la activación de la compra no se realizó.  \n" 
                    + "                                      Por favor reenvíe un nuevo documento."
                    + "                <br>\n"
                    + "                <br>\n"
                    + "                 SocialGroup agradece por su confianza.\n"
                    + "                <br>\n"
                    + "                <br>\n"
                    + "                 Ingreso al Sistema <a href=http://64.15.146.126/SocialGroup>www.SocialGroup.com</a></p>\n"
                    + "            <br>\n"
                    + "                \n"
                    + "            </p>\n"
                    + "        </div>\n"
                    + "        <div style=\"background-color:#112f54; width:auto;  color:#fff; padding-left:150px; \">www.SocialGroup.com - Derechos Reservados &#169;\n"
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

    //</editor-fold>   
    //<editor-fold defaultstate="collapsed" desc="Enviar Mensaje al correo">
    public void enviarMensajeActivacion(String strEmail,  String codigoCompra ) {
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
                    + "        <h2 style=\"text-align:center;\">Activación de Compra de Paquetes de Independencia Financiera</h2>\n"
                    + "            <p style=\"padding-left:40px; padding-right:40; text-align:justify;\">\n"
                    + "                ESTIMADO@ USUARIO: " + strEmail + "\n"
                    + "                <br>\n"
                    + "                <br>\n"
                    + "                CODIGO DE COMPRA: " +  codigoCompra+ "\n"
                    + "                <br>\n"
                    + "                <br>\n"
                    + "                ESTADO DE ACTIVACIÓN: La Cuenta ha sido Verificada y Activada Exitosamente\n" 
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

    //</editor-fold>    
    
    public void resetearDataTable(String id) {
        DataTable table = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent(id);
        table.reset();
    }

    /**
     * @return the compraSel
     */
    public Compra getCompraSel() {
        return compraSel;
    }

    /**
     * @param compraSel the compraSel to set
     */
    public void setCompraSel(Compra compraSel) {
        this.compraSel = compraSel;
    }

    /**
     * @return the lstCompras
     */
    public List<Compra> getLstCompras() {
        return lstCompras;
    }

    /**
     * @param lstCompras the lstCompras to set
     */
    public void setLstCompras(List<Compra> lstCompras) {
        this.lstCompras = lstCompras;
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
       public Usuario getObjCliente() {
        return objCliente;
    }

    public void setObjCliente(Usuario objCliente) {
        this.objCliente = objCliente;
    }

}
