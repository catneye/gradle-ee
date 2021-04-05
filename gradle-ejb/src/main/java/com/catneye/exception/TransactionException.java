/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catneye.exception;

/**
 *
 * @author plintus
 */
public class TransactionException  extends RuntimeException{
    public TransactionException(String error) {
        super(error);
    }
}
