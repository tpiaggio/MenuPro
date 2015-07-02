/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.menupro.services.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.menupro.business.logic.OrderSessionBeanLocal;
import com.menupro.business.logic.UserSessionBeanLocal;
import com.menupro.dtos.DTOMenu;
import com.menupro.dtos.DTOOrder;
import javax.ejb.EJB;
import javax.faces.bean.RequestScoped;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author Pepe
 */
@Path("orders")
@RequestScoped
public class OrdersResource {

    @Context
    private UriInfo context;

    @EJB
    OrderSessionBeanLocal orders;
    
    @EJB
    UserSessionBeanLocal log;
    /**
     * Creates a new instance of OrdersResource
     */
    public OrdersResource() {
    }

    /**
     * Retrieves representation of an instance of com.menupro.services.rest.OrdersResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson() {
        //TODO return proper representation object
        return "test";
    }

    /**
     * PUT method for updating or creating an instance of OrdersResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }
    
    @POST
    @Path("/addOrder")
    @Consumes("application/json")
    public String addOrder(String json) {
        try {
            Gson gson = new GsonBuilder().create();
            DTOOrder order = gson.fromJson(json, DTOOrder.class);
            if (log.isLoggedIn(order.getToken(), order.getBuyer().getUserName())) {
                orders.addOrder(order);
                return "ok";
            } else {
                return "You must be signed in to access this feature.";
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    
    @POST
    @Path("/editOrder")
    @Consumes("application/json")
    public String editOrder(String json) {
        try {
            Gson gson = new GsonBuilder().create();
            DTOOrder order = gson.fromJson(json, DTOOrder.class);
            if (log.isLoggedIn(order.getToken(), order.getBuyer().getUserName())) {
                orders.editOrder(order);
                return "ok";
            } else {
                return "You must be signed in to access this feature.";
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    
    @DELETE
    @Path("/deleteOrder")
    public String deleteOrder(@QueryParam("code") String code, 
            @QueryParam("buyer") String buyer, @QueryParam("token") String token) {
        if (log.isLoggedIn(token, buyer)) {
            try {
                Long orderCode = Long.parseLong(code);
                orders.deleteOrder(orderCode, buyer);
                return "ok";
            } 
            catch (NumberFormatException e) {
                return "The code must be a number.";
            }
            catch (Exception e) {
                return e.getMessage();
            }
        } else {
            return "You must be signed in to access this feature.";
        }
    }
    
    @GET
    @Path("/getOrder")
    @Consumes("application/json")
    public Response getOrder(@QueryParam("order") String code, 
            @QueryParam("buyer") String buyer, @QueryParam("token") String token){
         if (log.isLoggedIn(token, buyer)) {
            try {
                Gson gson = new GsonBuilder().create();
                Long orderCode = Long.parseLong(code);
                return Response.accepted(gson.toJson(orders.getOrder(orderCode,buyer))).build();
            }
            catch (Exception e) {
                return Response.ok("Order not found.").build();
            }
        } else {
            return Response.ok("You must be signed in to access this feature.").build();
        }
    }
    
    @GET
    @Path("/getOrdersFromUser")
    @Consumes("application/json")
    public Response getOrdersFromUser(@QueryParam("buyer") String buyer, @QueryParam("token") String token){
        System.out.println(token + " " + buyer);
         if (log.isLoggedIn(token, buyer)) {
            try {
                Gson gson = new GsonBuilder().create();
                return Response.accepted(gson.toJson(orders.getOrdersFromUser(buyer))).build();
            }
            catch (Exception e) {
                return Response.ok("Orders not found.").build();
            }
        } else {
            return Response.ok("You must be signed in to access this feature.").build();
        }
    }
    
    @POST
    @Path("/addMenuToOrder")
    @Consumes("application/x-www-form-urlencoded")
    public String addMenuToOrder(@FormParam("code") String code, 
            @FormParam("menu") String menu, @FormParam("buyer") String buyer, 
            @FormParam("token") String token) {
        if (log.isLoggedIn(token, buyer)) {
            try {
                Long orderCode = Long.parseLong(code);
                orders.addMenuToOrder(menu, buyer, orderCode);
                return "ok";
            } catch (Exception e) {
                return e.getMessage();
            }
        } else {
            return "You must be signed in to access this feature.";
        }
    }
    
    @POST
    @Path("/shareOrder")
    @Consumes("application/x-www-form-urlencoded")
    public String shareOrder(@FormParam("code") String code, 
            @FormParam("buyer") String buyer, @FormParam("shared") String shared, 
            @FormParam("token") String token) {
        if (log.isLoggedIn(token, buyer)) {
            try {
                Long orderCode = Long.parseLong(code);
                orders.shareOrder(shared, orderCode, buyer);
                return "ok";
            } catch (Exception e) {
                return e.getMessage();
            }
        } else {
            return "You must be signed in to access this feature.";
        }
    }
    
}
