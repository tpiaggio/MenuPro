/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.menupro.services.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.menupro.business.logic.PlateSessionBeanLocal;
import com.menupro.dtos.DTOPlate;
import com.menupro.dtos.DTOUser;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.RequestScoped;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
@Path("plates")
@RequestScoped
public class PlatesResource {

    @Context
    private UriInfo context;

    @EJB
    PlateSessionBeanLocal plates;
    /**
     * Creates a new instance of PlatesResource
     */
    public PlatesResource() {
    }

    /**
     * Retrieves representation of an instance of com.menupro.services.rest.PlatesResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson() {
        /*try{
         plates.addPlate(new DTOPlate(Long.MIN_VALUE, "foodPrueba", 2, "catPrueba"));
        } catch(Exception e){
            e.printStackTrace();
        }*/
        return "test";
    }

    /**
     * PUT method for updating or creating an instance of PlatesResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }
    
    /**
     * Adds a new plate to the system
     * @param plate the DTOPlate that is going to be added
     * @return confirmation message
     */
    @POST
    @Path("/addPlate")
    @Consumes("application/json")
    public String addPlate(String json) {
        try {
            Gson gson = new GsonBuilder().create();
            DTOPlate plate = gson.fromJson(json, DTOPlate.class);
            plates.addPlate(plate);
            return "ok";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    
    
    /**
     * Edits the new plate
     * @param plate the DTOPlate that is going to be edited
     * @return confirmation message
     */
    @POST
    @Path("/editPlate")
    @Consumes("application/json")
    public String editPlate(String json) {
        try {
            Gson gson = new GsonBuilder().create();
            DTOPlate plate = gson.fromJson(json, DTOPlate.class);
            plates.editPlate(plate);
            return "ok";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    
    /**
     * Deletes the active user.
     * @param plateName The plate to be deleted
     * @return confirmation message
     */
    @DELETE
    @Path("/deletePlate")
    public String deletePlate(@QueryParam("plate") String plateName) {
            try {
                plates.deletePlate(plateName);
                return "ok";
            } catch (Exception e) {
                return e.getMessage();
            }
    }
    
    @GET
    @Path("/getPlate")
    @Consumes("application/json")
    public Response getPlate(@QueryParam("plate") String plate) {
        try {
            Gson gson = new GsonBuilder().create();
            DTOPlate dPlate = plates.getPlate(plate);
            return Response.accepted(gson.toJson(dPlate)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.ok("Plate not found.").build();
        }
    }
    
    @GET
    @Path("/getPlates")
    @Consumes("application/json")
    public Response getPlates() {
        try {
            Gson gson = new GsonBuilder().create();
            List<DTOPlate> dPlates = plates.getPlates();
            return Response.accepted(gson.toJson(dPlates)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.ok("No plates added to system").build();
        }
    }
    
    @GET
    @Path("/getPlatesFromCategory")
    @Consumes("application/json")
    public Response getPlatesFromCategory(@QueryParam("category") String category) {
        try {
            Gson gson = new GsonBuilder().create();
            List<DTOPlate> dPlates = plates.getPlatesFromCategory(category);
            return Response.accepted(gson.toJson(dPlates)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.ok("No plates added to system").build();
        }
    }
}
