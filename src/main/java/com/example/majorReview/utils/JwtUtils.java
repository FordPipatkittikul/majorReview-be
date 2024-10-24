package com.example.majorReview.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.github.cdimascio.dotenv.Dotenv;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Base64;
//import java.util.logging.Logger;

import com.example.majorReview.models.User;

public class JwtUtils {

//    private static final Logger logger = Logger.getLogger(JwtUtils.class.getName());
    private static final Dotenv dotenv = Dotenv.load();
    private static final String BASE64_SECRET = dotenv.get("BASE64_SECRET");

    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(Base64.getDecoder().decode(BASE64_SECRET));

    public static String generateToken(User user, String duration) {
        long expirationTime = getExpirationTime(duration);  // Convert duration to milliseconds

        return Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("isAdmin", false)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SECRET_KEY)
                .compact();
    }


    public static String getUserIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }



    private static long getExpirationTime(String duration) {
        // Example: 1h = 1 hour, 7d = 7 days, 1m = 1 minute
        if (duration.endsWith("d")) {
            return Long.parseLong(duration.replace("d", "")) * 24 * 60 * 60 * 1000;
        } else if (duration.endsWith("h")) {
            return Long.parseLong(duration.replace("h", "")) * 60 * 60 * 1000;
        } else if (duration.endsWith("m")) {
            return Long.parseLong(duration.replace("m", "")) * 60 * 1000;
        }
        throw new IllegalArgumentException("Invalid duration format");
    }
}
