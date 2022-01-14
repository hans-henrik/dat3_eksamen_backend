package utils;

import dtos.user.BoatDTO;
import entities.Boat;
import entities.Role;
import entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FlushModeType;

public class StartDataSet {

    public static User user,admin,both,owner;
    public static Role userRole,adminRole;
    public static Boat boat;

    public static void main(String[] args) {
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        setupInitialData(emf);
    }

    //Entity managerFactory is deciding whether the data is to test or prod database.
    //Is called both from rest and test cases
    public static void setupInitialData(EntityManagerFactory _emf){
        EntityManager em = _emf.createEntityManager();
        em.setFlushMode(FlushModeType.AUTO);

        try {
            em.getTransaction().begin();
            em.createNativeQuery("TRUNCATE TABLE users").executeUpdate();
            em.createNamedQuery("Role.deleteAllRows").executeUpdate();
            em.createNativeQuery("TRUNCATE TABLE BOAT").executeUpdate();

            owner = new User("owner", "owner", "Bob", "12345678", "bob@bob.dk");
            
            user = new User("user", "testUser", "Gertrud", "87654321", "gert@rud.dk");
            admin = new User("admin", "testAdmin", "David", "12348888", "da@vid.dk");
            both = new User("user_admin", "testBoth", "Karl", "12312323", "ka@rl.dk");

            userRole = new Role("user");
            adminRole = new Role("admin");

            owner.addRole(userRole);
            
            user.addRole(userRole);
            admin.addRole(adminRole);
            both.addRole(userRole);
            both.addRole(adminRole);

            em.persist(owner);
            em.persist(user);
            em.persist(admin);
            em.persist(both);

            em.persist(userRole);
            em.persist(adminRole);

            em.getTransaction().commit();

        } finally {
            em.close();
        }
    }
}
