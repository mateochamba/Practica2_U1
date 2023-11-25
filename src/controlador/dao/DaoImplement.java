/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.dao;

import com.thoughtworks.xstream.XStream;
import controlador.ed.listas.DynamicList;
import controlador.ed.listas.exceptions.EmptyException;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Method;

/**
 *
 * @author FA506ICB-HN114W
 */
public class DaoImplement<T> implements DaoInterface<T> {

    private Class<T> clazz;
    private XStream conection;
    private String URL;

    public DaoImplement(Class<T> clazz) {
        this.clazz = clazz;
        conection = Bridge.getConection();
        URL = Bridge.URL + clazz.getSimpleName() + ".json";
    }

    @Override

    public Boolean persit(T data) {
        DynamicList<T> ld = all();
        ld.add(data);
        try {
            conection.toXML(ld, new FileWriter(URL));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public DynamicList<T> all() {
        DynamicList<T> dl = new DynamicList<>();
        try {
            dl = (DynamicList<T>) conection.fromXML(new FileReader(URL));
        } catch (Exception e) {

        }
        return dl;
    }

    @Override
    public T get(Integer id) {
        T obj = null;
        DynamicList<T> lista = all();

        for (int i = 0; i < lista.getLength(); i++) {
            try {
                T dato = lista.getInfo(i);
                if (id.intValue() == ((Integer) getValueField(dato)).intValue()) {
                    obj = dato;
                    break;
                }
            } catch (Exception e) {
                throw new RuntimeException("Error en el metodo get:" + e.getMessage(), e);
            }
        }
        return obj;
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody

    }

    private Object getValueField(T dato) throws Exception {
        Method metodo = null;
        for (Method aux : this.clazz.getDeclaredMethods()) {
            //System.out.println(aux.toString());
            if (aux.getName().toLowerCase().equalsIgnoreCase("getId")) {
                metodo = aux;
            }
        }

        if (metodo == null) {
            for (Method aux : this.clazz.getSuperclass().getDeclaredMethods()) {
                if (aux.getName().toLowerCase().equalsIgnoreCase("getId")) {
                    metodo = aux;
                }
            }
        }
        return metodo.invoke(dato);

    }

    @Override
    public Boolean merge(T data, Integer index) {

        try {
            DynamicList<T> lista = all();
            lista.add(data, index);
            
            return true;
        } catch (EmptyException | IndexOutOfBoundsException e) {
            e.printStackTrace();
            return false;
        }
    }

}
