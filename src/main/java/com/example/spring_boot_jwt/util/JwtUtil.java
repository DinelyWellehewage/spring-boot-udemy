package com.example.spring_boot_jwt.util;


import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private static final String secretKey = "Hello";
    public String generateToken(String username){
        JwtBuilder jwt = Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000*60*60*10))
                .signWith(SignatureAlgorithm.HS256,generateJwtSecretKey());

        return jwt.compact();
    }

    public SecretKey generateJwtSecretKey(){
        byte[] keyBytes = secretKey.getBytes();
        byte[] keyBytesPadded = new byte[32];
        System.arraycopy(keyBytes,0,keyBytesPadded,0,Math.min(keyBytes.length,3));
        return Keys.hmacShaKeyFor(keyBytesPadded);
    }
}
