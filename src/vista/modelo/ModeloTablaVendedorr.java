package vista.modelo;

import controlador.ed.listas.DynamicList;
import javax.swing.table.AbstractTableModel;
import modelo.Vendedor;

/**
 *
 * @author FA506ICB-HN114W
 */
public class ModeloTablaVendedorr extends AbstractTableModel {

    private DynamicList<Vendedor> lista = new DynamicList<>();

    public DynamicList<Vendedor> getLista() {
        return lista;
    }

    public void setLista(DynamicList<Vendedor> lista) {
        this.lista = lista;
    }

    @Override
    public int getRowCount() {
        return lista.getLength();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int i, int i1) {
        Vendedor v = null;

        try {
            v = lista.getInfo(i);
        } catch (Exception e) {
        }
        switch (i1) {
            case 0:
                return (v != null) ? v.getNombre() : "";
            case 1:
                return (v != null) ? v.getApellido() : "";
            case 2:
                return (v != null) ? v.getTelf() : "";
            case 3:
                return (v != null) ? v.getCorreo() : "";
            case 4:
                return (v != null) ? v.getDni() : "";
            default:
                return null;
        }

    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Nombre";
            case 1:
                return "Apellido";
            case 2:
                return "Telefono";
            case 3:
                return "Correo";
            case 4:
                return "Dni";
            default:
                return null;
        }
    }

}
