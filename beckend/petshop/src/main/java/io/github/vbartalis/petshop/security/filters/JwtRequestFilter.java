package io.github.vbartalis.petshop.security.filters;

import io.github.vbartalis.petshop.security.jwt.JwtTokenProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
@AllArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
            throws ServletException, IOException {

            var token = jwtTokenProvider.resolveToken(httpServletRequest);

            //token is not null and is not expired
            if (token != null && jwtTokenProvider.validateToken(token)) {

                Authentication authentication = jwtTokenProvider.getAuthentication(token);

                if (authentication != null) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }

            }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
