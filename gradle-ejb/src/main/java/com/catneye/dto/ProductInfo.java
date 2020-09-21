/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catneye.dto;

import com.catneye.util.DateUtil;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 * @author plintus
 */
public class ProductInfo {

    private Integer id;
    private String name;
    private BigDecimal price;
    private LocalDateTime adddate;
    private Boolean result = false;
    private String description;

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
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * @return the adddate
     */
    public LocalDateTime getAdddate() {
        return adddate;
    }

    public Date getAdddateUtil() {
        return DateUtil.asUtilDate(adddate);
    }

    /**
     * @param adddate the adddate to set
     */
    public void setAdddate(LocalDateTime adddate) {
        this.adddate = adddate;
    }

    public void setAdddateUtil(Date adddate) {
        this.adddate = DateUtil.asLocalDateTime(adddate);
    }

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
}
