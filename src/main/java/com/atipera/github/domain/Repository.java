package com.atipera.github.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class Repository {
    private String name;
    private String ownerName;
    private List<Branch> branches;
}
