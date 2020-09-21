/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catneye.bean;

import com.catneye.dto.ProductInfo;
import com.catneye.entity.Product;
import com.catneye.util.TransformUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author plintus
 */
@Stateless
public class ProductBean implements ProductBeanRemote {

    @PersistenceContext(unitName = "gradle-ejbPU")
    private EntityManager em;

    @Override
    public List<ProductInfo> getProducts() {
        List<ProductInfo> ret = new ArrayList<>();
        Query query = em.createNamedQuery("Product.findAll");
        List<Product> data = query.getResultList();
        for (Product p : data) {
            ProductInfo pi = (ProductInfo) TransformUtil.clone(new ProductInfo(), p);
            pi.setResult(true);
            ret.add(pi);
        }
        return ret;
    }

    @Override
    public ProductInfo getProduct(Integer id) {
        ProductInfo ret = null;
        try {
            Query query = em.createNamedQuery("Product.findById");
            query.setParameter("id", id);
            Product data = (Product) query.getSingleResult();
            ret = (ProductInfo) TransformUtil.clone(new ProductInfo(), data);
            ret.setResult(true);
        } catch (NoResultException ex) {
            Logger.getLogger(ProductBean.class.getName()).log(Level.WARNING, "getProduct : {0} NoResultException", id);
        }
        return ret;
    }

    @Override
    public List<ProductInfo> getProducts(String name) {
        List<ProductInfo> ret = new ArrayList<>();
        Query query = em.createNamedQuery("Product.findByName");
        query.setParameter("name", name);
        List<Product> data = query.getResultList();
        for (Product p : data) {
            ProductInfo pi = (ProductInfo) TransformUtil.clone(new ProductInfo(), p);
            pi.setResult(true);
            ret.add(pi);
        }
        return ret;
    }

    @Override
    public ProductInfo setProduct(ProductInfo product) {

        Product data = null;
        if (product.getId() != null) {
            Query query = em.createNamedQuery("Product.findById");
            query.setParameter("id", product.getId());
            try {
                data = (Product) query.getSingleResult();
                data.setAdddate(product.getAdddateUtil());
                data.setName(product.getName());
                data.setPrice(product.getPrice());

                em.merge(data);
                em.flush();
            } catch (NoResultException ex) {
                Logger.getLogger(ProductBean.class.getName()).log(Level.INFO, "getProduct : {0} NoResultException, Create new ", product.getId());
            }
        }
        if (data == null) {
            data = new Product();
            data.setAdddate(product.getAdddateUtil());
            data.setName(product.getName());
            data.setPrice(product.getPrice());
            em.persist(data);
            em.flush();
        }

        ProductInfo pi = (ProductInfo) TransformUtil.clone(new ProductInfo(), data);
        pi.setResult(true);
        return pi;
    }

    @Override
    public ProductInfo removeProduct(Integer id) {

        ProductInfo ret = null;
        try {
            Query query = em.createNamedQuery("Product.findById");
            query.setParameter("id", id);
            Product data = (Product) query.getSingleResult();
            ret = (ProductInfo) TransformUtil.clone(new ProductInfo(), data);
            ret.setResult(true);
            em.remove(data);
        } catch (NoResultException ex) {
            Logger.getLogger(ProductBean.class.getName()).log(Level.INFO, "removeProduct : {0} NoResultException", id);
        }
        return ret;
    }
}
