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

@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        JwtRequestFilter jwtRequestFilter = new JwtRequestFilter(jwtTokenProvider);

        http.csrf().disable(); //Cross Site Request Forgery
        http.cors(); //Cross Origin Requests
        http.headers().frameOptions().sameOrigin(); //Clickjacking //todo do i need it in a rest api?


        http
                .authorizeRequests()
                .antMatchers("/auth/**").permitAll()
//                .antMatchers( "/admin/**").hasAuthority("ROLE_ADMIN") //todo switch to hasAuthority() from permitAll()
                .antMatchers( "/admin", "/admin/**").permitAll()
                .antMatchers("/user", "/user/**").permitAll()
                .antMatchers("/profile", "/profile/**").permitAll()
                .antMatchers("/profileimage", "/profileimage/**").permitAll()
                .antMatchers("/role", "/role/**").permitAll()
                .antMatchers("/post", "/post/**").permitAll()
                .antMatchers("/postimage", "/postimage/**").permitAll()
                .antMatchers("/tag", "/tag/**").permitAll()
                .anyRequest().authenticated();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.exceptionHandling().authenticationEntryPoint(new JwtAuthenticationEntryPoint());
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4210", "http://localhost:4200"));
//        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","DELETE","PATCH","PUT"));
//        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }


}
