/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catneye.bean;

import com.catneye.db.Product;
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
    public List<Product> getProducts() {
        Query query = em.createNamedQuery("Product.findAll");
        List<Product> ret = query.getResultList();
        return ret;
    }

    @Override
    public Product getProduct(Integer id) {
        Product ret = null;
        try {
            Query query = em.createNamedQuery("Product.findById");
            query.setParameter("id", id);
            ret = (Product) query.getSingleResult();
        } catch (NoResultException ex) {
            Logger.getLogger(ProductBean.class.getName()).log(Level.WARNING, "getProduct : {0} NoResultException", id);
        }
        return ret;
    }

    @Override
    public List<Product> getProducts(String name) {
        Query query = em.createNamedQuery("Product.findByName");
        query.setParameter("name", name);
        List<Product> ret = query.getResultList();
        return ret;
    }

    @Override
    public Product setProduct(Product product) {

        Product ret = null;
        if (product.getId() != null) {
            Query query = em.createNamedQuery("Product.findById");
            query.setParameter("id", product.getId());
            try {
                ret = (Product) query.getSingleResult();
                ret.setAdddate(product.getAdddate());
                ret.setName(product.getName());
                ret.setPrice(product.getPrice());

                em.merge(ret);
                em.flush();
            } catch (NoResultException ex) {
                Logger.getLogger(ProductBean.class.getName()).log(Level.INFO, "getProduct : {0} NoResultException, Create new ", product.getId());
            }
        }
        if (ret == null) {
            ret = new Product();
            ret.setAdddate(product.getAdddate());
            ret.setName(product.getName());
            ret.setPrice(product.getPrice());
            em.persist(ret);
            em.flush();
        }
        return ret;
    }

    @Override
    public void removeProduct(Integer id) {

        Product ret = null;
        try {
            Query query = em.createNamedQuery("Product.findById");
            query.setParameter("id", id);
            ret = (Product) query.getSingleResult();
            em.remove(ret);
        } catch (NoResultException ex) {
            Logger.getLogger(ProductBean.class.getName()).log(Level.INFO, "removeProduct : {0} NoResultException", id);
        }
    }
}
