package sg.presentacion.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.component.datatable.DataTable;
import recursos.Util;
import sg.logica.entidades.PagosClientes;
import sg.logica.funciones.FPagosClientes;

@ManagedBean
@ViewScoped
public class CtResumenPagos implements Serializable {

    private Date fechaInicio;
    private Date fechaFin;
    private PagosClientes objPago;
    private List<PagosClientes> lstPagos;

    public CtResumenPagos() {
        objPago = new PagosClientes();
        obtenerPagos();
    }

    public void obtenerPagos() {
        try {
            setLstPagos(FPagosClientes.obtenerPagos());
            Util.addSuccessMessage(getLstPagos().size() + " pagos reazalizados");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            Util.addErrorMessage("public void obtenerPagos() dice: " + e.getMessage());
        }
    }

    public void obtenerPagosPorFechas() {
        try {
            java.sql.Date sqlFechaInicio = Util.sqlDate(fechaInicio);
            java.sql.Date sqlFechaFin = Util.sqlDate(fechaFin);

            System.out.println("fecha inicio: " + sqlFechaInicio);
            System.out.println("fecha fin: " + sqlFechaFin);

            setLstPagos(FPagosClientes.obtenerPagosRangoFecha(sqlFechaInicio, sqlFechaFin));
            System.out.println(getLstPagos().size() + " pagos realizados.");
            resetearDataTable("frmPrincipal:tblPagos");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            Util.addErrorMessage("public void obtenerPagosPorFechas() dice: " + e.getMessage());
        }
    }

    public void resetearDataTable(String id) {
        DataTable table = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent(id);
        table.reset();
    }

    /**
     * @return the lstPagos
     */
    public List<PagosClientes> getLstPagos() {
        return lstPagos;
    }

    /**
     * @param lstPagos the lstPagos to set
     */
    public void setLstPagos(List<PagosClientes> lstPagos) {
        this.lstPagos = lstPagos;
    }

    /**
     * @return the objPago
     */
    public PagosClientes getObjPago() {
        return objPago;
    }

    /**
     * @param objPago the objPago to set
     */
    public void setObjPago(PagosClientes objPago) {
        this.objPago = objPago;
    }

    /**
     * @return the fechaInicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the fechaFin
     */
    public Date getFechaFin() {
        return fechaFin;
    }

    /**
     * @param fechaFin the fechaFin to set
     */
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

}
