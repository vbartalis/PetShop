package io.github.vbartalis.petshop.security.jwt;

import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.security.Key;

//todo might be better if these constants were not inside the code.

/**
 * This class contains constants that are used by jwt tokens.
 */
@Configuration
@Data
public class JwtConstants {

    @Autowired
    private Environment env;

    private String secret = "TP21JJRfC+l26E7j4ZR31uvYabegKT4cfwosHoIKR64=";
//    private String secret = env.getProperty("PetShop.JWT.Secret");

    private Key key = Keys.hmacShaKeyFor(secret.getBytes());

    private long validityInMilliseconds = 60 * 60 * 1000 * 24 * 7;  //7d
//    private long validityInMilliseconds = env.getProperty("PetShop.JWT.ValidityInMilliseconds");
}
