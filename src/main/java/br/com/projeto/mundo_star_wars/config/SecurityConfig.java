package br.com.projeto.mundo_star_wars.config;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/autenticacao/**").permitAll()
            .requestMatchers("/api/usuarios/cadastro").permitAll()
            .requestMatchers("/swagger-ui/**", "/api-docs/**").permitAll()
            .requestMatchers(GET, "/api/conteudos/**").permitAll()
            .anyRequest().authenticated()
        )
        .exceptionHandling(exceptions -> exceptions
            .authenticationEntryPoint((request, response, authException) -> {
              response.setStatus(401);
              response.setContentType("application/json");
              response.getWriter().write("{\"error\":\"Token de acesso requerido\"}");
            })
            .accessDeniedHandler((request, response, accessDeniedException) -> {
              response.setStatus(403);
              response.setContentType("application/json");
              response.getWriter().write("{\"error\":\"Acesso negado - Apenas ROLE_ADMIN\"}");
            })
        );

    return httpSecurity.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}

