package org.example.daos;

import jakarta.persistence.EntityManagerFactory;
import org.example.model.User;

public class SecurityDao {

    private static SecurityDao instance;

    private static EntityManagerFactory emf;

    public static SecurityDao getInstance(EntityManagerFactory emf_){
        if(instance == null){
            instance = new SecurityDao();
            emf = emf_;
        }
        return instance;
    }

    public User createUser(String username){
        User user = new User();
        user.setUsername(username);
        try(var em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        }
        return user;
    }


}
