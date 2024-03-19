package org.example.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nimbusds.jose.JOSEException;
import io.javalin.http.Handler;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManagerFactory;
import org.example.daos.SecurityDao;
import org.example.dtos.UserDTO;
import org.example.exceptions.NotAuthorizedException;
import org.example.model.User;

import java.text.ParseException;
import java.util.Set;

public class SecurityController implements ISecurity{
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static EntityManagerFactory emf;
    private static SecurityDao securityDao = SecurityDao.getInstance(emf);

    public SecurityController(EntityManagerFactory emf_){
        emf = emf_;
    }

    @Override
    public Handler register() {
        return (ctx) -> {
            ObjectNode returnObject = objectMapper.createObjectNode();
            try{
                UserDTO userInput = ctx.bodyAsClass(UserDTO.class);
                User created = securityDao.createUser(userInput.getUsername());

                String token = createToken(new UserDTO(created));
            }catch(EntityExistsException e){

            }
        };
    }

    @Override
    public Handler login() {
        return null;
    }

    @Override
    public String createToken(UserDTO user) {
        return null;
    }

    @Override
    public boolean authorize(UserDTO user, Set<String> allowedRoles) {
        return false;
    }

    @Override
    public Handler authenticate() {
        return null;
    }

    @Override
    public UserDTO verifyToken(String token) {
        return null;
    }

    @Override
    public boolean tokenIsValid(String token, String secret) throws ParseException, JOSEException, NotAuthorizedException {
        return false;
    }

    @Override
    public boolean tokenNotExpired(String token) throws ParseException, NotAuthorizedException {
        return false;
    }

    @Override
    public UserDTO getUserWithRolesFromToken(String token) throws ParseException {
        return null;
    }

    @Override
    public int timeToExpire(String token) throws ParseException, NotAuthorizedException {
        return 0;
    }
}
