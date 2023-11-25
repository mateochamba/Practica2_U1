package modelo;

/**
 *
 * @author FA506ICB-HN114W
 */
public class Vendedor {
    private Integer id;
    private String nombre;
    private String apellido;
    private String telf;
    private String correo;
    private String dni;

    public Vendedor() {
    }

    public Vendedor(Integer id, String nombre, String apellido, String telf, String correo, String dni) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telf = telf;
        this.correo = correo;
        this.dni = dni;
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
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the apellido
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * @param apellido the apellido to set
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * @return the telf
     */
    public String getTelf() {
        return telf;
    }

    /**
     * @param telf the telf to set
     */
    public void setTelf(String telf) {
        this.telf = telf;
    }

    /**
     * @return the correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @param correo the correo to set
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * @return the dni
     */
    public String getDni() {
        return dni;
    }

    /**
     * @param dni the dni to set
     */
    public void setDni(String dni) {
        this.dni = dni;
    }
    
    
    
    
}
