package modelo;

import java.util.Date;

/**
 *
 * @author FA506ICB-HN114W
 */
public class Venta {

    private Integer id;
    private Casa casaVendida;
    private Vendedor vendedor;
    private Date fecha;

    public Venta() {
    }

    public Venta(Integer id, Casa casaVendida, Vendedor vendedor, Date fecha) {
        this.id = id;
        this.casaVendida = casaVendida;
        this.vendedor = vendedor;
        this.fecha = fecha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Casa getCasaVendida() {
        return casaVendida;
    }

    public void setCasaVendida(Casa casaVendida) {
        this.casaVendida = casaVendida;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

}
