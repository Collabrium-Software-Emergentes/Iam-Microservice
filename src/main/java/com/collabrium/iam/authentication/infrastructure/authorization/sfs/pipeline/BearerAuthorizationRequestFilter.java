package com.collabrium.iam.authentication.infrastructure.authorization.sfs.pipeline;

import com.collabrium.iam.authentication.infrastructure.authorization.sfs.model.UsernamePasswordAuthenticationTokenBuilder;
import com.collabrium.iam.authentication.infrastructure.tokens.jwt.BearerTokenService;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * Bearer Authorization Request Filter.
 * <p>
 * This class is responsible for filtering requests and setting the user authentication.
 * It extends the OncePerRequestFilter class.
 * </p>
 * @see OncePerRequestFilter
 */
public class BearerAuthorizationRequestFilter extends OncePerRequestFilter {

  private final BearerTokenService tokenService;
  private final UserDetailsService userDetailsService;

  private static final List<String> PUBLIC_PATHS = List.of(
      "/api/v1/authentication/"
  );

  public BearerAuthorizationRequestFilter(
      BearerTokenService tokenService,
      UserDetailsService userDetailsService
  ) {

    this.tokenService = tokenService;
    this.userDetailsService = userDetailsService;
  }

  /**
   * This method is responsible for filtering requests and setting the user authentication.
   * @param request The request object.
   * @param response The response object.
   * @param filterChain The filter chain object.
   */
  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain
  ) throws ServletException, IOException {

    String path = request.getRequestURI();

    for (String publicPath : PUBLIC_PATHS) {
      if (path.startsWith(publicPath)) {
        filterChain.doFilter(request, response);
        return;
      }
    }

    try {

      String token = tokenService.getBearerTokenFrom(request);

      if (token != null) {

        if (!tokenService.validateToken(token)) {

          request.setAttribute(
              "jwt_error_message",
              "JWT token is invalid"
          );

          response.sendError(
              HttpServletResponse.SC_UNAUTHORIZED
          );

          return;
        }

        String username =
            tokenService.getUsernameFromToken(token);

        var userDetails =
            userDetailsService.loadUserByUsername(username);

        SecurityContextHolder.getContext()
            .setAuthentication(
                UsernamePasswordAuthenticationTokenBuilder.build(
                    userDetails,
                    request
                )
            );
      }

    } catch (ExpiredJwtException e) {

      request.setAttribute(
          "jwt_error_message",
          "JWT token has expired"
      );

      response.sendError(
          HttpServletResponse.SC_UNAUTHORIZED
      );

      return;

    } catch (JwtException e) {

      request.setAttribute(
          "jwt_error_message",
          "JWT signature is invalid"
      );

      response.sendError(
          HttpServletResponse.SC_UNAUTHORIZED
      );

      return;

    } catch (Exception e) {

      request.setAttribute(
          "jwt_error_message",
          "Authentication failed"
      );

      response.sendError(
          HttpServletResponse.SC_UNAUTHORIZED
      );

      return;
    }

    filterChain.doFilter(request, response);
  }
}