/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catneye.bean;

import com.catneye.db.Product;
import java.util.List;
import javax.ejb.Remote;


/**
 *
 * @author plintus
 */
@Remote
public interface ProductBeanRemote {
    public List<Product> getProducts();
    public Product getProduct(Integer id);
    public List<Product> getProducts(String name);
    public Product setProduct(Product product);
    public void removeProduct(Integer id);
}
