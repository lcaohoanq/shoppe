package com.lcaohoanq.jvservice.config;

import com.lcaohoanq.jvservice.filter.JwtTokenFilter;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
@RequiredArgsConstructor
@EnableWebMvc
public class WebSecurityConfig {

    private final JwtTokenFilter jwtTokenFilter;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final AccessDeniedHandler accessDeniedHandler;

    @Value("${api.prefix}")
    private String apiPrefix;

    private final String[] publicEndpoints = {
            "/graphiql",
            "/graphql",
            "/error",
            "/v3/api-docs/**",
            "/v3/api-docs.yaml",
            "/swagger-ui/**",
            "/swagger-ui.html",
            apiPrefix + "/swagger-ui/**",
            apiPrefix + "/swagger-ui.html",
            apiPrefix + "/api-docs/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints
                        .requestMatchers(
                                String.format("%s/auth/login", apiPrefix),
                                String.format("%s/auth/register", apiPrefix),
                                String.format("%s/roles/**", apiPrefix),
                                String.format("%s/users", apiPrefix),
                                String.format("%s/forgot-password", apiPrefix),
                                String.format("%s/categories/**", apiPrefix),
                                String.format("%s/products/**", apiPrefix),
                                String.format("%s/product-images/**", apiPrefix),
                                String.format("%s/forgot-password/**", apiPrefix),
                                String.format("%s/orders-details/**", apiPrefix),
                                String.format("%s/orders/**", apiPrefix),
                                String.format("%s/assets/**", apiPrefix),
                                String.format("%s/carts/**", apiPrefix),
                                String.format("%s/wallets/**", apiPrefix),
                                String.format("%s/warehouses/**", apiPrefix),
                                String.format("%s/payments/**", apiPrefix),
                                String.format("%s/headquarters/**", apiPrefix))
                        .permitAll()
                        // Swagger UI with basic auth
                        .requestMatchers(publicEndpoints).permitAll()
                        .anyRequest().authenticated())
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler));

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Allow your Vercel domain and localhost for development
        configuration.setAllowedOrigins(Arrays.asList(
                "https://shoppe-git-develop-lcaohoanqs-projects.vercel.app",
                "http://localhost:4000",
                "http://localhost:5173"));

        // Allow common HTTP methods
        configuration.setAllowedMethods(Arrays.asList(
                "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "HEAD"));

        // Allow all headers
        configuration.addAllowedHeader("*");

        // Allow cookies if needed
        configuration.setAllowCredentials(true);

        // How long the browser should cache the CORS response
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
