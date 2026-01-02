package com.example.TaskBoard.util;

import com.example.TaskBoard.entity.User.UserRole;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

@Component
public class TokenUtility {

    private static String SECRET_KEY;

    TokenUtility() throws IOException {
        Properties envVars = new Properties();
        Path envFile = Paths.get(".env");
        try(InputStream inputStream = Files.newInputStream(envFile)){
            envVars.load(inputStream);
        }

        SECRET_KEY = envVars.getProperty("SECRET_KEY");
    }


    public String generateLoginToken(UUID userId, UserRole role){
        //This is equivalent to 1 hour in milliseconds
        final int TOKEN_DURATION = 60*60*1000;

        return Jwts.builder()
                .subject(userId.toString())
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + TOKEN_DURATION))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), Jwts.SIG.HS256)
                .compact();
    }

    public String extractId(String token){
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public UserRole extractUserRole(String token){
        return UserRole.valueOf(
                Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role", String.class));
    }
}
