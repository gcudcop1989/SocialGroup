package sg.presentacion.bean;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import master.logica.entidades.Usuario;
import master.logica.funciones.FUsuario;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import recursos.Util;
import sg.logica.entidades.Cuenta;
import sg.logica.entidades.CuentaComision;
import sg.logica.funciones.FCuenta;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;

@ManagedBean
@ViewScoped
public class CtHomeClientes implements Serializable {

    private List<Cuenta> lstCuentas;
    private List<CuentaComision> lstComisiones;
    private CuentaComision comision;
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;
    private Usuario sessionUsuario;
    private LineChartModel lineModel;

    private double totalSaldo;

    public CtHomeClientes() {
        comision = new CuentaComision();
        sessionUsuario = new Usuario();
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
        obtenerCuentas();
    }

    @PostConstruct
    public void init() {
        obtenerSession();
        createLineModels();
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

    public void obtenerCuentas() {
        try {
            int intIdUsuario = (int) getHttpServletRequest().getSession().getAttribute("idUsuario");
            System.out.println("Cuentas obtenidas para: " + intIdUsuario);

            setLstCuentas(FCuenta.obtenerCuentasComisionesDadoTitular(intIdUsuario));

            for (int i = 0; i < getLstCuentas().size(); i++) {
                setTotalSaldo(getTotalSaldo() + getLstCuentas().get(i).getTotalComision());
            }
        } catch (Exception e) {
            System.out.println("public void obtenerCuentas() dice: " + e.getMessage());
            Util.addErrorMessage("public void obtenerCuentas() dice: " + e.getMessage());
        }
    }

    private void createLineModels() {

        setLineModel(initCategoryModel());
//        getLineModel().setTitle("Category Chart");
        getLineModel().setLegendPosition("n");
        getLineModel().setShowPointLabels(true);
        getLineModel().setAnimate(true);

        getLineModel().setZoom(true);
        lineModel.setLegendCols(5);
        
        lineModel.setSeriesColors("58BA27,FFCC33,F74A4A,F52F2F,A30303");

                
        getLineModel().getAxes().put(AxisType.X, new CategoryAxis("Paquetes adquiridos"));

        Axis yAxis = getLineModel().getAxis(AxisType.Y);
        yAxis = getLineModel().getAxis(AxisType.Y);
        yAxis.setLabel("Comisiones");
        yAxis.setMin(0);
//        yAxis.setMax(200);
    }

    private LineChartModel initCategoryModel() {
        LineChartModel model = new LineChartModel();

        try {
            int intIdUsuario = (int) getHttpServletRequest().getSession().getAttribute("idUsuario");
            List<Cuenta> lst = FCuenta.obtenerCuentasComisionesDadoTitular(intIdUsuario);

            ChartSeries directa = new ChartSeries();
            directa.setLabel("Comisión Directa");
            for (int i = 0; i < lst.size(); i++) {
                directa.set(String.valueOf(lst.get(i).getCodigo()), lst.get(i).getComisionDirecta());                
            }

            ChartSeries residual = new ChartSeries();
            residual.setLabel("Comisión Residual");
            for (int i = 0; i < lst.size(); i++) {
                residual.set(String.valueOf(lst.get(i).getCodigo()), lst.get(i).getComisionResidual());
            }

            ChartSeries total = new ChartSeries();
            total.setLabel("Total");
            for (int i = 0; i < lst.size(); i++) {
                total.set(String.valueOf(lst.get(i).getCodigo()), lst.get(i).getTotalComision());
            }
            
            model.addSeries(directa);
            model.addSeries(residual);
            model.addSeries(total);

        } catch (Exception e) {
            System.out.println("private LineChartModel initCategoryModel() dice: " + e.getMessage());
        }
        return model;
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
     * @return the lstComisiones
     */
    public List<CuentaComision> getLstComisiones() {
        return lstComisiones;
    }

    /**
     * @param lstComisiones the lstComisiones to set
     */
    public void setLstComisiones(List<CuentaComision> lstComisiones) {
        this.lstComisiones = lstComisiones;
    }

    /**
     * @return the comision
     */
    public CuentaComision getComision() {
        return comision;
    }

    /**
     * @param comision the comision to set
     */
    public void setComision(CuentaComision comision) {
        this.comision = comision;
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
     * @return the totalSaldo
     */
    public double getTotalSaldo() {
        return totalSaldo;
    }

    /**
     * @param totalSaldo the totalSaldo to set
     */
    public void setTotalSaldo(double totalSaldo) {
        this.totalSaldo = totalSaldo;
    }

    /**
     * @return the lineModel
     */
    public LineChartModel getLineModel() {
        return lineModel;
    }

    /**
     * @param lineModel the lineModel to set
     */
    public void setLineModel(LineChartModel lineModel) {
        this.lineModel = lineModel;
    }

}
