package com.atipera.github.exception;

import jakarta.ws.rs.NotFoundException;

public class GitHubUserNotFoundException extends NotFoundException {
    public GitHubUserNotFoundException(String username) {
        super("User " + username + " not found on GitHub");
    }
}
