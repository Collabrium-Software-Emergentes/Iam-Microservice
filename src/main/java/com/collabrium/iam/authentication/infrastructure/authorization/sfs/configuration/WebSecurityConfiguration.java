package com.collabrium.iam.authentication.infrastructure.authorization.sfs.configuration;

import com.collabrium.iam.authentication.infrastructure.authorization.sfs.pipeline.BearerAuthorizationRequestFilter;
import com.collabrium.iam.authentication.infrastructure.hashing.bcrypt.BcryptHashingService;
import com.collabrium.iam.authentication.infrastructure.tokens.jwt.BearerTokenService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

/**
 * Spring Security configuration for stateless JWT-based authentication.
 */
@Configuration
@EnableMethodSecurity
public class WebSecurityConfiguration {

  private final UserDetailsService userDetailsService;
  private final BearerTokenService tokenService;
  private final BcryptHashingService hashingService;
  private final AuthenticationEntryPoint unauthorizedRequestHandler;

  /**
   * Creates a new {@code WebSecurityConfiguration}.
   *
   * @param userDetailsService user details service
   * @param tokenService bearer token service
   * @param hashingService password hashing service
   * @param authenticationEntryPoint entry point for unauthorized requests
   */
  public WebSecurityConfiguration(
      @Qualifier("defaultUserDetailsService") UserDetailsService userDetailsService,
      BearerTokenService tokenService,
      BcryptHashingService hashingService,
      AuthenticationEntryPoint authenticationEntryPoint) {

    this.userDetailsService = userDetailsService;
    this.tokenService = tokenService;
    this.hashingService = hashingService;
    this.unauthorizedRequestHandler = authenticationEntryPoint;
  }

  /**
   * Creates the bearer authorization request filter.
   *
   * @return authorization request filter
   */
  @Bean
  public BearerAuthorizationRequestFilter authorizationRequestFilter() {
    return new BearerAuthorizationRequestFilter(tokenService, userDetailsService);
  }

  /**
   * Provides the authentication manager.
   *
   * @param authenticationConfiguration authentication configuration
   * @return authentication manager
   * @throws Exception if authentication manager cannot be created
   */
  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  /**
   * Configures the DAO authentication provider.
   *
   * @return authentication provider
   */
  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    var authenticationProvider = new DaoAuthenticationProvider(userDetailsService);
    authenticationProvider.setPasswordEncoder(hashingService);
    return authenticationProvider;
  }

  /**
   * Exposes the password encoder.
   *
   * @return password encoder
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return hashingService;
  }

  /**
   * Configures the security filter chain.
   *
   * @param http HTTP security configuration
   * @return security filter chain
   * @throws Exception if configuration fails
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .cors(corsConfigurer ->
            corsConfigurer.configurationSource(request -> {
              var cors = new CorsConfiguration();
              cors.setAllowedOrigins(List.of("*"));
              cors.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE"));
              cors.setAllowedHeaders(List.of("*"));
              return cors;
            }))
        .csrf(AbstractHttpConfigurer::disable)
        // Safe: CSRF protection is disabled because the API is stateless and uses JWT
        // authentication via Authorization header instead of session cookies.
        .exceptionHandling(exceptionHandling ->
            exceptionHandling.authenticationEntryPoint(unauthorizedRequestHandler))
        .sessionManagement(sessionManagement ->
            sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(authorizeRequests ->
            authorizeRequests
                .requestMatchers(
                    "/api/v1/authentication/**",
                    "/v3/api-docs/**",
                    "/swagger-ui.html",
                    "/swagger-ui/**",
                    "/swagger-resources/**",
                    "/webjars/**",
                    "/actuator/**")
                .permitAll()
                .anyRequest()
                .authenticated());

    http.authenticationProvider(authenticationProvider());
    http.addFilterBefore(
        authorizationRequestFilter(),
        UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}