package org.demointernetshop45efs.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtTokenProvider {

    private String jwtSecret = "984hg493gh0439rthr0429uruj2309yh937gc763fe87t3f89723gf";

    private long jwtLifeTime = 600000;

    public String createToken(String username) {

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtLifeTime);

        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }


    public boolean validateToken(String token) {

        try {
            Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

            Jwts
                    .parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
        } catch (JwtException e) {
            throw new InvalidJwtException("Invalid JWT token: " + e.getMessage());
        }

//        } catch (SignatureException e) {
//            // Invalid JWT signature
//            throw new InvalidJwtException("Invalid JWT signature");
//        } catch (MalformedJwtException e){
//            // Invalid JWT token
//            throw new InvalidJwtException("Invalid JWT token");
//        }catch (ExpiredJwtException e){
//            // Expired JWT token
//            throw new InvalidJwtException("Expired JWT token");
//        } catch (UnsupportedJwtException e){
//            // Unsupported JWT token
//            throw new InvalidJwtException("Unsupported JWT token");
//        } catch (IllegalArgumentException e){
//            // JWT claims is empty
//            throw new InvalidJwtException("JWT claims is empty");
//        }

        return true;
    }

    public String getUsernameFromJwt(String token){
        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        // вытаскиваем из claims (из части payload нашего JWT)
        // из них берем содержимое поля subject

        return claims.getSubject();
    }
}
