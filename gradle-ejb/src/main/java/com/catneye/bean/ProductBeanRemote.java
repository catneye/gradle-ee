/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catneye.bean;

import com.catneye.dto.ProductInfo;
import java.util.List;
import javax.ejb.Remote;


/**
 *
 * @author plintus
 */
@Remote
public interface ProductBeanRemote {
    /**
     * Gets all Products list.
     *
     * @return this Product objects.
     */
    public List<ProductInfo> getProducts();
    /**
     * Get Product.
     * @param id This Product id.  
     * @return this Product object.
     */
    public ProductInfo getProduct(Integer id);
    /**
     * Get Products list.
     * @param name This Product name.  
     * @return this Product objects.
     */
    public List<ProductInfo> getProducts(String name);
    /**
     * Set Product.
     * @param product Is object.
     * @return this modified Product object.
     */
    public ProductInfo setProduct(ProductInfo product);
    /**
     * Remove Product.
     * @param id is Product id.  
     * @return is removed Product object.
     */
    public ProductInfo removeProduct(Integer id);
}
