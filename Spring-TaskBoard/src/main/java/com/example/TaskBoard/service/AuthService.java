package com.example.TaskBoard.service;

import com.example.TaskBoard.entity.User;
import com.example.TaskBoard.util.TokenUtility;
import io.jsonwebtoken.JwtException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final TokenUtility tokenUtility;

    public AuthService(TokenUtility tokenUtility){
        this.tokenUtility = tokenUtility;
    }

    public User.UserRole getAuthLevel(String headerData){
        if(headerData == null){
            return null;
        }
        try{
            String token = headerData.split(" ")[1];
            System.out.println(token);
            return tokenUtility.extractUserRole(token);
        } catch (JwtException e){
            e.printStackTrace();
            return null;
        }
    }

}
