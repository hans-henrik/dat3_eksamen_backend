/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.user.BoatDTO;
import entities.Boat;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.WebApplicationException;
import java.util.List;


public class BoatFacade {

    private static EntityManagerFactory emf;
    private static BoatFacade instance;

    private BoatFacade() {
    }

    public static BoatFacade getBoatFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new BoatFacade();
        }
        return instance;
    }

    public List<BoatDTO> getAllBoats() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Boat> query = em.createQuery("SELECT b FROM Boat b", Boat.class);
        List<Boat> boats = query.getResultList();
        return BoatDTO.getDTOs(boats);
    }

    public List<BoatDTO> getBoatsByOwnerId(long id) {
        EntityManager em = emf.createEntityManager();
        List<Boat> boats;
        try {
            TypedQuery<Boat> query = em.createQuery("select b from Boat b WHERE b.owner.id = :id", Boat.class);
            query.setParameter("id", id);
            boats = query.getResultList();
        } finally {
            em.close();
        }
        return BoatDTO.getDTOs(boats);
    }

    public Boat createBoat(BoatDTO boatDTO) throws WebApplicationException {
        Boat boat = boatDTO.getEntity();

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(boat);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        return boat;
    }

    public Boat updateBoat(BoatDTO boatDTO) {
        Boat boat =  boatDTO.getEntity();
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.merge(boat);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        return boat;
    }
}