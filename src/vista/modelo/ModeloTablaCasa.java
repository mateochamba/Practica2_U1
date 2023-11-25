
package vista.modelo;

import controlador.ed.listas.DynamicList;
import javax.swing.table.AbstractTableModel;
import modelo.Casa;

/**
 *
 * @author FA506ICB-HN114W
 */
public class ModeloTablaCasa extends AbstractTableModel{

private DynamicList<Casa> lista = new DynamicList<>();

    public DynamicList<Casa> getLista() {
        return lista;
    }

    public void setLista(DynamicList<Casa> lista) {
        this.lista = lista;
    }

    @Override
    public int getRowCount() {
        return lista.getLength();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int i, int i1) {
        Casa c = null;

        try {
            c = lista.getInfo(i);

        } catch (Exception e) {
        }

        switch (i1) {
            case 0:
                return (c != null) ? c.getDimensiones() : "";
            case 1:
                return (c != null) ? c.getArea() : "";
            case 2:
                return (c != null) ? c.getDireccion(): "";
            case 3:
                return (c != null) ? c.getTipoCasa() : "";

            default:
                return null;
        }

    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Dimensiones";
            case 1:
                return "Area";
            case 2:
                return "Direccion";
            case 3:
                return "TipoCasa";

            default:
                return null;
        }
    }
    
}
