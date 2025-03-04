# GitHub Repositories API

This project is a Quarkus 3 application written in Java that exposes a REST API to list all GitHub repositories for a given user which are not forks. For each repository, the API returns the repository name, owner login, and for each branch its name along with the last commit SHA.

# Project Stack

**Language:** Java 17

**Framework:** Quarkus 3.19.1

**Build Tool:** Gradle

# API Endpoints

## Get Non-Fork Repositories for a GitHub User

### Request:

```
GET <host>/api/v1/github/users/{username}/repos
```

#### Example:
```
GET http://localhost:8080/api/v1/github/users/octocat/repos
```

### Response:

#### Successful response
The response is a JSON array where each element represents a repository. Each repository includes its name, the owner's login, and a list of branches. Each branch has a name and its last commit SHA.
```
[
    {
        "name": "git-consortium",
        "ownerLogin": "octocat",
        "branches": [
            {
                "name": "master",
                "lastCommitSha": "b33a9c7c02ad93f621fa38f0e9fc9e867e12fa0e"
            }
        ]
    },
    {
        "name": "hello-worId",
        "ownerLogin": "octocat",
        "branches": [
            {
                "name": "master",
                "lastCommitSha": "7e068727fdb347b685b658d2981f8c85f7bf0585"
            }
        ]
    }
]
```

#### Error Response - User Not Found:

When a non-existent GitHub username is provided, the API returns a JSON error object with the status and a descriptive message.

```
{
  "status": 404,
  "message": "User nonExistentUser not found on GitHub"
}
```
# Running the Application

### Clone the Repository:

```
git clone https://github.com/danielwolski/GithubReposAPI.git
```

### Set the Environment Variable:
The application requires a GitHub API token to authenticate requests to GitHub. Make sure to set the environment variable `GITHUB_API_TOKEN` with your valid GitHub API token. You can define this in a `.env` file. \
Create a `.env` file in the root directory with the following content:

```
GITHUB_API_TOKEN=Bearer <your-github-api-token>
e.g.
GITHUB_API_TOKEN=Bearer github_gfda321harkjgndkajnfkvcrerf3nmdfn3n1nnj4k3k2j1
```

For more information about generating GitHub API token, check `Getting GitHub API token` section


### Run the Application in Dev Mode:
```
./gradlew quarkusDev
```

### Testing
The project includes an integration test (covering the happy path). In order for the integration test to run properly, `.env` file with proper GitHub API token must be created. To run tests:

```
./gradlew test
```

# Getting GitHub API token

### Description

This application connects with GitHub API through following endpoints:

REST API endpoint to list user repositories \
Required token permission: "Metadata" repository permissions (read) \
https://docs.github.com/en/rest/repos/repos?apiVersion=2022-11-28#list-repositories-for-a-user

REST API endpoint to list branches \
Required token permission: "Contents" repository permissions (read) \
https://docs.github.com/en/rest/branches/branches?apiVersion=2022-11-28#list-branches

### Token generation

In order to generate fine-grained token with required permissions:

#### 1. Login to your GitHub account

#### 2. Go to Developer settings

#### 3. Go to Personal access tokens -> Fine-grained tokens

#### 4. Generate new token  with following settings:
- **Repository access:** All repositories (Also includes public repositories (read-only))
- **Repository permissions:** Contents (read-only), Metadata (read-only)






