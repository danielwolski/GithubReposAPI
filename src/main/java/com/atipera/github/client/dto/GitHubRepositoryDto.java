package com.atipera.github.client.dto;

public record GitHubRepositoryDto(
        String name,
        boolean fork,
        GitHubOwnerDto owner
) {
}

