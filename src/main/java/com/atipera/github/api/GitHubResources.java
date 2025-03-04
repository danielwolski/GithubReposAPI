package com.atipera.github.api;

import com.atipera.github.api.dto.RepositoryDto;
import com.atipera.github.mapper.GitHubMapper;
import com.atipera.github.service.GitHubService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import io.smallrye.mutiny.Uni;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Path("/api/v1/github")
@AllArgsConstructor
public class GitHubResources {

    private final GitHubService gitHubService;

    @GET
    @Path("/users/{username}/repos")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<RepositoryDto>> getNonForkRepositoriesByUsername(@PathParam("username") String username) {
        return gitHubService.getNonForkRepositoriesByUsername(username)
                .onItem().transform(repos ->
                        repos.stream()
                                .map(GitHubMapper::toDto)
                                .toList()
                );
    }
}
