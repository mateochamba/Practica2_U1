package controlador.ed.listas;

import controlador.ed.listas.exceptions.EmptyException;
import controlador.ed.listas.exceptions.PosicionException;


/**
 *
 * @author FA506ICB-HN114W
 */
public class DynamicList<E> {

    private Nodo<E> header;
    private Nodo<E> last;
    private Integer length;

    /**
     * @return the length
     */
    public Integer getLength() {
        return length;
    }

    /**
     * @param length the length to set
     */
    public void setLength(Integer length) {
        this.length = length;
    }

    public DynamicList() {
        header = null;
        last = null;
        length = 0;
    }

    public DynamicList(Nodo<E> header, Nodo<E> last, Integer length) {
        this.header = header;
        this.last = last;
        this.length = length;
    }

    // refactorizar codigo
    public Boolean isEmpty() {
        return (header == null || getLength().intValue() == 0);

    }

    private void addHeader(E info) {
        Nodo<E> help;
        if (isEmpty()) {
            help = new Nodo<>(info);
            header = help;
            last = help;
            setLength((Integer) (getLength() + 1));
        } else {
            Nodo<E> headHelp = header;
            help = new Nodo<>(info, headHelp);
            header = help;
            setLength((Integer) (getLength() + 1));
        }
    }

    private void addLast(E info) {
        Nodo<E> help;
        if (isEmpty()) {
            addHeader(info);
        } else {
            help = new Nodo<>(info, null);
            last.setNext(help);
            last = help;
            setLength((Integer) (getLength() + 1));
        }
    }

    public void add(E info) {
        addLast(info);
    }

    private E getFirst() throws EmptyException {
        if (isEmpty()) {
            throw new EmptyException("esta vacia");
        }
        return header.getInfo();
    }

    private E getLast() throws EmptyException {
        if (isEmpty()) {
            throw new EmptyException("esta vacia");
        }
        return last.getInfo();
    }

    public E getInfo(Integer index) throws EmptyException, IndexOutOfBoundsException {

        return getNodo(index).getInfo();

    }

    private Nodo<E> getNodo(Integer index) throws EmptyException, IndexOutOfBoundsException {
        if (isEmpty()) {
            throw new EmptyException("esta vacia");

        } else if (index.intValue() < 0 || index.intValue() >= getLength()) {
            throw new IndexOutOfBoundsException("Error fuera de rango");
        } else if (index.intValue() == 0) {
            return header;
        } else if (index.intValue() == (getLength() - 1)) {
            return last;
        } else {
            Nodo<E> search = header;
            int cont = 0;
            while (cont < index.intValue()) {
                cont++;
                search = search.getNext();
            }
            return search;
        }
    }

    public void add(E info, Integer index) throws EmptyException, IndexOutOfBoundsException {
        if (isEmpty() || index.intValue() == 0) {
            addHeader(info);

        } else if (index.intValue() == getLength()) {
            addLast(info);

        } else {

            Nodo<E> search_preview = getNodo(index - 1);
            Nodo<E> search = getNodo(index);
            Nodo<E> help = new Nodo<>(info, search);
            search_preview.setNext(help);
            setLength((Integer) (getLength() + 1));
        }
    }
//
    public void update(Integer pos, E dato) throws EmptyException, PosicionException {
        if (isEmpty()) {
            throw new EmptyException();
        } else {
            if (pos >= 0 && pos < getLength()) {
                if (pos == 0) {
                    dato = header.getInfo();

                } else {
                    Nodo<E> aux = header;
                    for (int i = 0; i < pos; i++) {
                        aux = aux.getNext();
                    }
                    aux.setInfo(dato);
                }
            } else {
                throw new PosicionException();
            }
        }

    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder("List data \n");
        try {
            Nodo<E> help = header;
            while (help != null) {
                sb.append(help.getInfo()).append("\n");
                help = help.getNext();
            }
        } catch (Exception e) {
            sb.append(e.getMessage());
        }
        return sb.toString();
    }

}
