package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.user.BoatDTO;
import facades.BoatFacade;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;
import utils.StartDataSet;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

//Disabled
public class LoginEndpointTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;
    private static BoatFacade boatFacade;
    
    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        
        httpServer.shutdownNow();
    }

    @BeforeEach
    public void setUp() {
        StartDataSet.setupInitialData(emf);
        boatFacade = BoatFacade.getBoatFacade(emf);
    }

    @Test
    public void testCreateBoat() {
        Gson gson = new GsonBuilder().create();
        BoatDTO boatDTO = new BoatDTO(null, null, "Boaty McBoatFace", "Brand", "Make", 1996, "Image");
        String json = gson.toJson(boatDTO);

        given()
                .contentType("application/json")
                .body(json)
                .when().post("/boat/create/4")
                .then()
                .body("id", equalTo(1))
                .body("ownerId", equalTo(4));
    }

    @Test
    public void testUpdateBoat() {
        Gson gson = new GsonBuilder().create();
        BoatDTO boatDTO = new BoatDTO(null, 4L, "Boaty McBoatFace", "Brand", "Make", 1996, "Image");
        boatDTO = new BoatDTO(boatFacade.createBoat(boatDTO));

        boatDTO.setName("Bobby McBobFace");
        String json = gson.toJson(boatDTO);

        given()
                .contentType("application/json")
                .body(json)
                .when().post("/boat/update")
                .then()
                .body("id", equalTo(1))
                .body("name", equalTo("Bobby McBobFace"));
    }

    @Test
    public void testGetBoatsByOwner() {
        BoatDTO boatDto = new BoatDTO(null, 4L, "Boaty McBoatFace", "Brand", "Make", 1996, "Image");

        for (int i = 0; i < 5; i++) {
            boatFacade.createBoat(boatDto);
        }

        boatDto.setOwnerId(1L);
        boatFacade.createBoat(boatDto);

        given()
                .contentType("application/json")
                .when().get("/boat/show/4")
                .then()
                .body("size()", equalTo(5))
                .body("size()", not(boatFacade.getAllBoats().size()));
    }

    @Test
    public void testGetAllBoats() {
        BoatDTO boatDto = new BoatDTO(null, 4L, "Boaty McBoatFace", "Brand", "Make", 1996, "Image");

        for (int i = 0; i < 5; i++) {
            boatFacade.createBoat(boatDto);
        }

        given()
                .contentType("application/json")
                .when().get("/boat/showall")
                .then()
                .body("size()", equalTo(5));
    }

}
