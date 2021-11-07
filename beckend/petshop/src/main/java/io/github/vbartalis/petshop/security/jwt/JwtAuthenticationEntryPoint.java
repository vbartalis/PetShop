package io.github.vbartalis.petshop.security.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This Class extends the {@code AuthenticationEntryPoint} class.
 */
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * This method is a {@code commence} implementation. It is invoked when an {@code AuthenticationException}
     * or an {@code AccessDeniedException} is thrown.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        log.debug("Jwt authentication failed:" + authException);
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Jwt authentication failed");

    }
}
