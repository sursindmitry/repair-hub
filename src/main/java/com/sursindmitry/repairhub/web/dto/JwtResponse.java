package com.sursindmitry.repairhub.web.dto;

public record JwtResponse(
    String token,

    String refreshToken,

    String email
) {
}
