/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catneye.dto;

import java.io.Serializable;

/**
 *
 * @author plintus
 */
public class DtoBase implements Serializable{
    private Boolean result = false;
    private String description="";
    private String debug="";

    /**
     * @return the result
     */
    public Boolean getResult() {
        return result;
    }

    /**
     * @param result the result to set
     */
    public void setResult(Boolean result) {
        this.result = result;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the debug
     */
    public String getDebug() {
        return debug;
    }

    /**
     * @param debug the debug to set
     */
    public void setDebug(String debug) {
        this.debug = debug;
    }
}
