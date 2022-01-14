package facades;

import dtos.user.UserDTO;
import entities.Role;
import entities.User;
import errorhandling.API_Exception;
import errorhandling.NotFoundException;
import security.errorhandling.AuthenticationException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

public class UserFacade {

    private static EntityManagerFactory emf;
    private static UserFacade instance;

    private UserFacade() {
    }

    public static UserFacade getUserFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new UserFacade();
        }
        return instance;
    }

    //metode bliver bruger til at få bruger hvis username & password er korrekt,logger ind
    public User getVerifiedUser(String username, String password) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        User user;
        try {
            TypedQuery<User> query = em.createQuery("select u from User u where u.userName = :userName", User.class);
            query.setParameter("userName", username);
            user = query.getSingleResult();
            if (user == null || !user.verifyPassword(password)) {
                throw new AuthenticationException("Invalid user name or password");
            }
        } finally {
            em.close();
        }
        return user;
    }

    public UserDTO createUser(UserDTO userDTO) throws Exception {
        User user = userDTO.getEntity();
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            //Makes sure roles is managed objects and checks that it exist
            for (int i = 0; i < user.getRoleList().size(); i++) {
                Role role = user.getRoleList().get(i);
                role = em.find(Role.class, role.getRoleName()); // find finder @Id i Role klassen og tjekker med role.getRollName er det samme som det der findes i databasen
                if (role == null) {
                    throw new NotFoundException("Role doesn't exist");
                }
            }
            em.persist(user);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new UserDTO(user);
    }

}
