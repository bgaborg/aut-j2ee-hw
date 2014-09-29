/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bg.ebank.exceptions;

import javax.ejb.ApplicationException;

/**
 *
 * @author bg
 */
@ApplicationException(rollback=true)
public class BankException extends Exception {

    /**
     * Creates a new instance of
     * <code>BankException</code> without detail message.
     */
    public BankException() {
    }

    /**
     * Constructs an instance of
     * <code>BankException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public BankException(String msg) {
        super(msg);
    }
}
