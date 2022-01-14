/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.user.BoatDTO;
import facades.BoatFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("boat")
public class BoatResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final BoatFacade FACADE =  BoatFacade.getBoatFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"You made it into /people!\"}";
    }

    @Path("showall")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String showBoats() {
        List<BoatDTO> bDTO = FACADE.getAllBoats();
        return GSON.toJson(bDTO);
    } 
    
    @Path("show/{userId}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String showOwnerBoats(@PathParam("userId") long userId) {
        List<BoatDTO> boats = FACADE.getBoatsByOwnerId(userId);
        return GSON.toJson(boats);
    }

    @Path("create/{userId}")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public String createBoat(@PathParam("userId") long userId, BoatDTO boatDTO) {
        boatDTO.setOwnerId(userId);
        BoatDTO boat = new BoatDTO(FACADE.createBoat(boatDTO));
        return GSON.toJson(boat);
    }

    @Path("update")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public String updateBoat(BoatDTO boatDTO) {
        BoatDTO boat = new BoatDTO(FACADE.updateBoat(boatDTO));
        return GSON.toJson(boat);
    }
}