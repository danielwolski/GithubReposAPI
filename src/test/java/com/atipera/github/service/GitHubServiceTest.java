package com.atipera.github.service;

import com.atipera.github.client.GitHubClient;
import com.atipera.github.client.dto.GitHubBranchDto;
import com.atipera.github.client.dto.GitHubCommitDto;
import com.atipera.github.client.dto.GitHubOwnerDto;
import com.atipera.github.client.dto.GitHubRepositoryDto;
import com.atipera.github.domain.Branch;
import com.atipera.github.domain.Repository;
import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GitHubServiceTest {

    private GitHubClient gitHubClient;
    private GitHubService gitHubService;

    @BeforeEach
    void setUp() {
        gitHubClient = mock(GitHubClient.class);
        gitHubService = new GitHubService(gitHubClient);
    }

    @Test
    void testGetNonForkRepositoriesByUsername_HappyPath() {
        // Given
        String username = "testuser";
        GitHubOwnerDto ownerDto = new GitHubOwnerDto(username);

        GitHubRepositoryDto repo1 = new GitHubRepositoryDto("repo1", false, ownerDto);
        GitHubRepositoryDto repo2 = new GitHubRepositoryDto("repo2", true, ownerDto);
        List<GitHubRepositoryDto> repositories = List.of(repo1, repo2);

        when(gitHubClient.getUserRepositories(username))
                .thenReturn(Uni.createFrom().item(repositories));

        GitHubBranchDto branchDto = new GitHubBranchDto("main", new GitHubCommitDto("sha123"));
        List<GitHubBranchDto> branches = List.of(branchDto);
        when(gitHubClient.getRepositoryBranches(username, "repo1"))
                .thenReturn(Uni.createFrom().item(branches));

        // When
        List<Repository> result = gitHubService.getNonForkRepositoriesByUsername(username)
                .await().indefinitely();

        // Then
        assertNotNull(result);

        assertEquals(1, result.size());

        Repository repository = result.get(0);
        assertEquals("repo1", repository.getName());
        assertEquals(username, repository.getOwnerName());

        List<Branch> branchList = repository.getBranches();
        assertNotNull(branchList);
        assertEquals(1, branchList.size());

        Branch branch = branchList.get(0);
        assertEquals("main", branch.getName());
        assertEquals("sha123", branch.getLastCommitSha());
    }
}
