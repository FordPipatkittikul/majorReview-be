package com.example.majorReview.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;

public class JwtUtils {

    //private static final String SECRET_KEY = System.getenv("JWT_SECRET_KEY");

    public static String generateToken(Long userId, String duration) {
        long expirationTime = getExpirationTime(duration);  // Convert duration to milliseconds

        return Jwts.builder()
                .setSubject(userId.toString())
                .claim("isAdmin", false)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS512, "7v3++pmJzlQ0r+FJXFdHGjJxXq7BHHOWp9zP7xUKseA=")
                .compact();
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
