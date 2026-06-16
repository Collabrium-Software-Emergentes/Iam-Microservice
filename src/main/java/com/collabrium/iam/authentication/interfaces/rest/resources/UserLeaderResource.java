package com.collabrium.iam.authentication.interfaces.rest.resources;

public record UserLeaderResource(
    Long id,
    String averageSolutionTime,
    Integer solvedRequests
) {
}