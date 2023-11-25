package controlador.ed.listas;

/**
 *
 * @author FA506ICB-HN114W
 */
public class Nodo  <E> {
    private E info;
    private Nodo<E> next;

    public Nodo() {
        next= null;
        info=null;
    }
    public Nodo(E info) {
        this.info = info;
        next = null;
    }
    public Nodo(E info, Nodo<E> next) {
        this.info = info;
        this.next = next;
    }

    /**
     * @return the info
     */
    public E getInfo() {
        return info;
    }

    /**
     * @param info the info to set
     */
    public void setInfo(E info) {
        this.info = info;
    }

    /**
     * @return the next
     */
    public Nodo<E> getNext() {
        return next;
    }

    /**
     * @param next the next to set
     */
    public void setNext(Nodo<E> next) {
        this.next = next;
    }
    
    
  

    
    
    
    
    
    
}
