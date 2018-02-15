/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.presentacion.bean;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import master.logica.entidades.Usuario;
import master.logica.funciones.FUsuario;
import org.apache.taglibs.standard.functions.Functions;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.DefaultRequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import recursos.Util;
import sg.logica.entidades.Cuenta;
import sg.logica.entidades.FormaPago;
import sg.logica.entidades.Publicidad;
import sg.logica.funciones.FCuenta;
import sg.logica.funciones.FFormaPago;
import sg.logica.funciones.FPublicidad;

/**
 *
 * @author Geovanny Cudco
 */
@ManagedBean
@ViewScoped
public class CtPublicidadSocio implements Serializable {
    
    private List<Publicidad> lstSolicitudes;
    private Publicidad objPublicidad;
    private Publicidad publicidadSel;
    private String msg;
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;
    private Usuario sessionUsuario;
    private List<Cuenta> lstCuentas;
    private int idCuenta;
    private String observaciones;
    private List<FormaPago> lstFormasPago;
    private int idFormaPago;

    //manejo de archivos
    private String nombreDocumento;
    private UploadedFile archivoDocumento;
    //cargar configuracion del  path
    private java.util.ResourceBundle Configuracion = java.util.ResourceBundle.getBundle("recursos.rutasMedia");
    
    public CtPublicidadSocio() {
        publicidadSel = new Publicidad();
        objPublicidad = new Publicidad();
        sessionUsuario = new Usuario();
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
    }
    
    @PostConstruct
    public void init() {
        obtenerSession();
        obtenerCuentas();
        obtenerAnuncios();
        obtenerFormasPago();
    }
    
    public void obtenerFormasPago() {
        try {
            setLstFormasPago(FFormaPago.obtenerFormasPagoActivas());
        } catch (Exception e) {
            System.out.println("public void obtenerFormasPago() dice: " + e.getMessage());
        }
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
    
    public void obtenerAnuncios() {
        try {
            setLstSolicitudes(FPublicidad.obtenerAnunciosDadoCliente(getSessionUsuario().getIdPersona()));
        } catch (Exception e) {
            System.out.println("public void obtenerAnuncios() dice: " + e.getMessage());
        }
    }
    
    public void obtenerCuentas() {
        try {
            setLstCuentas(FCuenta.obtenerCuentasDadoTitular(getSessionUsuario().getIdPersona()));
            
        } catch (Exception e) {
            System.out.println("public void obtenerAnuncios() dice: " + e.getMessage());
        }
    }
    
    public void registrarPublicidad() {
        try {
            getObjPublicidad().getFormaPago().setIdFormaPago(getIdFormaPago());
            getObjPublicidad().getCuenta().setIdCuenta(getIdCuenta());
            getObjPublicidad().setSessionUsuario(getSessionUsuario());
            
            setMsg(FPublicidad.registrarPublicidad(getObjPublicidad()));
            Util.addSuccessMessage(getMsg());
            
            obtenerAnuncios();
            setObjPublicidad(new Publicidad());
            setIdCuenta(0);
            
            resetearDataTable("frmPrincipal:tblSolicitudes");
            DefaultRequestContext.getCurrentInstance().execute("PF('wdlgNuevo').hide()");
            
        } catch (Exception e) {
            System.out.println("public void registrarPublicidad() dice: " + e.getMessage());
            Util.addErrorMessage(e.getMessage());
        }
    }
    
    public void resetearDataTable(String id) {
        DataTable table = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent(id);
        table.reset();
    }
    
    public void cargarArchivoDocumento(FileUploadEvent e) {
        System.out.println("Entra al método cargar documento");
        UploadedFile file = e.getFile();
        this.setArchivoDocumento(file);
        System.out.println(file.getContentType());
        // getObjDocumento().setTipo(file.getContentType());
        System.out.println(file.getSize());
        System.out.println(file.getFileName());
        setNombreDocumento(file.getFileName());
        //byte[] contenido = file.getContents();
        byte[] contenido;
        try {
            contenido = this.getFileContents(e.getFile().getInputstream());
            if (guardarArchivo(file.getFileName(), contenido)) {
                Util.addSuccessMessage("Documento guardado con éxito!!");
            } else {
                Util.addErrorMessage("Error al cargar el Documento");
            }
        } catch (IOException ex) {
            Logger.getLogger(CtPublicidadSocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public boolean guardarArchivo(String nombre, byte[] contenido) {
        String rutaImagenes = getConfiguracion().getString("rutaPublicidad");
        int longitudRelativa = Integer.valueOf(getConfiguracion().getString("logitudRelativa"));
        File f = new File(rutaImagenes + nombre);
        try {
            System.out.println("PATH: " + f.getAbsolutePath());
            System.out.println("Ruta a guardar: " + Functions.substring(f.getAbsolutePath(), longitudRelativa, f.getAbsolutePath().length()));
            // getObjCliente().setPathCedula(Functions.substring(f.getAbsolutePath(), longitudRelativa, f.getAbsolutePath().length()));
            // getObjDocumento().setTitulo(nombre);

            String rutaTemp = Functions.substring(f.getAbsolutePath(), longitudRelativa, f.getAbsolutePath().length());
            //getCompraSel().setComprobante(rutaTemp.replace('\\', '/'));
            getObjPublicidad().setAdjunto(rutaTemp.replace('\\', '/'));
            
            System.out.println("Publicidad a insertar: " + getObjPublicidad().getAdjunto());
            
            System.out.println("cargar objeto fos ");
            FileOutputStream fos = new FileOutputStream(f);
            System.out.println("escribir fos ");
            fos.write(contenido);
            
            return true;
        } catch (Exception e) {
            Util.mostrarMensaje(e.getMessage());
            return false;
        }
    }
    
    private byte[] getFileContents(InputStream in) {
        byte[] bytes = null;
        try {
            // write the inputStream to a FileOutputStream            
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int read = 0;
            bytes = new byte[1024];
            
            while ((read = in.read(bytes)) != -1) {
                bos.write(bytes, 0, read);
            }
            bytes = bos.toByteArray();
            in.close();
            in = null;
            bos.flush();
            bos.close();
            bos = null;
            System.out.println("New file created!");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return bytes;
    }

    /**
     * @return the lstSolicitudes
     */
    public List<Publicidad> getLstSolicitudes() {
        return lstSolicitudes;
    }

    /**
     * @param lstSolicitudes the lstSolicitudes to set
     */
    public void setLstSolicitudes(List<Publicidad> lstSolicitudes) {
        this.lstSolicitudes = lstSolicitudes;
    }

    /**
     * @return the objPublicidad
     */
    public Publicidad getObjPublicidad() {
        return objPublicidad;
    }

    /**
     * @param objPublicidad the objPublicidad to set
     */
    public void setObjPublicidad(Publicidad objPublicidad) {
        this.objPublicidad = objPublicidad;
    }

    /**
     * @return the publicidadSel
     */
    public Publicidad getPublicidadSel() {
        return publicidadSel;
    }

    /**
     * @param publicidadSel the publicidadSel to set
     */
    public void setPublicidadSel(Publicidad publicidadSel) {
        this.publicidadSel = publicidadSel;
    }

    /**
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @param msg the msg to set
     */
    public void setMsg(String msg) {
        this.msg = msg;
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
     * @return the nombreDocumento
     */
    public String getNombreDocumento() {
        return nombreDocumento;
    }

    /**
     * @param nombreDocumento the nombreDocumento to set
     */
    public void setNombreDocumento(String nombreDocumento) {
        this.nombreDocumento = nombreDocumento;
    }

    /**
     * @return the archivoDocumento
     */
    public UploadedFile getArchivoDocumento() {
        return archivoDocumento;
    }

    /**
     * @param archivoDocumento the archivoDocumento to set
     */
    public void setArchivoDocumento(UploadedFile archivoDocumento) {
        this.archivoDocumento = archivoDocumento;
    }

    /**
     * @return the Configuracion
     */
    public java.util.ResourceBundle getConfiguracion() {
        return Configuracion;
    }

    /**
     * @param Configuracion the Configuracion to set
     */
    public void setConfiguracion(java.util.ResourceBundle Configuracion) {
        this.Configuracion = Configuracion;
    }

    /**
     * @return the lstCuentas
     */
    public List<Cuenta> getLstCuentas() {
        return lstCuentas;
    }

    /**
     * @param lstCuentas the lstCuentas to set
     */
    public void setLstCuentas(List<Cuenta> lstCuentas) {
        this.lstCuentas = lstCuentas;
    }

    /**
     * @return the idCuenta
     */
    public int getIdCuenta() {
        return idCuenta;
    }

    /**
     * @param idCuenta the idCuenta to set
     */
    public void setIdCuenta(int idCuenta) {
        this.idCuenta = idCuenta;
    }

    /**
     * @return the observaciones
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * @param observaciones the observaciones to set
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    /**
     * @return the lstFormasPago
     */
    public List<FormaPago> getLstFormasPago() {
        return lstFormasPago;
    }

    /**
     * @param lstFormasPago the lstFormasPago to set
     */
    public void setLstFormasPago(List<FormaPago> lstFormasPago) {
        this.lstFormasPago = lstFormasPago;
    }

    /**
     * @return the idFormaPago
     */
    public int getIdFormaPago() {
        return idFormaPago;
    }

    /**
     * @param idFormaPago the idFormaPago to set
     */
    public void setIdFormaPago(int idFormaPago) {
        this.idFormaPago = idFormaPago;
    }
    
}
