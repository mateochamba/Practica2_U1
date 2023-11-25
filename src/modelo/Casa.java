package modelo;

/**
 *
 * @author FA506ICB-HN114W
 */
public class Casa {
    private Integer id;
    private String dimensiones;
    private String direccion;
    private String area;
    private String tipoCasa;

    public Casa() {
    }

    public Casa(Integer id, String dimensiones, String direccion, String area, String tipoCasa) {
        this.id = id;
        this.dimensiones = dimensiones;
        this.direccion = direccion;
        this.area = area;
        this.tipoCasa = tipoCasa;
    }

   

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the dimensiones
     */
    public String getDimensiones() {
        return dimensiones;
    }

    /**
     * @param dimensiones the dimensiones to set
     */
    public void setDimensiones(String dimensiones) {
        this.dimensiones = dimensiones;
    }

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @return the area
     */
    public String getArea() {
        return area;
    }

    /**
     * @param area the area to set
     */
    public void setArea(String area) {
        this.area = area;
    }

    /**
     * @return the tipoCasa
     */
    public String getTipoCasa() {
        return tipoCasa;
    }

    /**
     * @param tipoCasa the tipoCasa to set
     */
    public void setTipoCasa(String tipoCasa) {
        this.tipoCasa = tipoCasa;
    }
    
    
}
