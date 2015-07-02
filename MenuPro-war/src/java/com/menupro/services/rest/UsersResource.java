/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.menupro.services.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.menupro.business.logic.UserSessionBeanLocal;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author Pepe
 */
@Path("users")
@RequestScoped
public class UsersResource{

    @Context
    private UriInfo context;
    
    //@Inject
    @EJB
    UserSessionBeanLocal users;

    /**
     * Creates a new instance of UsersResource
     */
    public UsersResource() {

    }

    @GET
    @Produces("application/json")
    public String getJson() {
        //TODO return proper representation object
        return "test";
    }

    /**
     * Adds a new user to the system, and then signs in with that account
     * @param json the DTOUser that is going to be added
     * @return token returned by signing in with the new account, error otherwise
     */
    @POST
    @Path("/addUser")
    @Consumes("application/json")
    public String addUser(String json) {
        try {
            Gson gson = new GsonBuilder().create();
            DTOUser user = gson.fromJson(json, DTOUser.class);
            users.addUser(user);
            return users.signIn(user.getUserName(), user.getPassword());
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    
    /**
     * Edits the active user
     * The username cannot be changed, as it is used to identify the user
     * @param json The user to be edited
     * @return confirmation message
     */
    @POST
    @Path("/editUser")
    @Consumes("application/json")
    public String editUser(String json) {
       Gson gson = new GsonBuilder().create();
       DTOUser user = gson.fromJson(json, DTOUser.class);
       if (users.isLoggedIn(user.getToken(), user.getUserName())) {
            try {
                users.editUser(user);
                return "ok";
            } catch (Exception e) {
                return e.getMessage();
            }
        } else {
            return "You must be signed in to access this feature.";
        }
    }
    
    /**
     * Deletes the active user.
     * @param userName The user to be deleted
     * @param token token of the user session, if token doesn't exists the user has to sign in again.
     * The user can only delete itself
     * @return confirmation message
     */
    @DELETE
    @Path("/deleteUser")
    public String deleteUser(@QueryParam("user") String userName, @QueryParam("token") String token) {
        if (users.isLoggedIn(token, userName)) {
            try {
                users.deleteUser(userName);
                return "ok";
            } catch (Exception e) {
                return e.getMessage();
            }
        } else {
            return "You must be signed in to access this feature.";
        }
    }
    
    /**
     * Adds a new contact to the user
     * @param user the user that recieves a new contact
     * @param contact the user going to be added as a contact
     * @param token token of the user session, if token doesn't exists the user has to sign in again.
     * @return confirmation message
     */
    @POST
    @Path("/addContact")
    @Consumes("application/x-www-form-urlencoded")
    public String addContact(@FormParam("user") String user, 
            @FormParam("contact") String contact, @FormParam("token") String token) {
        if (users.isLoggedIn(token, user)) {
            try {
                users.addContact(user, contact);
                return "ok";
            } catch (Exception e) {
                return e.getMessage();
            }
        } else {
            return "You must be signed in to access this feature.";
        }
    }
    
    /**
     * Gets the user with all its data
     * @param userName The user to be retrieved
     * @return DTOUser with the data of the user. If user doesn't exists, 
     * returns an empty DTOUser
     */
    @GET
    @Path("/getUser")
    @Consumes("application/json")
    public Response getUser(@QueryParam("user") String userName) {
        try {
            Gson gson = new GsonBuilder().create();
            return Response.accepted(gson.toJson(users.getUser(userName))).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.ok("User not found.").build();
        }
    }
    
    @GET
    @Path("/getUsers")
    @Consumes("application/json")
    public Response getUsers() {
        try {
            Gson gson = new GsonBuilder().create();
            return Response.accepted(gson.toJson(users.getUsers())).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.ok("User not found.").build();
        }
    }
    
    /**
     * Sign in the user
     * @param password password of the user trying to sign in
     * @param user username of user trying to sign in
     * @return if success, return token. Otherwise returns error message
     */
    
    @POST
    @Path("/signin")
    @Consumes("application/x-www-form-urlencoded")
    public String signin(@FormParam("password") String password, @FormParam("user") String user) {
        System.out.println(password+" y "+user);
        try {
            String token = users.signIn(user, password);
            if (token.equals("")) {
                token = "The username and password you entered don't match.";
            }
            return token;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
    
    /**
     * Sign out the user
     * @param token token of the user session
     * @return if success, return ok. Otherwise returns error message
     */
    @DELETE
    @Path("/{token}/signout")
    public String signout(@PathParam("token") String token) {
        try {
            users.signOut(token);
            return "ok";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    
}
