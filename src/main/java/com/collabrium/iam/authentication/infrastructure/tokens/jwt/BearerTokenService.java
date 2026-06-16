package com.collabrium.iam.authentication.infrastructure.tokens.jwt;

import com.collabrium.iam.authentication.application.internal.outboundservices.tokens.TokenService;
import com.collabrium.iam.authentication.infrastructure.tokens.jwt.services.TokenServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

/**
 * This interface is a marker interface for the JWT token service.
 * It extends the {@link TokenService} interface.
 * This interface is used to inject the JWT token service in the {@link TokenServiceImpl} class.
 */
public interface BearerTokenService extends TokenService {

  /**
   * This method is responsible for extracting the JWT token from the HTTP request.
   * @param token the HTTP request
   * @return String the JWT token
   */
  String getBearerTokenFrom(HttpServletRequest token);
}