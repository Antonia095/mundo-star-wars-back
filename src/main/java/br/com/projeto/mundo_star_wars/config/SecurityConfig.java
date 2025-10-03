package br.com.projeto.mundo_star_wars.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
       httpSecurity.csrf(AbstractHttpConfigurer::disable)
           .authorizeHttpRequests(auth -> auth
               .requestMatchers(
                   "/api/autenticacao/**",
                   "/api/usuarios/cadastro",
                   "/swagger-ui.html",
                   "/v3/api-docs/**")
               .permitAll()
               .anyRequest().authenticated()
           );

       return httpSecurity.build();
    }
}
