package io.github.vbartalis.petshop.security.jwt;

import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.context.annotation.Configuration;

import java.security.Key;

@Configuration
@Data
public class JwtConstants {

    private String secret = "TP21JJRfC+l26E7j4ZR31uvYabegKT4cfwosHoIKR64=";

    private Key key = Keys.hmacShaKeyFor(secret.getBytes());

    private long validityInMilliseconds = 60 * 60 * 1000 * 24 * 7;  //7d
}
