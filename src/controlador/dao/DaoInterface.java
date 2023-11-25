
package controlador.dao;

import controlador.ed.listas.DynamicList;

/**
 *
 * @author FA506ICB-HN114W
 */
//e es para conjunto de datos
public interface DaoInterface<T> {

    public Boolean persit(T data);

    public Boolean merge(T data, Integer index);

    public DynamicList<T> all();

    public T get(Integer id);
}
