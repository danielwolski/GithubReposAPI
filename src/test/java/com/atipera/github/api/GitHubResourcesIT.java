package com.atipera.github.api;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

@QuarkusTest
class GitHubResourcesIT {

    @Test
    void shouldReturnExpectedResponseForExistingUser() {
        given()
                .pathParam("username", "octocat")

                .when()
                .get("/api/v1/github/users/{username}/repos")

                .then()

                // Check for correct status code
                .statusCode(200)

                // Check for existence of all user repositories
                .and()
                .body("name", hasItems("git-consortium", "hello-worId", "Hello-World", "Spoon-Knife", "test-repo1", "octocat.github.io"))

                // Check the details of example repository: git-consortium
                .and()
                .body("find { it.name == 'git-consortium' }.ownerLogin", equalTo("octocat"))
                .body("find { it.name == 'git-consortium' }.branches.size()", equalTo(1))
                .body("find { it.name == 'git-consortium' }.branches[0].name", equalTo("master"))
                .body("find { it.name == 'git-consortium' }.branches[0].lastCommitSha", equalTo("b33a9c7c02ad93f621fa38f0e9fc9e867e12fa0e"))

                // Check the details of example repository: Hello-World
                .and()
                .body("find { it.name == 'Hello-World' }.ownerLogin", equalTo("octocat"))
                .body("find { it.name == 'Hello-World' }.branches.size()", equalTo(3))
                .body("find { it.name == 'Hello-World' }.branches.find { it.name == 'master' }.lastCommitSha", equalTo("7fd1a60b01f91b314f59955a4e4d4e80d8edf11d"))
                .body("find { it.name == 'Hello-World' }.branches.find { it.name == 'octocat-patch-1' }.lastCommitSha", equalTo("b1b3f9723831141a31a1a7252a213e216ea76e56"))
                .body("find { it.name == 'Hello-World' }.branches.find { it.name == 'test' }.lastCommitSha", equalTo("b3cbd5bbd7e81436d2eee04537ea2b4c0cad4cdf"));
    }
}
