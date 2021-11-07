package io.github.vbartalis.petshop.security.jwt;

import io.jsonwebtoken.security.Keys;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Objects;

//todo might be better if these constants were not inside the code.

/**
 * This class contains constants that are used by jwt tokens.
 */
@Slf4j
@Configuration
@Data
public class JwtConstants {

    @Autowired
    private Environment env;

    private String secret;
    private Key key;
    private long validityInMilliseconds;

    /**
     * This method is used to read and initialize the constants from the property files.
     * It is annotated with @PostConstruct so there is no need to call it manually.
     */
    @PostConstruct
    public void init() {
        this.secret = Objects.requireNonNull(env.getProperty("petshop.jwt.secret"));
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.validityInMilliseconds = Long.parseLong(Objects.requireNonNull(env.getProperty("petshop.jwt.validityinmilliseconds")));
    }


}
