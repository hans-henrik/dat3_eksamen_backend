/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.user.AuctionDTO;
import errorhandling.API_Exception;
import facades.AuctionFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import utils.EMF_Creator;

@Path("auction")
public class AuctionResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final AuctionFacade FACADE =  AuctionFacade.getOwnerFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"You made it into /people!\"}";
    }

    @Path("show")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String showOwners() throws API_Exception {
        List<AuctionDTO> adto = AuctionDTO.getDTOs(FACADE.getAllAuctions());
        return GSON.toJson(adto);
    }
}