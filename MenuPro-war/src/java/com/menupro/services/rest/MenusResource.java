/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.menupro.services.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.menupro.business.logic.MenuSessionBeanLocal;
import com.menupro.business.logic.UserSessionBeanLocal;
import com.menupro.dtos.DTOMenu;
import com.menupro.dtos.DTOUser;
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
@Path("menus")
@RequestScoped
public class MenusResource {

    @Context
    private UriInfo context;

    @EJB
    MenuSessionBeanLocal menus;
    
    @EJB
    UserSessionBeanLocal log;
    /**
     * Creates a new instance of MenusResource
     */
    public MenusResource() {
    }

    /**
     * Retrieves representation of an instance of com.menupro.services.rest.MenusResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson() {
        //TODO return proper representation object
        return "test";
    }

    /**
     * PUT method for updating or creating an instance of MenusResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }
    
    @POST
    @Path("/addMenu")
    @Consumes("application/json")
    public String addMenu(String json) {
        try {
            Gson gson = new GsonBuilder().create();
            DTOMenu menu = gson.fromJson(json, DTOMenu.class);
            if (log.isLoggedIn(menu.getToken(), menu.getOwner().getUserName())) {
                menus.addMenu(menu);
                return "ok";
            } else {
                return "You must be signed in to access this feature.";
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    
    @POST
    @Path("/editMenu")
    @Consumes("application/json")
    public String editMenu(String json) {
        try {
            Gson gson = new GsonBuilder().create();
            DTOMenu menu = gson.fromJson(json, DTOMenu.class);
            if (log.isLoggedIn(menu.getToken(), menu.getOwner().getUserName())) {
                menus.editMenu(menu);
                return "ok";
            } else {
                return "You must be signed in to access this feature.";
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    
    @DELETE
    @Path("/deleteMenu")
    public String deleteMenu(@QueryParam("menu") String menu, 
            @QueryParam("owner") String owner, @QueryParam("token") String token) {
        if (log.isLoggedIn(token, owner)) {
            try {
                menus.deleteMenu(menu, owner, token);
                return "ok";
            }
            catch (Exception e) {
                return e.getMessage();
            }
        } else {
            return "You must be signed in to access this feature.";
        }
    }
    
    @GET
    @Path("/getMenu")
    @Consumes("application/json")
    public Response getMenu(@QueryParam("menu") String menu, 
            @QueryParam("owner") String owner, @QueryParam("token") String token){
         if (log.isLoggedIn(token, owner)) {
            try {
                Gson gson = new GsonBuilder().create();
                return Response.accepted(gson.toJson(menus.getMenu(menu, owner))).build();
            }
            catch (Exception e) {
                e.printStackTrace();
                return Response.ok("Menu not found.").build();
            }
        } else {
            return Response.ok("You must be signed in to access this feature.").build();
        }
    }
    
    @GET
    @Path("/getMenusFromUser")
    @Consumes("application/json")
    public Response getMenusFromUser(@QueryParam("owner") String owner, @QueryParam("token") String token){
         if (log.isLoggedIn(token, owner)) {
            try {
                Gson gson = new GsonBuilder().create();
                return Response.accepted(gson.toJson(menus.getMenusFromUser(owner))).build();
            }
            catch (Exception e) {
                return Response.ok("Menus not found.").build();
            }
        } else {
            return Response.ok("You must be signed in to access this feature.").build();
        }
    }
    
    @POST
    @Path("/addPlateToMenu")
    @Consumes("application/x-www-form-urlencoded")
    public String addPlateToMenu(@FormParam("plate") String plate, 
            @FormParam("menu") String menu, @FormParam("owner") String owner, 
            @FormParam("token") String token) {
        if (log.isLoggedIn(token, owner)) {
            try {
                menus.addPlateToMenu(plate, menu, owner);
                return "ok";
            } catch (Exception e) {
                return e.getMessage();
            }
        } else {
            return "You must be signed in to access this feature.";
        }
    }
    
    @POST
    @Path("/shareMenu")
    @Consumes("application/x-www-form-urlencoded")
    public String shareMenu(@FormParam("menu") String menu, 
            @FormParam("owner") String owner, @FormParam("shared") String shared, 
            @FormParam("token") String token) {
        if (log.isLoggedIn(token, owner)) {
            try {
                menus.shareMenu(menu, owner, shared);
                return "ok";
            } catch (Exception e) {
                return e.getMessage();
            }
        } else {
            return "You must be signed in to access this feature.";
        }
    }
    
}
