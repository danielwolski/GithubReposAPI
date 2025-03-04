package com.atipera.github.mapper;

import com.atipera.github.api.dto.BranchDto;
import com.atipera.github.api.dto.RepositoryDto;
import com.atipera.github.client.dto.GitHubBranchDto;
import com.atipera.github.client.dto.GitHubCommitDto;
import com.atipera.github.client.dto.GitHubOwnerDto;
import com.atipera.github.client.dto.GitHubRepositoryDto;
import com.atipera.github.domain.Branch;
import com.atipera.github.domain.Repository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GitHubMapperTest {

    @Test
    void testToDomain() {
        // Given
        GitHubRepositoryDto repoDto = new GitHubRepositoryDto("TestRepo", false, new GitHubOwnerDto("testUser"));
        GitHubBranchDto branchDto1 = new GitHubBranchDto("main", new GitHubCommitDto("sha1"));
        GitHubBranchDto branchDto2 = new GitHubBranchDto("dev", new GitHubCommitDto("sha2"));
        List<GitHubBranchDto> branches = List.of(branchDto1, branchDto2);

        // When
        Repository repository = GitHubMapper.toDomain(repoDto, branches);

        // Then
        assertEquals("TestRepo", repository.getName());
        assertEquals("testUser", repository.getOwnerName());
        assertEquals(2, repository.getBranches().size());

        Branch b1 = repository.getBranches().get(0);
        Branch b2 = repository.getBranches().get(1);
        assertEquals("main", b1.getName());
        assertEquals("sha1", b1.getLastCommitSha());
        assertEquals("dev", b2.getName());
        assertEquals("sha2", b2.getLastCommitSha());
    }

    @Test
    void testToDto() {
        // Given
        Branch branch1 = new Branch("main", "sha1");
        Branch branch2 = new Branch("dev", "sha2");
        List<Branch> branches = List.of(branch1, branch2);
        Repository repository = new Repository("TestRepo", "testUser", branches);

        // When
        RepositoryDto repositoryDto = GitHubMapper.toDto(repository);

        // Then
        assertEquals("TestRepo", repositoryDto.name());
        assertEquals("testUser", repositoryDto.ownerLogin());
        assertEquals(2, repositoryDto.branches().size());

        BranchDto bDto1 = repositoryDto.branches().get(0);
        BranchDto bDto2 = repositoryDto.branches().get(1);
        assertEquals("main", bDto1.name());
        assertEquals("sha1", bDto1.lastCommitSha());
        assertEquals("dev", bDto2.name());
        assertEquals("sha2", bDto2.lastCommitSha());
    }
}
