package io.github.vbartalis.petshop.security;

import io.github.vbartalis.petshop.security.filters.JwtRequestFilter;
import io.github.vbartalis.petshop.security.jwt.JwtAuthenticationEntryPoint;
import io.github.vbartalis.petshop.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * Configuration class for security all across the application.
 */
@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    /**
     * This method is used to set up basic configuration and to modify it.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        JwtRequestFilter jwtRequestFilter = new JwtRequestFilter(jwtTokenProvider);

        //Cross Site Request Forgery
        http.csrf().disable();
        //Cross Origin Requests
        http.cors();
        //Clickjacking - not needed for an api but doesn't hurt.
        http.headers().frameOptions().sameOrigin();
        http.authorizeRequests()
                // For the following patterns, the required authorization will be set on the method level,
                // otherwise it will default to the below specified ones.
                .antMatchers("/auth/**").permitAll()
                .antMatchers("/user", "/user/**").permitAll()
                .antMatchers("/profile", "/profile/**").permitAll()
                .antMatchers("/profileimage", "/profileimage/**").permitAll()
                .antMatchers("/role", "/role/**").permitAll()
                .antMatchers("/post", "/post/**").permitAll()
                .antMatchers("/postimage", "/postimage/**").permitAll()
                .antMatchers("/tag", "/tag/**").permitAll()
                //this is where the swagger can be accessed
                .antMatchers("/admin", "/admin/**").permitAll()
                .anyRequest().authenticated();
        //Stateless session policy, jwt based authentication is stateless
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //configuring how to handle exceptions
        http.exceptionHandling().authenticationEntryPoint(new JwtAuthenticationEntryPoint());
        //add the jwtRequestFilter before the position of the UsernamePasswordAuthenticationFilter class
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * This method is used to set up the cors configuration.
     *
     * @return Returns the cors configuration.
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4210", "http://localhost:4200"));
//        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PATCH", "PUT"));
//        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }


}
