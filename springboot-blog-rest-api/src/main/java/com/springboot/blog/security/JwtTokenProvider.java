package com.springboot.blog.security;

import com.springboot.blog.exception.BlogApiException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;


import java.security.Key;
import java.util.Date;

import static io.jsonwebtoken.Jwts.*;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${aap.jwt-expiration-milliseconds}")
    private long jwtExpirationDate;


    // generate Jwt token
    public String generateToken(Authentication authentication){

        String username = authentication.getName();

        Date currentDate = new Date();

        Date expireDate = new Date(currentDate.getTime()+jwtExpirationDate);

         String token = builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(key())
                .compact();

        return token;

    }

    private Key key(){

        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(jwtSecret)
        );
    }

    // get usernaem from jwt token

    public String getUsername(String token) {

        Claims claims = Jwts.parser().
                setSigningKey(key()).
                build().parseClaimsJws(token).getBody();

        String username = claims.getSubject();

        return username;

    }

     // validate jwt token

        public boolean validateToken(String token){

        try{
            Jwts.parser().setSigningKey(key())
                    .build()
                    .parse(token);

            return true;


        } catch (MalformedJwtException ex){
            throw  new BlogApiException(HttpStatus.BAD_REQUEST,"Invalid Jwt Token");
        }
        catch (ExpiredJwtException ex){
            throw  new BlogApiException(HttpStatus.BAD_REQUEST,"Expired Jwt Token");
        }
        catch (UnsupportedJwtException ex){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Unsupported Jwt Token");
        }
        catch(IllegalArgumentException ex){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Jwt Claims String is empty");
        }




    }

}
