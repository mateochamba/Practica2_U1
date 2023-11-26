package controlador;

import controlador.dao.DaoImplement;
import controlador.ed.listas.DynamicList;
import modelo.Vendedor;

/**
 *
 * @author FA506ICB-HN114W
 */
public class VendedorControl extends DaoImplement<Vendedor> {

    private Vendedor vendedor;
    private DynamicList<Vendedor> vendedorList;

    public VendedorControl() {
        super(Vendedor.class);
        this.setVendedorList(new DynamicList<>());

    }

    public void guardar() throws Exception {
        Vendedor nuevoV = getVendedor();
        getVendedorList().add(nuevoV);
    }

    public Boolean persit() {
        if (vendedor != null) {
            vendedor.setId(all().getLength() + 1);
            return persit(vendedor);
        }
        return false;
//        vendedor.setId(all().getLength() + 1);
//        return persit(vendedor);
    }

    public void modificar(Integer index) throws Exception {
        if (vendedor != null) {
            this.merge(vendedor, index);
        }
        //this.merge(vendedor, index);
    }

    public void imprimir() {
        System.out.println("Vendedor Registrado");
        for (int i = 0; i < getVendedorList().getLength(); i++) {
            try {
                Vendedor v = getVendedorList().getInfo(i);
                System.out.println(vendedor);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Vendedor getVendedor() {
        if (vendedor == null) {
            vendedor = new Vendedor();
        }
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public DynamicList<Vendedor> getVendedorList() {

        vendedorList = all();
        return vendedorList;
    }

    public void setVendedorList(DynamicList<Vendedor> vendedorList) {
        this.vendedorList = vendedorList;
    }

}
