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
    public List<ProductInfo> getProducts();
    public ProductInfo getProduct(Integer id);
    public List<ProductInfo> getProducts(String name);
    public ProductInfo setProduct(ProductInfo product);
    public ProductInfo removeProduct(Integer id);
}
