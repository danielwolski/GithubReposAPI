package com.atipera.github.client.dto;

public record GitHubBranchDto(
        String name,
        GitHubCommitDto commit
) {
}
