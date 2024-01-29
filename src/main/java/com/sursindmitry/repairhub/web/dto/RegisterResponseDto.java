package com.sursindmitry.repairhub.web.dto;

public record RegisterResponseDto(

    String email,

    String firstName,

    String lastName,

    String message
) {
}
