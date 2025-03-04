package com.atipera.github.exception;

import com.atipera.github.api.dto.ErrorResponse;
import jakarta.inject.Singleton;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

@Singleton
public class GlobalExceptionHandler {

    @ServerExceptionMapper
    public Response handleGitHubUserNotFoundException(GitHubUserNotFoundException e) {
        int status = Response.Status.NOT_FOUND.getStatusCode();
        return Response.status(status)
                .type(MediaType.APPLICATION_JSON)
                .entity(new ErrorResponse(status, e.getMessage()))
                .build();
    }

    @ServerExceptionMapper
    public Response handleGenericException(Exception e) {
        int status = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
        return Response.status(status)
                .type(MediaType.APPLICATION_JSON)
                .entity(new ErrorResponse(status, e.getMessage()))
                .build();
    }
}
