package com.collabrium.iam.authentication.infrastructure.authorization.sfs.pipeline;

import com.collabrium.iam.shared.interfaces.rest.resources.AuthenticationErrorResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

/**
 * Unauthorized Request Handler.
 * <p>
 * This class is responsible for handling unauthorized requests.
 * It is used by the Spring Security framework to handle unauthorized requests.
 * It implements the AuthenticationEntryPoint interface.
 * </p>
 * @see AuthenticationEntryPoint
 */
@Component
public class UnauthorizedRequestHandlerEntryPoint implements AuthenticationEntryPoint {

  private final ObjectMapper objectMapper = new ObjectMapper();

  /**
   * This method is called by the Spring Security framework when an unauthorized request is detected.
   * @param request The request that caused the exception
   * @param response The response that will be sent to the client
   */
  @Override
  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException
  ) throws IOException {

    String message =
        (String) request.getAttribute("jwt_error_message");

    if (message == null) {
      message = "Authentication is required";
    }

    AuthenticationErrorResource error =
        new AuthenticationErrorResource(
            Instant.now().toString(),
            HttpServletResponse.SC_UNAUTHORIZED,
            "Unauthorized",
            "AUTH-001",
            message,
            request.getRequestURI(),
            List.of(
                "Authorization header is required",
                "Expected format: Bearer <token>"
            )
        );

    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentType("application/json");

    objectMapper.writeValue(
        response.getOutputStream(),
        error
    );
  }
}
