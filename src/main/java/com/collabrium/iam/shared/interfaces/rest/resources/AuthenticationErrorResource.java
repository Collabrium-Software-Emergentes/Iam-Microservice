package com.collabrium.iam.shared.interfaces.rest.resources;

import java.util.List;

public record AuthenticationErrorResource(
    String timestamp,
    int status,
    String error,
    String code,
    String message,
    String path,
    List<String> details
) {
}