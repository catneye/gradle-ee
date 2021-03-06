/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catneye.servlet;

import com.catneye.bean.MessageSenderBeanRemote;
import com.catneye.bean.ProductBeanRemote;
import com.catneye.dto.ProductInfo;
import com.catneye.entity.Product;
import com.catneye.util.Constants;
import java.io.StringReader;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.json.stream.JsonParsingException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author plintus
 */
@Path("/ProductAPI")
public class ProductAPI extends Application {

    @EJB
    ProductBeanRemote pBean;
    @EJB
    MessageSenderBeanRemote msgBean;

    JsonbConfig jsonconfig = new JsonbConfig().setProperty("jsonb.to.json.encoding", "UTF-8");
    Jsonb jsonb = JsonbBuilder.create(jsonconfig);

    /**
     * Class constructor.
     */
    public ProductAPI() {
        try {
            pBean = (ProductBeanRemote) InitialContext.doLookup(Constants.SERVICE_BEAN_NAME);
            msgBean = (MessageSenderBeanRemote) InitialContext.doLookup(Constants.MESSAGE_SENDER_BEAN_NAME);
        } catch (NamingException ex) {
            Logger.getLogger(ProductAPI.class.getName()).log(Level.SEVERE, "ProductAPI : {0}", ex);
        }
    }

    /**
     * Gets all Products list.
     *
     * @return this Product objects as JSON string.
     */
    @GET
    @Path("getAllProducts")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllProducts() {
        Logger.getLogger(ProductAPI.class.getName()).log(Level.FINE, "getAllProducts : {0}", 0);
        return jsonb.toJson(pBean.getProducts());
    }

    /**
     * Get Product.
     * @param id This Product id.  
     * @return this Product object as JSON string.
     */
    @GET
    @Path("getProduct/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getProduct(@PathParam("id") Integer id) {
        Logger.getLogger(ProductAPI.class.getName()).log(Level.FINE, "getProduct : {0}", id);
        ProductInfo p = pBean.getProduct(id);
        return jsonb.toJson(p != null ? p : new Product());
    }

    /**
     * Get Products list.
     * @param name This Product name.  
     * @return this Product object as JSON string.
     */
    @GET
    @Path("getProducts/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getProducts(@PathParam("name") String name) {
        Logger.getLogger(ProductAPI.class.getName()).log(Level.FINE, "getProducts : {0}", name);
        List<ProductInfo> ps = pBean.getProducts(name);
        return jsonb.toJson(ps);
    }

    //http://127.0.0.1:8080/gradle-war/rest/ProductAPI/setProduct/{"product":{"adddate": "2020-09-10T07:05:52.234Z[UTC]","id": 1,"name": "p11","price": 12.12}}
    
    /**
     * Set Product.
     * @param obj Is JSON string.
     * example: {"product":{"adddate": "2020-09-10T07:05:52.234Z[UTC]","id": 1,"name": "p11","price": 12.12}}
     * @return this Product object as JSON string.
     */
    @POST
    @Path("setProduct/{obj}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String setProduct(@PathParam("obj") String obj) {
        Logger.getLogger(ProductAPI.class.getName()).log(Level.FINE, "setProduct : {0}", obj);
        try {
            JsonReader jsonReader = Json.createReader(new StringReader(obj));
            JsonObject baseJson = jsonReader.readObject();
            ProductInfo p = jsonb.fromJson(baseJson.get("product").toString(), ProductInfo.class);
            return jsonb.toJson(pBean.setProduct(p));
        } catch (JsonParsingException ex) {
            return (jsonb.toJson("JsonParsingException"));
        }
    }

    /**
     * Remove Product.
     * @param id is Product id.  
     * @return is removed Product object.
     */
    @DELETE
    @Path("removeProduct/{id}")
    public String removeProduct(@PathParam("id") Integer id) {
        Logger.getLogger(ProductAPI.class.getName()).log(Level.FINE, "removeProduct : {0}", id);
        ProductInfo p = pBean.removeProduct(id);
        //Send jms message
        msgBean.sendTextMessage(new StringBuilder()
                .append("Product id:")
                .append(id)
                .append("removed ")
                .toString());
        return jsonb.toJson(p);
    }
}
