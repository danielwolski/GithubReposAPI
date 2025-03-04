package com.atipera.github.api.dto;

public record BranchDto (
        String name,
        String lastCommitSha
) {}
