/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.user.AuctionDTO;
import entities.Auction;
import errorhandling.API_Exception;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

public class AuctionFacade {
    private static EntityManagerFactory emf;
    private static AuctionFacade instance;

    private AuctionFacade() {
    }

    public static AuctionFacade getOwnerFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new AuctionFacade();
        }
        return instance;
    }

    public List<Auction> getAllAuctions() throws API_Exception {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Auction> query = em.createQuery("SELECT a FROM Auction a", Auction.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new API_Exception(e.getMessage());
        }
    }
}
