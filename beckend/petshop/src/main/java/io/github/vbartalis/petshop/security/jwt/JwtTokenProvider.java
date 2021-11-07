package io.github.vbartalis.petshop.security.jwt;

import io.github.vbartalis.petshop.service.impl.MyUserDetailsService;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * This class contains methods that are used to manipulate jwt tokens.
 */
@Slf4j
@Component
public class JwtTokenProvider {

    @Autowired
    JwtConstants jwtConstants;
    @Autowired
    private MyUserDetailsService myUserDetailsService;

    /**
     * This method creates a new jwt token for a specified user.
     *
     * @param username The username of the user.
     * @param roles    The roles of the user.
     * @return Returns a string containing a jwt token for the specified user.
     */
    public String createToken(String username, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", roles);
        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtConstants.getValidityInMilliseconds());
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(jwtConstants.getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * This method gets the authentication of the user specified in the given jwt token.
     *
     * @param token The jwt token.
     * @return Returns the authentication of the specified user.
     */
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.myUserDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), "", userDetails.getAuthorities());
    }

    /**
     * This method gets the username specified in a jwt token.
     *
     * @param token The jwt token.
     * @return Returns the username specified in the jwt token.
     */
    public String getUsername(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(jwtConstants.getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * This method gets the jwt token from a HttpServletRequest.
     *
     * @param request The HttpServletRequest.
     * @return Returns the Jwt token if it exists on the provided request otherwise it returns null.
     */
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /**
     * This method validates the given jwt token.
     *
     * @param token The jwt token.
     * @return Returns true if the jwt token is valid, otherwise false
     */
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts
                    .parserBuilder()
                    .setSigningKey(jwtConstants.getKey())
                    .build()
                    .parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            log.warn("Expired or invalid JWT token");
        } catch (Exception e) {
            throw e;
        }
        return false;
    }
}
