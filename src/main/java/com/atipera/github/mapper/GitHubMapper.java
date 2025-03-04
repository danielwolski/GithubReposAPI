package com.atipera.github.mapper;

import com.atipera.github.api.dto.BranchDto;
import com.atipera.github.api.dto.RepositoryDto;
import com.atipera.github.client.dto.GitHubBranchDto;
import com.atipera.github.client.dto.GitHubRepositoryDto;
import com.atipera.github.domain.Branch;
import com.atipera.github.domain.Repository;

import java.util.List;

public class GitHubMapper {

    public static Repository toDomain(GitHubRepositoryDto repoDto, List<GitHubBranchDto> branchDtos) {
        var branches = branchDtos.stream()
                .map(b -> new Branch(b.name(), b.commit().sha()))
                .toList();

        return new Repository(
                repoDto.name(),
                repoDto.owner().login(),
                branches
        );
    }

    public static RepositoryDto toDto(Repository repository) {
        var branchDtos = repository.getBranches().stream()
                .map(b -> new BranchDto(b.getName(), b.getLastCommitSha()))
                .toList();

        return new RepositoryDto(
                repository.getName(),
                repository.getOwnerName(),
                branchDtos
        );
    }
}
