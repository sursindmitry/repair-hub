package com.sursindmitry.repairhub.web.dto;

public record LoginResponse(
    String token,

    String email
) {
}
