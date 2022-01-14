package facades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dtos.user.BoatDTO;
import dtos.user.UserDTO;
import entities.Boat;
import entities.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;
import utils.StartDataSet;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FacadeTest {

    private static EntityManagerFactory emf;
    private static UserFacade userFacade;
    private static BoatFacade boatFacade;


    public FacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        userFacade = UserFacade.getUserFacade(emf);
        boatFacade = BoatFacade.getBoatFacade(emf);
    }


    // Setup the DataBase in a known state BEFORE EACH TEST
    @BeforeEach
    public void setUp() {
        StartDataSet.setupInitialData(emf);
    }

    @Test
    public void testVerifiedUser(){
        User user = emf.createEntityManager().find(User.class, 4L);

        assertTrue(user.verifyPassword("owner"), "Expected password to be 'owner'");
    }

    @Test
    public void testCreateBoat() {
        Boat boat = boatFacade.createBoat(new BoatDTO(null, 4L, "Boaty McBoatFace", "Brand", "Make", 1996, "Image"));

        assertNotNull(boat);
        assertEquals(boat.getId(), 1);
        assertEquals(boat.getOwnerId(), 4L);
        assertEquals(boat.getName(), "Boaty McBoatFace");
        assertEquals(boat.getBrand(), "Brand");
        assertEquals(boat.getMake(), "Make");
        assertEquals(boat.getYear(), 1996);
        assertEquals(boat.getImg(), "Image");
    }

    @Test
    public void testUpdateBoat() {
        BoatDTO boatDto = new BoatDTO(null, 4L, "Boaty McBoatFace", "Brand", "Make", 1996, "Image");
        Boat boat = boatFacade.createBoat(boatDto);

        boatDto.setId(boat.getId());

        boatDto.setName("Bobby McBobFace");
        boat = boatFacade.updateBoat(boatDto);

        assertEquals(boat.getId(), 1); // Sikrer os at vi ikke har lavet en ny Boat.
        assertEquals(boat.getName(), "Bobby McBobFace");
    }

    @Test
    public void testGetBoatsByOwner() {
        BoatDTO boatDto = new BoatDTO(null, 4L, "Boaty McBoatFace", "Brand", "Make", 1996, "Image");

        for (int i = 0; i < 5; i++) {
            boatFacade.createBoat(boatDto);
        }

        boatDto.setOwnerId(1L);
        boatFacade.createBoat(boatDto);

        List<BoatDTO> boats = boatFacade.getBoatsByOwnerId(4L);
        List<BoatDTO> allBoats = boatFacade.getAllBoats();

        assertEquals(boats.size(), 5);
        assertNotEquals(boats.size(), allBoats.size());
    }

    @Test
    public void testGetAllBoats() {
        BoatDTO boatDto = new BoatDTO(null, 4L, "Boaty McBoatFace", "Brand", "Make", 1996, "Image");

        for (int i = 0; i < 5; i++) {
            boatFacade.createBoat(boatDto);
        }

        List<BoatDTO> boats = boatFacade.getAllBoats();

        assertEquals(boats.size(), 5);
    }

}
