package com.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

                DefaultAuthorizationCodeTokenResponseClient accessTokenResponseClient = new DefaultAuthorizationCodeTokenResponseClient();
                http
                                .csrf(csrf -> csrf
                                                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))

                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/dashboard", "/swagger-ui/*").permitAll()
                                                .anyRequest().authenticated())
                                .oauth2Client(oclient -> oclient.authorizationCodeGrant(
                                                authCode -> authCode
                                                                .accessTokenResponseClient(accessTokenResponseClient)))
                                .oauth2Login(login -> login.tokenEndpoint(
                                                tokenEndpoint -> tokenEndpoint
                                                                .accessTokenResponseClient(accessTokenResponseClient))
                                                .defaultSuccessUrl("/swagger-ui/index.html", true))
                                // .oauth2Login(Customizer.withDefaults())
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                                                .invalidSessionUrl("http://localhost:9001/session-invalid")
                                                .maximumSessions(1)
                                                .maxSessionsPreventsLogin(true)
                                                .expiredUrl("/login?expired=true"))
                                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                                .logout(logout -> logout.logoutSuccessUrl("/").permitAll());
                return http.build();
        }

}
