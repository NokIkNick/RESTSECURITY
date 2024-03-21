package org.example.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nimbusds.jose.JOSEException;
import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import org.example.daos.SecurityDao;
import org.example.dtos.TokenDTO;
import org.example.dtos.UserDTO;
import org.example.exceptions.ApiException;
import org.example.exceptions.NotAuthorizedException;
import org.example.exceptions.ValidationException;
import org.example.model.User;
import org.example.utils.TokenUtils;

import java.text.ParseException;
import java.util.Set;

public class SecurityController {
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static EntityManagerFactory emf;
    private static SecurityDao securityDao;

    private static TokenUtils tokenUtils = new TokenUtils();
    public SecurityController(EntityManagerFactory emf_){
        emf = emf_;
        securityDao = SecurityDao.getInstance(emf);
    }


    public Handler register() {
        return (ctx) -> {
            ObjectNode returnObject = objectMapper.createObjectNode();
            try{
                UserDTO userInput = ctx.bodyAsClass(UserDTO.class);
                User created = securityDao.createUser(userInput.getUsername(), userInput.getPassword());


                String token = tokenUtils.createToken(new UserDTO(created));
                ctx.status(HttpStatus.CREATED).json(new TokenDTO(token, userInput.getUsername()));
            }catch(EntityExistsException | ApiException e){
                ctx.status(HttpStatus.UNPROCESSABLE_CONTENT);
                ctx.json(returnObject.put("msg", "User already exists"));
            }
        };
    }

    public Handler login(){
        return (ctx) -> {
            ObjectNode returnObject = objectMapper.createObjectNode();
            try{
                UserDTO user = ctx.bodyAsClass(UserDTO.class);
                System.out.println("USER IN LOGIN: "+ user);

                User verifiedUserEntity = securityDao.getVerifiedUser(user.getUsername(), user.getPassword());
                String token = tokenUtils.createToken(new UserDTO(verifiedUserEntity));
                ctx.status(200).json(new TokenDTO(token, user.getUsername()));

            }catch(EntityNotFoundException | ValidationException | ApiException e){
                ctx.status(401);
                System.out.println(e.getMessage());
                ctx.json(returnObject.put("msg", e.getMessage()));
            }
        };
    }


}
