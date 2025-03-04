package com.atipera.github.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Branch {
    private String name;
    private String lastCommitSha;
}
