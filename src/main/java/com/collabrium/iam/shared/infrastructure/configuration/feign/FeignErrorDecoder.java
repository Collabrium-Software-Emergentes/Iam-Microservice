package com.collabrium.iam.shared.infrastructure.configuration.feign;

import feign.Response;
import feign.codec.ErrorDecoder;

public class FeignErrorDecoder implements ErrorDecoder {

  @Override
  public Exception decode(String methodKey, Response response) {

    return switch (response.status()) {

      case 400 -> new RuntimeException("Bad Request");

      case 401 -> new RuntimeException("Unauthorized");

      case 404 -> new RuntimeException("Resource Not Found");

      case 500 -> new RuntimeException("Internal Server Error");

      default -> new RuntimeException(
        "Feign error: " + response.status()
      );
    };
  }
}