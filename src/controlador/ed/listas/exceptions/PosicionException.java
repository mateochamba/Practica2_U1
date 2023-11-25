/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.ed.listas.exceptions;

/**
 *
 * @author mateo
 */
public class PosicionException extends Exception {

    /**
     * Creates a new instance of <code>PosicionException</code> without detail
     * message.
     */
    public PosicionException() {
    }

    /**
     * Constructs an instance of <code>PosicionException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public PosicionException(String msg) {
        super("No existe la posicion en tu lista");
    }
}
