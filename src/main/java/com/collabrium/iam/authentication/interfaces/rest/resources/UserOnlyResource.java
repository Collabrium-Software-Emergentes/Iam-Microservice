package com.collabrium.iam.authentication.interfaces.rest.resources;

public record UserOnlyResource(
    Long id,
    String username,
    String name,
    String surname,
    String imgUrl,
    String email,
    Long leaderId,
    Long memberId
) {
}