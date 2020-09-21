/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catneye.servlet;

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

    JsonbConfig jsonconfig = new JsonbConfig().setProperty("jsonb.to.json.encoding", "UTF-8");
    Jsonb jsonb = JsonbBuilder.create(jsonconfig);

    public ProductAPI() {
        try {
            String lookupName = Constants.SERVICE_BEAN_NAME;
            pBean = (ProductBeanRemote) InitialContext.doLookup(lookupName);
        } catch (NamingException ex) {
            Logger.getLogger(ProductAPI.class.getName()).log(Level.SEVERE, "ProductAPI : {0}", ex);
        }
    }

    @GET
    @Path("getAllProducts")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllProducts() {
        Logger.getLogger(ProductAPI.class.getName()).log(Level.INFO, "getAllProducts : {0}", 0);
        return jsonb.toJson(pBean.getProducts());
    }

    @GET
    @Path("getProduct/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getProduct(@PathParam("id") Integer id) {
        Logger.getLogger(ProductAPI.class.getName()).log(Level.INFO, "getProduct : {0}", id);
        ProductInfo p = pBean.getProduct(id);
        return jsonb.toJson(p != null ? p : new Product());
    }

    @GET
    @Path("getProducts/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getProducts(@PathParam("name") String name) {
        Logger.getLogger(ProductAPI.class.getName()).log(Level.INFO, "getProducts : {0}", name);
        List<ProductInfo> ps = pBean.getProducts(name);
        return jsonb.toJson(ps);
    }

    //http://127.0.0.1:8080/gradle-war/rest/ProductAPI/setProduct/{"product":{"adddate": "2020-09-10T07:05:52.234Z[UTC]","id": 1,"name": "p11","price": 12.12}}
    @POST
    @Path("setProduct/{obj}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String setProduct(@PathParam("obj") String obj) {
        Logger.getLogger(ProductAPI.class.getName()).log(Level.INFO, "setProduct : {0}", obj);
        try {
            JsonReader jsonReader = Json.createReader(new StringReader(obj));
            JsonObject baseJson = jsonReader.readObject();
            ProductInfo p = jsonb.fromJson(baseJson.get("product").toString(), ProductInfo.class);
            return jsonb.toJson(pBean.setProduct(p));
        } catch (JsonParsingException ex) {
            return (jsonb.toJson("JsonParsingException"));
        }
    }

    @DELETE
    @Path("removeProduct/{id}")
    public String removeProduct(@PathParam("id") Integer id) {
        Logger.getLogger(ProductAPI.class.getName()).log(Level.INFO, "removeProduct : {0}", id);
        ProductInfo p = pBean.removeProduct(id);
        return jsonb.toJson(p);
    }
}
