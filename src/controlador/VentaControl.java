/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import controlador.dao.DaoImplement;
import controlador.ed.listas.DynamicList;
import modelo.Venta;

/**
 *
 * @author FA506ICB-HN114W
 */
public class VentaControl extends DaoImplement<Venta> {

    private Venta venta;
    private DynamicList<Venta> venList;

    public VentaControl() {
        super(Venta.class);
        this.setVenList(new DynamicList<>());
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public DynamicList<Venta> getVenList() {
        return venList;
    }

    public void setVenList(DynamicList<Venta> venList) {
        this.venList = venList;
    }
    
    

}
