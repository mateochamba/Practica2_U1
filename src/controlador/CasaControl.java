package controlador;

import controlador.dao.DaoImplement;
import controlador.ed.listas.DynamicList;
import modelo.Casa;

/**
 *
 * @author FA506ICB-HN114W
 */
public class CasaControl extends DaoImplement<Casa> {

    private Casa casa;
    private DynamicList<Casa> casaList;

    public CasaControl() {
        super(Casa.class);
        this.setCasaList(new DynamicList<>());

    }

    public void guardar() {
        Casa nuevaC = getCasa();
        getCasaList().add(nuevaC);
    }

    public void imprimir() {
        System.out.println("Casa Registrado");
        for (int i = 0; i < getCasaList().getLength(); i++) {
            try {
                Casa c = getCasaList().getInfo(i);
                System.out.println(casa);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Boolean persit() {
        casa.setId(all().getLength() + 1);
        return persit(casa);
    }

    public Casa getCasa() {
        if (casa == null) {
            casa = new Casa();
        }
        return casa;
    }

    public void setCasa(Casa casa) {
        this.casa = casa;
    }

    public DynamicList<Casa> getCasaList() {
        casaList = all();
        return casaList;
    }

    public void setCasaList(DynamicList<Casa> casaList) {
        this.casaList = casaList;
    }

    public void modificar(Integer index) throws Exception {
        this.merge(casa, index);
    }
}
