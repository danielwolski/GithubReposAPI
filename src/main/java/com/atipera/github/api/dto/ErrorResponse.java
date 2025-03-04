package com.atipera.github.api.dto;

public record ErrorResponse(
        int status,
        String message
) {}
