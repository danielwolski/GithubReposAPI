package com.atipera.github.service;

import com.atipera.github.domain.Repository;
import com.atipera.github.client.GitHubClient;
import com.atipera.github.exception.GitHubUserNotFoundException;
import com.atipera.github.mapper.GitHubMapper;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;

@ApplicationScoped
public class GitHubService {

    @Inject
    @RestClient
    GitHubClient gitHubClient;

    public GitHubService(@RestClient GitHubClient gitHubClient) {
        this.gitHubClient = gitHubClient;
    }

    public Uni<List<Repository>> getNonForkRepositoriesByUsername(String username) {
        return gitHubClient.getUserRepositories(username)
                .onFailure().recoverWithUni(t -> {
                    if (t instanceof WebApplicationException wae && wae.getResponse().getStatus() == 404) {
                        return Uni.createFrom().failure(new GitHubUserNotFoundException(username));
                    }
                    return Uni.createFrom().failure(new RuntimeException(t));
                })
                .onItem().transformToMulti(list -> Multi.createFrom().iterable(list))
                .filter(repoDto -> !repoDto.fork())
                .onItem().transformToUniAndMerge(repoDto ->
                        gitHubClient.getRepositoryBranches(repoDto.owner().login(), repoDto.name())
                                .onItem().transform(branchList ->
                                        GitHubMapper.toDomain(repoDto, branchList)
                                )
                )
                .collect().asList();
    }
}